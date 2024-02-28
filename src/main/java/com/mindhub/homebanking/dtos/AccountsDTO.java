package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountsDTO {
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private List<TransactionDTO>transactions = new ArrayList<>();

    public AccountsDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = transactionDTO(account.getTransactions());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public List<TransactionDTO> getTransactions() {return transactions;}

    private List<TransactionDTO>transactionDTO(List<Transaction>transactions){
        return transactions.stream().map(TransactionDTO::new).collect(Collectors.toList());
    }
}
