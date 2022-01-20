package egc.bankservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import egc.bankservice.configuration.JwtProperties;
import egc.bankservice.model.AuthCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class AuthManager {

    @Autowired
    private RSAPublicKey publicKey;

    @Autowired
    private RSAPrivateKey privateKey;

    private final int consoleLifetime = 120;

    public String createAccessToken(AuthCardRequest authCard) {
        return JWT.create()
                .withIssuer(JwtProperties.getIssuer())
                .withSubject(String.valueOf(authCard.getCardNumber()))
                .withIssuedAt(new Date())
                .withExpiresAt(getExpirationTime(Calendar.MINUTE, consoleLifetime))
                .withArrayClaim("scp", new String[]{"BANK_SERVICE"})
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }

    public String getCardNumber(){
        Jwt jwt = getJwt();
        assert jwt != null;
        return  jwt.getSubject();
    }

    private Jwt getJwt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Jwt) {
                return (Jwt) principal;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private Date getExpirationTime(int unit, int duration){
        Calendar calendar = Calendar.getInstance();
        calendar.add(unit,duration);
        return calendar.getTime();
    }
}
