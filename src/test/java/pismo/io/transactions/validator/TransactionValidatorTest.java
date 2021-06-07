package pismo.io.transactions.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import pismo.io.transactions.domain.Account;
import pismo.io.transactions.domain.OperationType;
import pismo.io.transactions.domain.Transaction;

@ExtendWith(MockitoExtension.class)
public class TransactionValidatorTest {

	private static TransactionValidator validator;

	private static EntityManager entityManager = mock(EntityManager.class);

	@BeforeAll
	public static void setup() {

		validator = new TransactionValidator(entityManager);
	}

	@Test
	public void validAccount() {

		Transaction transaction = new Transaction();
		transaction.setAccount(Account.of(1L));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> validator.validate(transaction));

		assertEquals(exception.getReason(), "Não foi encontrado a conta 1");
	}

	@Test
	public void validOperationType() {

		when(entityManager.find(Account.class, 1L)).thenReturn(new Account());

		Transaction transaction = new Transaction();
		transaction.setAccount(Account.of(1L));
		transaction.setOperationType(OperationType.of(1L));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> validator.validate(transaction));

		assertEquals(exception.getReason(), "Não foi encontrado a operação 1");
	}

	@Test
	public void validAmountByOperationNegative() {

		when(entityManager.find(Account.class, 1L)).thenReturn(new Account());
		when(entityManager.find(OperationType.class, 1L)).thenReturn(new OperationType());

		Transaction transaction = new Transaction();
		transaction.setAccount(Account.of(1L));
		transaction.setOperationType(OperationType.of(1L));
		transaction.setAmount(BigDecimal.TEN);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> validator.validate(transaction));

		assertEquals(exception.getReason(), "Para transações de saque ou compra o valor deve ser negativo");
	}

	@Test
	public void validAmountByOperationPositive() {

		when(entityManager.find(Account.class, 1L)).thenReturn(new Account());
		when(entityManager.find(OperationType.class, 4L)).thenReturn(new OperationType());

		Transaction transaction = new Transaction();
		transaction.setAccount(Account.of(1L));
		transaction.setOperationType(OperationType.of(4L));
		transaction.setAmount(BigDecimal.TEN.negate());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> validator.validate(transaction));

		assertEquals(exception.getReason(), "Para transações de pagamento o valor deve ser positivo");
	}

	@Test
	public void validateAvaliableCredit() {

		Account account = Account.of(1L);
		account.setAvaliableCreditLimit(BigDecimal.ONE);

		OperationType operationType = OperationType.of(1L);
		when(entityManager.find(OperationType.class, 1L)).thenReturn(operationType);

		when(entityManager.find(Account.class, 1L)).thenReturn(account);

		Transaction transaction = new Transaction();
		transaction.setAccount(Account.of(1L));
		transaction.setOperationType(operationType);
		transaction.setAmount(BigDecimal.TEN.negate());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> validator.validate(transaction));

		assertEquals(exception.getReason(), "O valor da transação deve ser menor ou igual ao crédito da conta.");
	}

}
