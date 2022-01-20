package egc.bankservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

@Slf4j
@Configuration
public class JwtConfig {



    @Bean
    public KeyStore keyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = new ClassPathResource(JwtProperties.getKeyStorePath()).getInputStream();
            keyStore.load(resourceAsStream, JwtProperties.getKeyStorePassword().toCharArray());
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load keystore {}", JwtProperties.getKeyStorePath());
        }

        throw new IllegalArgumentException("Unable to load keystore");
    }

    @Bean
    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
        try {
            Key key = keyStore.getKey(JwtProperties.getKeyAlias(), JwtProperties.getPrivateKeyPassphrase().toCharArray());
            if (key instanceof RSAPrivateKey) {
                return (RSAPrivateKey) key;
            }
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load RSA private key {}", JwtProperties.getKeyAlias());
        }

        throw new IllegalArgumentException("Unable to load RSA private key");
    }

    @Bean
    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate(JwtProperties.getKeyAlias());
            PublicKey publicKey = certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
        } catch (KeyStoreException e) {
            log.error("Unable to load RSA public key {}", JwtProperties.getKeyAlias());
        }
        throw new IllegalArgumentException("Unable to load RSA public key");
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(Arrays.asList(
                new JwtTimestampValidator(),
                new JwtIssuerValidator(JwtProperties.getIssuer())
        )));
        return decoder;
    }
}
