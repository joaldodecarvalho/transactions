package pismo.io.transactions.resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import pismo.io.transactions.domain.Transaction;
import pismo.io.transactions.dto.TransactionDTO;
import pismo.io.transactions.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionResourceTest {

	@MockBean
	private static TransactionService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@SneakyThrows
	public void save() {

		final Transaction transaction = new Transaction();
		transaction.setAmount(BigDecimal.TEN);

		final TransactionDTO dto = new TransactionDTO(1L, 1L, BigDecimal.TEN);

		given(service.save(transaction)).willReturn(transaction);

		mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isOk());

		verify(service, times(1)).save(ArgumentMatchers.any(Transaction.class));
		verifyNoMoreInteractions(service);
	}

}
