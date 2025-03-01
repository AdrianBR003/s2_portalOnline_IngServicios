package com.p2.portal_online.Security;

import java.security.MessageDigest;
import java.util.Base64;

public class HashPassword {

    public static String hashP(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashB = md.digest(password.getBytes()); // Transformamos la password en una array de bytes
            return Base64.getEncoder().encodeToString(hashB); // Lo transformamos a binario
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al realizar el hash de la password");
        }
    }

    public static boolean verifyP(String oPassword, String hPassword){
        System.out.println("Contraseñas: " +  hashP(oPassword).equals(hPassword));
        return hashP(oPassword).equals(hPassword);
    }

}
