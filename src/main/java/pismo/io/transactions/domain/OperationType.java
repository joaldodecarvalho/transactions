package pismo.io.transactions.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "operation_type")
public class OperationType {

	private static final Long PAGAMENTO = 4L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String description;

	public static OperationType of(Long id) {
		OperationType operationType = new OperationType();
		operationType.setId(id);
		return operationType;
	}

	@JsonIgnore
	public Boolean isPagamento() {

		return PAGAMENTO.equals(id);

	}

}
