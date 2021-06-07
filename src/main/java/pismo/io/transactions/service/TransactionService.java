package pismo.io.transactions.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pismo.io.transactions.domain.Account;
import pismo.io.transactions.domain.Transaction;
import pismo.io.transactions.repository.AccountRepository;
import pismo.io.transactions.repository.TransactionRepository;
import pismo.io.transactions.validator.TransactionValidator;

@Service
@AllArgsConstructor
public class TransactionService {

	@Getter
	private final TransactionRepository repository;
	private final TransactionValidator validator;
	private final AccountRepository accountRepository;

	public Transaction save(Transaction transaction) {

		validator.validate(transaction);

		repository.save(transaction);

		afterSave(transaction);

		return transaction;
	}

	private void afterSave(Transaction transaction) {

		Account account = transaction.getAccount();

		account.setAvaliableCreditLimit(Optional.ofNullable(account.getAvaliableCreditLimit()).orElse(BigDecimal.ZERO)
				.add(transaction.getAmount()));

		accountRepository.save(account);
	}

}
