package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository  extends JpaRepository<ClientLoan, Long> {
    Boolean existsClientLoanByClient(Client client);
}
