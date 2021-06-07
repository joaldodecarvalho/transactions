package pismo.io.transactions.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table
@Data
@Entity
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "document_number")
	private String documentNumber;

	@NotNull
	@Column(name = "avaliable_credit_limit")
	private BigDecimal avaliableCreditLimit;

	public static Account of(Long id) {
		Account account = new Account();
		account.setId(id);

		return account;
	}

}
