package egc.bankservice.repository;

import egc.bankservice.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

        boolean existsByCardNumber(String cardNumber);

        CardEntity getByCardNumber(String cardNumber);
}
