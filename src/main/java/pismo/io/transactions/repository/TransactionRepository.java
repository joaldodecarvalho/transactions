package pismo.io.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pismo.io.transactions.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
