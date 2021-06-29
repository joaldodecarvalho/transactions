package pismo.io.transactions.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.AllArgsConstructor;
import pismo.io.transactions.domain.Account;
import pismo.io.transactions.repository.AccountRepository;
import rx.Observable;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountResource {

	private final AccountRepository repository;

	@PostMapping
	public DeferredResult<Long> post(@RequestBody Account account) {

		DeferredResult<Long> output = new DeferredResult<>();
		Observable.just(repository.save(account)).subscribe(a -> {
			output.setResult(a.getId());
		});
		return output;
	}

	@GetMapping("/{accountId}")
	public DeferredResult<Optional<Account>> findById(@PathVariable("accountId") Long id) {

		DeferredResult<Optional<Account>> output = new DeferredResult<>();
		Observable.just(repository.findById(id)).subscribe(a -> {
			output.setResult(a);
		});
		return output;
	}

	@GetMapping("/avaliable-credit/{accountId}")
	public DeferredResult<BigDecimal> getAvaliableCredit(@PathVariable("accountId") Long id) {

		DeferredResult<BigDecimal> output = new DeferredResult<>();
		Observable.just(repository.findById(id)).subscribe(a -> {
			output.setResult(a.map(Account::getAvaliableCreditLimit)
					.orElseThrow(() -> new RestClientException("Conta não encontrada")));
		});
		return output;
	}

}
