package egc.bankservice.configuration;



public class JwtProperties {
    private static String keyStorePath = "keystore.jks";
    private static String keyStorePassword = "123456";
    private static String keyAlias = "alias";
    private static String privateKeyPassphrase = "123456";
    private static String issuer = "123456";

    public static String getKeyStorePath() {
        return keyStorePath;
    }

    public static void setKeyStorePath(String keyStorePath) {
        JwtProperties.keyStorePath = keyStorePath;
    }

    public static String getKeyStorePassword() {
        return keyStorePassword;
    }

    public static void setKeyStorePassword(String keyStorePassword) {
        JwtProperties.keyStorePassword = keyStorePassword;
    }

    public static String getKeyAlias() {
        return keyAlias;
    }

    public static void setKeyAlias(String keyAlias) {
        JwtProperties.keyAlias = keyAlias;
    }

    public static String getPrivateKeyPassphrase() {
        return privateKeyPassphrase;
    }

    public static void setPrivateKeyPassphrase(String privateKeyPassphrase) {
        JwtProperties.privateKeyPassphrase = privateKeyPassphrase;
    }

    public static String getIssuer() {
        return issuer;
    }

    public static void setIssuer(String issuer) {
        JwtProperties.issuer = issuer;
    }
}
