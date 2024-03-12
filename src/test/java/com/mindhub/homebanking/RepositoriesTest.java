package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//test of integration

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientLoanRepository clientLoandRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRepository loanRepository;
    @Test
    public void existAccounts() {
        List<Account> accountList = accountRepository.findAll();
        assertThat(accountList, is(not(empty())));
    }

    @Test
    public void melbaAccount() {
        Account account = accountRepository.findByNumber("VIN-12345678");
        assertThat(account, is(notNullValue()));
    }

    //Repository of Card

    @Test
    public void existCards() {
        List<Card> cardsList = cardRepository.findAll();
        assertThat(cardsList, is(not(empty())));
    }

    @Test
    public void melbaCard() {
        Boolean card = cardRepository.existsCardByCardHolder("Melba Morel");
        assertThat(card, is(true));
    }

    //Repository of ClientLoan

    @Test
    public void existClientLoans() {
        List<ClientLoan> clientLoansList = clientLoandRepository.findAll();
        assertThat(clientLoansList, is(not(empty())));
    }

    @Test
    public void luigiClientLoan() {
        Client client = clientRepository.findByEmail("luigi@luigi");
        Boolean clientLoan = clientLoandRepository.existsClientLoanByClient(client);
        assertThat(clientLoan, is(false));
    } // false por que vamos a verificar que el cliente luigi no tenga un prestamo

    //Repository of Client

    @Test
    public void existClients() {
        List<Client> clientsList = clientRepository.findAll();
        assertThat(clientsList, is(not(empty())));
    }

    @Test
    public void luigiClient() {
        Client client = clientRepository.findByEmail("luigi@luigi");
        assertThat(client, is(notNullValue()));
    }

    //Repository of Loan

    @Test
    public void existLoans() {
        List<Loan> loansList = loanRepository.findAll();
        assertThat(loansList, is(not(empty())));
    }

    @Test
    public void loanName() {
        Loan loan = loanRepository.findByName("Mortgage");
        assertThat(loan, is(notNullValue()));
    }

    //Repository of Transaction

    @Test
    public void existTransactions() {
        List<Transaction> transactionsList = transactionRepository.findAll();
        assertThat(transactionsList, is(not(empty())));
    }

    @Test
    public void transactionAmount() {
        Boolean transaction = transactionRepository.existsByAmount(800.0);
        assertThat(transaction, is(true));
    }

}


