package com.cheikh.gestionstock.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class EntityManagerUtils {
    private static EntityManager em;
    public static EntityManager getEM() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        }
        return em;
    }
}
