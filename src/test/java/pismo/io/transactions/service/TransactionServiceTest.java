package pismo.io.transactions.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pismo.io.transactions.domain.Transaction;
import pismo.io.transactions.repository.TransactionRepository;
import pismo.io.transactions.validator.TransactionValidator;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	private static TransactionService service;

	private static TransactionRepository repository = mock(TransactionRepository.class);
	private static TransactionValidator validator = mock(TransactionValidator.class);

	@BeforeAll
	public static void setup() {

		service = new TransactionService(repository, validator);
	}

	@Test
	public void save() {

		Transaction transaction = new Transaction();
		transaction.setId(1L);

		service.save(transaction);

		verify(repository, times(1)).save(transaction);
		verify(validator, times(1)).validate(transaction);
	}

}
