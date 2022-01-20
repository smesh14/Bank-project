package egc.bankservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCardRequest {

    @JsonProperty(required = true)
    private String cardNumber;

    @JsonProperty(required = true)
    private long amount;
}
