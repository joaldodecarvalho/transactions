package pismo.io.transactions.validator;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import pismo.io.transactions.domain.Account;
import pismo.io.transactions.domain.OperationType;
import pismo.io.transactions.domain.Transaction;

@Component
@AllArgsConstructor
public class TransactionValidator {

	private final EntityManager entityManager;

	public void validate(Transaction transaction) {

		validAccount(transaction.getAccount().getId());
		validOperationType(transaction.getOperationType().getId());
		validAmountByOperation(transaction);
	}

	private void validAccount(Long accountId) {

		Optional.ofNullable(entityManager.find(Account.class, accountId)).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado a conta " + accountId));
	}

	private void validOperationType(Long operationTypeId) {

		Optional.ofNullable(entityManager.find(OperationType.class, operationTypeId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Não foi encontrado a operação " + operationTypeId));
	}

	private void validAmountByOperation(Transaction transaction) {

		Boolean amountIsPositive = transaction.amountIsPositive();
		Boolean isPagamento = transaction.getOperationType().isPagamento();

		if (amountIsPositive && !isPagamento) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Para transações de saque ou compra o valor deve ser negativo");
		} else if (!amountIsPositive && isPagamento) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Para transações de pagamento o valor deve ser positivo");
		}

	}

}
