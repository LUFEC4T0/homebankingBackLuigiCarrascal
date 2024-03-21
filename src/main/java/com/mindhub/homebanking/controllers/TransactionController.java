package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionApplyDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional //transactional para que no se rompa la base de datos
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionApplyDTO transactionApplyDTO) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        Account accountOrigen = accountRepository.findByNumber(transactionApplyDTO.numberAccountOrigen());
        Account accountDestiny = accountRepository.findByNumber(transactionApplyDTO.numberAccountDestiny());

        if (transactionApplyDTO.amount() == null || transactionApplyDTO.description().isBlank() || transactionApplyDTO.numberAccountOrigen().isBlank() || transactionApplyDTO.numberAccountDestiny().isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }


        if(transactionApplyDTO.numberAccountOrigen().equals(transactionApplyDTO.numberAccountDestiny())){
            return new ResponseEntity<>("Same account", HttpStatus.FORBIDDEN);
        }

        if(!accountRepository.existsByNumber(transactionApplyDTO.numberAccountOrigen())){
            return new ResponseEntity<>("Account origen not found", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(accountOrigen)){
            return new ResponseEntity<>("the account of origin does not belong to you", HttpStatus.FORBIDDEN);
        }

        if(!accountRepository.existsByNumber(transactionApplyDTO.numberAccountDestiny())){
            return new ResponseEntity<>("Account destiny not found", HttpStatus.FORBIDDEN);
        }

        if(transactionApplyDTO.amount() > accountOrigen.getBalance()) {
            return new ResponseEntity<>("Insufficient balance", HttpStatus.FORBIDDEN);
        }

        double newBalanceAccountOrigen = accountOrigen.getBalance() - transactionApplyDTO.amount();
        double newBalanceAccountDestiny = accountDestiny.getBalance() + transactionApplyDTO.amount();

        accountOrigen.setBalance(newBalanceAccountOrigen);
        accountDestiny.setBalance(newBalanceAccountDestiny);

        accountRepository.save(accountOrigen);
        accountRepository.save(accountDestiny);

        Transaction transactionOrigen = new Transaction(TransactionType.DEBIT, -transactionApplyDTO.amount(), transactionApplyDTO.description(), LocalDateTime.now());
        Transaction transactionDestiny = new Transaction(TransactionType.CREDIT, transactionApplyDTO.amount(), transactionApplyDTO.description(), LocalDateTime.now());

        accountOrigen.addTransaction(transactionOrigen);
        transactionRepository.save(transactionOrigen);

        accountDestiny.addTransaction(transactionDestiny);
        transactionRepository.save(transactionDestiny);




        return new ResponseEntity<>("Transaction created", HttpStatus.CREATED);
    }

}
