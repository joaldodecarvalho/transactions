package pismo.io.transactions.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pismo.io.transactions.domain.Account;
import pismo.io.transactions.domain.Transaction;
import pismo.io.transactions.repository.AccountRepository;
import pismo.io.transactions.repository.TransactionRepository;
import pismo.io.transactions.validator.TransactionValidator;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	private static TransactionService service;

	private static TransactionRepository repository = mock(TransactionRepository.class);
	private static TransactionValidator validator = mock(TransactionValidator.class);
	private static AccountRepository accountRepository = mock(AccountRepository.class);

	@BeforeAll
	public static void setup() {

		service = new TransactionService(repository, validator, accountRepository);
	}

	@Test
	public void save() {

		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAmount(BigDecimal.TEN);
		transaction.setAccount(Account.of(1L));

		service.save(transaction);

		verify(repository, times(1)).save(transaction);
		verify(validator, times(1)).validate(transaction);
	}

	@Test
	public void changeAvaliableCreditAccount() {

		Transaction transaction = new Transaction();
		transaction.setAmount(BigDecimal.TEN);

		Account account = new Account();
		account.setId(1L);
		account.setAvaliableCreditLimit(BigDecimal.ONE);
		transaction.setAccount(account);

		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

		// Transaction saved = service.save(transaction);

		// assertEquals(saved.getAccount().getAvaliableCreditLimit(), new
		// BigDecimal(11));
		verify(accountRepository, times(1)).save(account);
	}

}
