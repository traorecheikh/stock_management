package com.cheikh.gestionstock.services;

import com.cheikh.gestionstock.models.User;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.cheikh.gestionstock.services.EntityManagerUtils.getEM;

@Slf4j
public class UserServices {
    @Getter
    @Setter
    private static User userSession;
    private static EntityManager em;
    private static final Path sessions = Paths.get("sessions");

    // Instantiate BCryptPasswordEncoder (you can also inject it via Spring if you're using Spring)
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public int Register(User user) {
        em = getEM();
        Object o = em.createQuery("select u from User u where u.email = :email")
                .setParameter("email", user.getEmail())
                .getSingleResultOrNull();

        if (o != null) {
            log.warn("L'utilisateur existe déjà avec l'email {}", user.getEmail());
            return 2;
        }

        try {
            // Hash the password using BCryptPasswordEncoder
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            log.info("Utilisateur enregistré avec succès avec l'email {}.", user.getEmail());
            return 0;
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Erreur lors de l'enregistrement de l'utilisateur avec l'email {}: {}", user.getEmail(), e.getMessage());
            return 1;
        }
    }

    public boolean Login(String email, String password) {
        em = getEM();

        Object o = em.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email)
                .getSingleResultOrNull();

        if (o == null) {
            log.warn("Utilisateur non trouvé avec l'email {}", email);
            return false;
        }

        User user = (User) o;

        if (passwordEncoder.matches(password, user.getPassword())) {
            String sessionKey = UUID.randomUUID().toString();

            user.setSessionKey(sessionKey);
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();

            try {
                if (!Files.exists(sessions)) {
                    Files.createDirectories(sessions);
                }

                Path sessionFile = sessions.resolve(sessionKey);
                Files.write(sessionFile, sessionKey.getBytes());

                setUserSession(user);
                log.info("Utilisateur avec l'email {} connecté avec succès, clé de session générée.", email);
                return true;
            } catch (IOException e) {
                log.error("Erreur lors de la création du fichier de session pour l'utilisateur avec l'email {}: {}", email, e.getMessage());
                return false;
            }
        }

        log.warn("Mot de passe incorrect pour l'utilisateur avec l'email {}", email);
        return false;
    }

    public void Logout() {
        setUserSession(null);
        log.info("Utilisateur déconnecté avec succès.");
    }

    public static  User checkSession() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sessions)) {
            for (Path entry : stream) {
                String sessionKey = new String(Files.readAllBytes(entry));

                Object o = em.createQuery("select u from User u where u.sessionKey = :sessionKey")
                        .setParameter("sessionKey", sessionKey)
                        .getSingleResultOrNull();

                if (o != null) {
                    User user = (User) o;
                    if (isSessionValid(user.getSessionKey())) {
                        setUserSession(user);
                        log.info("Session valide trouvée pour l'utilisateur avec l'email {}", user.getEmail());
                        return user;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Erreur lors de la vérification de la session: {}", e.getMessage());
        }
        log.warn("Aucune session valide trouvée.");
        return null;
    }

    private static boolean isSessionValid(String sessionKey) {
        Path sessionFile = sessions.resolve(sessionKey);
        return Files.exists(sessionFile);
    }

    public boolean addAdmin(User admin, User newAdmin) {
        if (!admin.isAdmin()) {
            log.warn("L'utilisateur avec l'email {} n'est pas un administrateur, action interdite.", admin.getEmail());
            return false;
        }

        try {
            newAdmin.setAdmin(true);
            em.getTransaction().begin();
            em.merge(newAdmin);
            em.getTransaction().commit();
            log.info("L'utilisateur avec l'email {} a été ajouté en tant qu'administrateur par {}.", newAdmin.getEmail(), admin.getEmail());
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Erreur lors de l'ajout de l'utilisateur {} en tant qu'administrateur: {}", newAdmin.getEmail(), e.getMessage());
            return false;
        }
    }

    public boolean removeAdmin(User admin, User targetUser) {
        if (!admin.isAdmin()) {
            log.warn("L'utilisateur avec l'email {} n'est pas un administrateur, action interdite.", admin.getEmail());
            return false;
        }

        try {
            targetUser.setAdmin(false);
            em.getTransaction().begin();
            em.merge(targetUser);
            em.getTransaction().commit();
            log.info("L'utilisateur avec l'email {} a été retiré des administrateurs par {}.", targetUser.getEmail(), admin.getEmail());
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Erreur lors du retrait de l'utilisateur {} en tant qu'administrateur: {}", targetUser.getEmail(), e.getMessage());
            return false;
        }
    }
}
