package pismo.io.transactions.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pismo.io.transactions.domain.OperationType;

@SpringBootTest
public class OperationTypeRepositoryTest {

	@Autowired
	private OperationTypeRepository repository;

	@Test
	public void save() {

		OperationType operationType = new OperationType();
		operationType.setDescription("PIX");

		OperationType saved = repository.save(operationType);

		Assertions.assertNotNull(saved);
		Assertions.assertNotNull(saved.getId());
		Assertions.assertEquals(saved.getDescription(), operationType.getDescription());
	}

}
