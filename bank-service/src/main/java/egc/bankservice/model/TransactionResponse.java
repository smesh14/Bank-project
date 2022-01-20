package egc.bankservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionResponse {

        private SuccessCode code;

        private ErrorMessage errorMessage;

}

