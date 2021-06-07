package pismo.io.transactions.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import lombok.AllArgsConstructor;
import pismo.io.transactions.domain.Account;
import pismo.io.transactions.repository.AccountRepository;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountResource {

	private final AccountRepository repository;

	@PostMapping
	public ResponseEntity<Long> post(@RequestBody Account account) {

		return ResponseEntity.ok(repository.save(account).getId());
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<Optional<Account>> findById(@PathVariable("accountId") Long id) {

		return ResponseEntity.ok(repository.findById((id)));
	}

	@GetMapping("/avaliable-credit/{accountId}")
	public ResponseEntity<BigDecimal> getAvaliableCredit(@PathVariable("accountId") Long id) {

		BigDecimal avaliableCredit = repository.findById(id).map(Account::getAvaliableCreditLimit)
				.orElseThrow(() -> new RestClientException("Conta não encontrada"));

		return ResponseEntity.ok(avaliableCredit);
	}

}
