package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("")
    public ResponseEntity<List<AccountsDTO>> getAllAccounts(){
        List<Account> accounts= accountRepository.findAll();
        return new ResponseEntity<>(accounts.stream().map(AccountsDTO::new).collect(java.util.stream.Collectors.toList()), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountsDTO> getAccountById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);
        if(account == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        AccountsDTO accountsDTO = new AccountsDTO(account);
        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }
}
