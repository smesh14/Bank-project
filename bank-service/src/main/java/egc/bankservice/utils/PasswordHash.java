package egc.bankservice.utils;

import egc.bankservice.exception.BankException;
import lombok.extern.slf4j.Slf4j;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class PasswordHash {

    public static String getMD5PasswordHash(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            log.error("Error occurred while hash password", ex);
            throw new BankException("Can't hash password");
        }
        return generatedPassword;
    }

    public static boolean validateMD5PasswordHash(String fullPasswordHash, String password) {
        return fullPasswordHash.equals(getMD5PasswordHash(password));
    }
}
