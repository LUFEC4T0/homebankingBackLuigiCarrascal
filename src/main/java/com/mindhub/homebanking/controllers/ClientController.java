package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> clients= clientRepository.findAll();
        return new ResponseEntity<>(clients.stream().map(ClientDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ClientDTO clientDTO = new ClientDTO(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("hello" + email);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        return ResponseEntity.ok(new ClientDTO(client));
    }

    @PostMapping("/current/accounts")
    public ResponseEntity<?> createAccount() {
        try {
            String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
            Client client = clientRepository.findByEmail(userMail);

            if (client.getAccounts().size() >= 3) {
                return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
            }

            String accountNumber = "VIN-" + String.format("%08d", new Random().nextInt(100000000));
            Account account = new Account(accountNumber, LocalDate.now(), 0.0);

            client.addAccount(account);
            clientRepository.save(client);
            accountRepository.save(account);

            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current/accounts")
    public ResponseEntity<List<AccountsDTO>> getAccounts(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        List<Account> accounts = client.getAccounts();

        return new ResponseEntity<>(accounts.stream().map(AccountsDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/current/cards")
    public ResponseEntity<List<CardDTO>> getCards(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        Set<Card> cards = client.getCards();

        return new ResponseEntity<>(cards.stream().map(CardDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

}
