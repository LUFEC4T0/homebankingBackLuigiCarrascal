package com.mindhub.homebanking.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String name;

    private double maxAmount;

    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private List<ClientLoan> clientLoans = new ArrayList<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(List<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    @Embeddable
    public class Payment{
        private double maxAmount;

        private int installmets;
    }
}
