package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return  args -> {
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123") );
			Account melba1 = new Account("123456", LocalDate.now(), 5000.00);
			Account melba2 = new Account("78945", LocalDate.now(), 7500.00);

			clientRepository.save(melba);
			melba.addAccount(melba1);
//
			melba.addAccount(melba2);
			accountRepository.save(melba1);
			accountRepository.save(melba2);
			System.out.println(melba);

			Transaction transaction = new Transaction(TransactionType.DEBIT, -1200.00, "Matelsa", LocalDateTime.now());
			melba1.addTransaction(transaction);
			transactionRepository.save(transaction);

			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 700.00, "True", LocalDateTime.now());
			melba2.addTransaction(transaction2);
			transactionRepository.save(transaction2);



			Client Melba2 = new Client("Melba2", "Morel2", "melba2@mindhub.com ", passwordEncoder.encode("melba123"));
			Account melba2_1 = new Account("654321", LocalDate.now(), 5000.00);
			Account melba2_2 = new Account("567890", LocalDate.now(), 7500.00);

			clientRepository.save(Melba2);
			Melba2.addAccount(melba2_1);
//
			Melba2.addAccount(melba2_2);
			accountRepository.save(melba2_1);
			accountRepository.save(melba2_2);
			System.out.println(Melba2);

			Transaction transaction2_1 = new Transaction(TransactionType.DEBIT, -1400.00, "Nike", LocalDateTime.now());
			melba2_1.addTransaction(transaction2_1);
			transactionRepository.save(transaction2_1);

			Transaction transaction2_2 = new Transaction(TransactionType.CREDIT, 600.00, "GatOversize", LocalDateTime.now());
			melba2_2.addTransaction(transaction2_2);
			transactionRepository.save(transaction2_2);

			Loan mortgage = new Loan("Mortgage", 500000, Arrays.asList(12,24,36,48,60));
			Loan personal = new Loan("Personal", 100000, Arrays.asList(6,12,24));
			Loan automotive = new Loan("Automotive", 300000, Arrays.asList(6,12,24,36));

			loanRepository.save(mortgage);
			loanRepository.save(personal);
			loanRepository.save(automotive);

			ClientLoan loan1 = new ClientLoan(400000, 60);
			ClientLoan loan2 = new ClientLoan(50000, 12);

			loan1.setClient(melba);
			loan2.setClient(melba);

			loan1.setLoan(mortgage);
			loan2.setLoan(personal);

			clientLoanRepository.save(loan1);
			clientLoanRepository.save(loan2);



			ClientLoan loan2_1 = new ClientLoan(100000, 24);
			ClientLoan loan2_2 = new ClientLoan(200000, 36);

			loan2_1.setClient(Melba2);
			loan2_2.setClient(Melba2);

			loan2_1.setLoan(personal);
			loan2_2.setLoan(automotive);

			clientLoanRepository.save(loan2_1);
			clientLoanRepository.save(loan2_2);

			Card card1 = new Card(melba, Cardtype.CREDIT, CardColor.GOLD, "1234-5678-9009-8765", 123, LocalDate.now().plusYears(5), LocalDate.now());

			melba.addCard(card1);

			cardRepository.save(card1);
		};
	}

}
