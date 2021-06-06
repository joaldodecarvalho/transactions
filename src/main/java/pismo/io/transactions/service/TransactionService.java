package pismo.io.transactions.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pismo.io.transactions.domain.Transaction;
import pismo.io.transactions.repository.TransactionRepository;
import pismo.io.transactions.validator.TransactionValidator;

@Service
@AllArgsConstructor
public class TransactionService {

	@Getter
	private final TransactionRepository repository;
	private final TransactionValidator validator;

	public Transaction save(Transaction transaction) {

		validator.validate(transaction);

		return repository.save(transaction);
	}

}
