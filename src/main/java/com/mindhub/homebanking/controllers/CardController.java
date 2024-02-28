package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardApplyDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Cardtype;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/clients")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CardApplyDTO cardApplyDTO) {
        try {
            String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
            Client client = clientRepository.findByEmail(userMail);

            if(client.getCards().size() >= 3) {
                return new ResponseEntity<>("Maximum number of cards reached", HttpStatus.FORBIDDEN);
            }

            String cardNumber = String.format("%4d", new Random().nextInt(10000));
            String cardFinalNumber = cardNumber + "-" + cardNumber + "-" + cardNumber + "-" + cardNumber;    // 0000-0000-0000-0000
            int cvvNumber = 100 + new Random().nextInt(900);

            Card card = new Card(client, Cardtype.valueOf(cardApplyDTO.cardType()), CardColor.valueOf(cardApplyDTO.cardColor()), cardFinalNumber, cvvNumber, LocalDate.now().plusYears(5), LocalDate.now());

            client.addCard(card);
            cardRepository.save(card);


            return new ResponseEntity<>("Created card", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
