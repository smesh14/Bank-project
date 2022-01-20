package egc.bankservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResult {
    private AuthCardResponse authCardResponse;
    private String jwt;
}
