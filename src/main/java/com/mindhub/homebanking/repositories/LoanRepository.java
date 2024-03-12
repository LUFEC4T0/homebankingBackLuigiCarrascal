package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Boolean existsByName(String name);

    Loan findByName(String name);
    Boolean existsByNameAndPayments(String name, Integer payments);
}
