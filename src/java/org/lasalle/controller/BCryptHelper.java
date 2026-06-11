package org.lasalle.controller;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHelper {

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean verify(String plainPassword, String hashed) {
        try {
            return BCrypt.checkpw(plainPassword, hashed);
        } catch (Exception e) {
            return false;
        }
    }
}