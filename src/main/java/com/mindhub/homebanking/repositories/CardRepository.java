package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Cardtype;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Boolean existsCardByColorAndTypeAndClient(
            CardColor color,
            Cardtype type,
            Client client
    );
    int countByTypeAndClient(
            Cardtype type,
            Client client
    );
}
