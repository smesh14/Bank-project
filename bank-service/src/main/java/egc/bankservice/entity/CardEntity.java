package egc.bankservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 30, unique = true, nullable = false)
    private String cardNumber;

    @Column(length = 100)
    private String pin;

    private long amount;

    @Embedded
    private CardAuthInfo authInfo = new CardAuthInfo();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

}
