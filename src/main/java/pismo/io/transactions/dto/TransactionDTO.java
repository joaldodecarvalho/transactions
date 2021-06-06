package pismo.io.transactions.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDTO {

	@NotNull
	private Long accountId;

	@NotNull
	private Long operationTypeId;

	@NotNull
	private BigDecimal amount;

}
