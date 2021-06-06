package pismo.io.transactions.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pismo.io.transactions.domain.Account;
import pismo.io.transactions.domain.OperationType;
import pismo.io.transactions.domain.Transaction;

@SpringBootTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void save() {

		Transaction transaction = new Transaction();
		transaction.setAccount(entityManager.find(Account.class, 1L));
		transaction.setOperationType(entityManager.find(OperationType.class, 4L));
		transaction.setEventDate(LocalDateTime.now());
		transaction.setAmount(BigDecimal.TEN);

		Transaction saved = repository.save(transaction);

		Assertions.assertNotNull(saved);
		Assertions.assertNotNull(saved.getId());

		Assertions.assertNotNull(saved.getAccount());
		Assertions.assertEquals(saved.getAccount().getId(), 1L);

		Assertions.assertNotNull(saved.getOperationType());
		Assertions.assertEquals(saved.getOperationType().getId(), 4L);

		Assertions.assertNotNull(saved.getAmount());
		Assertions.assertNotNull(saved.getEventDate());
	}

}
