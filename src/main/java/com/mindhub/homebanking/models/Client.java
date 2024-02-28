package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
   private String firstName;
    private String lastName;

    private String email;

    private String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoan> clientLoans = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
//   @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
//   @JsonManagedReference
    private Set<Card> cards = new HashSet<>();
    public Client() { }



    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(List<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void addAccount (Account account){
        account.setClient(this);
        accounts.add(account);
    }
    public void addCard (Card card){
        card.setClient(this);
        cards.add(card);
    }



    @Override
    public String toString() {
        return
         "Client{" +
                "id= " + id +
                ", Name= " + firstName + ' ' + lastName + "\n" +
                ", email='" + email + "\n" +
                ", Accounts= "+ accounts+
                '}';
    }
}

