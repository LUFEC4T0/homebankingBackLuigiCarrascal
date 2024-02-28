package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {
    private  long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountsDTO> accounts;
    private List<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO() {
    }



    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts=accountsDTO(client.getAccounts());
        this.loans = clientLoanDTO(client.getClientLoans());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    private List<ClientLoanDTO> clientLoanDTO(List<ClientLoan> clientLoans) {
        return clientLoans.stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    private List<AccountsDTO>accountsDTO(List<Account>accounts){
        return accounts.stream().map(AccountsDTO::new).collect(Collectors.toList());
    }


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountsDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
}
