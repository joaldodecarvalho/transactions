package pismo.io.transactions.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pismo.io.transactions.dto.TransactionDTO;

@Table
@Data
@Entity
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "operation_type_id")
	private OperationType operationType;

	@NotNull
	private BigDecimal amount;

	@NotNull
	@Column(name = "event_date")
	private LocalDateTime eventDate;

	public static Transaction ofDTO(TransactionDTO dto) {

		Transaction transaction = new Transaction();
		transaction.setAccount(Account.of(dto.getAccountId()));
		transaction.setOperationType(OperationType.of(dto.getOperationTypeId()));
		transaction.setAmount(dto.getAmount());
		transaction.setEventDate(LocalDateTime.now());

		return transaction;
	}

	@JsonIgnore
	public Boolean amountIsPositive() {

		return BigDecimal.ZERO.compareTo(amount) < 1;

	}

	@JsonIgnore
	public Boolean isPagamento() {

		return Optional.ofNullable(operationType).map(OperationType::isPagamento).orElse(Boolean.FALSE);

	}

}
