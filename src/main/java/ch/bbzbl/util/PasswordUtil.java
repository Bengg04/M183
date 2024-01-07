package ch.bbzbl.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    // Generate a salted hash for the given password using BCrypt
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Verify a password against a stored hash
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}
