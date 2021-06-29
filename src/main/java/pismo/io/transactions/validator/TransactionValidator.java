package pismo.io.transactions.validator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import pismo.io.transactions.domain.Account;
import pismo.io.transactions.domain.OperationType;
import pismo.io.transactions.domain.Transaction;
import rx.Observable;

@Component
@AllArgsConstructor
public class TransactionValidator {

	private final EntityManager entityManager;

	public List<Observable<Object>> validate(Transaction transaction) {

		Observable<Object> validAccount = validAccount(transaction.getAccount().getId());
		Observable<Object> validOperationType = validOperationType(transaction.getOperationType().getId());
		Observable<Object> validAmountByOperation = validAmountByOperation(transaction);
		Observable<Object> validCreditLimit = validCreditLimit(transaction);

		return Arrays.asList(validAccount, validOperationType, validAmountByOperation, validCreditLimit);
	}

	private Observable<Object> validAccount(Long accountId) {

		return Observable.just(accountId).flatMap(id -> Optional.ofNullable(entityManager.find(Account.class, id))
				.map(a -> Observable.empty()).orElse(Observable.error(
						new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado a conta " + accountId))));
	}

	private Observable<Object> validCreditLimit(Transaction transaction) {

		return Observable.just(transaction).flatMap(t -> {

			Account account = entityManager.find(Account.class, t.getAccount().getId());

			t.setAccount(account);

			if (!transaction.isPagamento()
					&& account.getAvaliableCreditLimit().add(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
				return Observable.error(new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
						"O valor da transação deve ser menor ou igual ao crédito da conta."));
			} else {
				return Observable.empty();
			}
		});

	}

	private Observable<Object> validOperationType(Long operationTypeId) {

		return Observable.just(operationTypeId)
				.flatMap(id -> Optional.ofNullable(entityManager.find(OperationType.class, id))
						.map(a -> Observable.empty())
						.orElse(Observable.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
								"Não foi encontrado a operação " + operationTypeId))));
	}

	private Observable<Object> validAmountByOperation(Transaction transaction) {

		return Observable.just(transaction).flatMap(t -> {

			Boolean amountIsPositive = t.amountIsPositive();
			Boolean isPagamento = t.getOperationType().isPagamento();

			if (amountIsPositive && !isPagamento) {
				return Observable.error(new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Para transações de saque ou compra o valor deve ser negativo"));
			} else if (!amountIsPositive && isPagamento) {
				return Observable.error(new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Para transações de pagamento o valor deve ser positivo"));
			} else {
				return Observable.empty();
			}
		});

	}

}
