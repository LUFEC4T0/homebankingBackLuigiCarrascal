package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplyDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public ResponseEntity<List<LoanDTO>> getAllClients(){
        List<Loan> loans = loanRepository.findAll();
        return new ResponseEntity<>(loans.stream().map(LoanDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> createLoan(@RequestBody LoanApplyDTO loanApplyDTO) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        if (loanApplyDTO.name().isBlank()) {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.FORBIDDEN);
        }

        if (loanApplyDTO.maxAmount() <= 0 || loanApplyDTO.maxAmount() == null) {
            return new ResponseEntity<>("Max Amount cannot be empty", HttpStatus.FORBIDDEN);
        }

        if (loanApplyDTO.payments() <= 0 || loanApplyDTO.payments() == null) {
            return new ResponseEntity<>("Payments cannot be empty", HttpStatus.FORBIDDEN);
        }

        if(loanApplyDTO.numberAccount().isBlank()){
            return new ResponseEntity<>("Number Account cannot be empty", HttpStatus.FORBIDDEN);
        }

        if(!loanRepository.existsByName(loanApplyDTO.name())){
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }

        if(loanApplyDTO.maxAmount() > loanRepository.findByName(loanApplyDTO.name()).getMaxAmount()){
            return new ResponseEntity<>("Amount in excess of the amount allowed by the loan", HttpStatus.FORBIDDEN);
        }

        if(!loanRepository.existsByNameAndPayments(loanApplyDTO.name(), loanApplyDTO.payments())){
            return new ResponseEntity<>("payments not allowed by the loan", HttpStatus.FORBIDDEN);
        }

        if(!accountRepository.existsByNumber(loanApplyDTO.numberAccount())){
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(accountRepository.findByNumber(loanApplyDTO.numberAccount()))){
            return new ResponseEntity<>("the account not belong to you", HttpStatus.FORBIDDEN);
        }



        Loan loanApply = loanRepository.findByName(loanApplyDTO.name());

        ClientLoan loanClien = new ClientLoan(loanApplyDTO.maxAmount() + loanApplyDTO.maxAmount() * 0.2, loanApplyDTO.payments());

        loanClien.setLoan(loanApply);
        loanClien.setClient(client);

        clientLoanRepository.save(loanClien);

        Account account = accountRepository.findByNumber(loanApplyDTO.numberAccount());
        account.setBalance(account.getBalance() + loanApplyDTO.maxAmount());
        accountRepository.save(account);

        Transaction transactionLoan = new Transaction(TransactionType.CREDIT, loanApplyDTO.maxAmount(), loanApplyDTO.name() + " loan approved", LocalDateTime.now());
        account.addTransaction(transactionLoan);
        transactionRepository.save(transactionLoan);

        return new ResponseEntity<>("Created loan", HttpStatus.CREATED);
    }

}

