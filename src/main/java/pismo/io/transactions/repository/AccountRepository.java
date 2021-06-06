package pismo.io.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pismo.io.transactions.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
