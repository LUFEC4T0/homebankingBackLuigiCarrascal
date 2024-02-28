package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private LocalDate creationDate;

    private double balance;

    @ManyToOne
//    @JsonBackReference
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions    .add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public long getId() {
        return id;
    }


}
