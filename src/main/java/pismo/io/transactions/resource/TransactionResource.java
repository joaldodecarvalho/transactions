package pismo.io.transactions.resource;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pismo.io.transactions.domain.Transaction;
import pismo.io.transactions.dto.TransactionDTO;
import pismo.io.transactions.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionResource {

	private final TransactionService service;

	@PostMapping
	public ResponseEntity<Long> post(@RequestBody @Valid TransactionDTO transactionDTO) {

		return ResponseEntity.ok(service.save(Transaction.ofDTO(transactionDTO)).getId());
	}
}
