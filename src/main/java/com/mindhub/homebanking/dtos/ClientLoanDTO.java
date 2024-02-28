package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public class ClientLoanDTO {

    private long id;

    private long loan_id;

    private String loan_name;

    private double amount;

    private int payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loan_id = clientLoan.getLoan().getId();
        this.loan_name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public long getLoan_id() {
        return loan_id;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
