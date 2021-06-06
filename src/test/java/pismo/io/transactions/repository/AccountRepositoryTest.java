package pismo.io.transactions.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pismo.io.transactions.domain.Account;

@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;

	@Test
	public void save() {

		Account account = new Account();
		account.setDocumentNumber("068897098505");

		Account saved = repository.save(account);

		Assertions.assertNotNull(saved);
		Assertions.assertNotNull(saved.getId());
		Assertions.assertEquals(saved.getDocumentNumber(), account.getDocumentNumber());
	}

}
