package pismo.io.transactions.resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import pismo.io.transactions.domain.Account;
import pismo.io.transactions.repository.AccountRepository;

@WebMvcTest(controllers = AccountResource.class)
public class AccountResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountRepository repository;

	@Test
	@SneakyThrows
	public void save() {

		final Account account = new Account();
		account.setDocumentNumber("102109870");

		given(repository.save(account)).willReturn(account);

		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(account))).andExpect(status().isOk());

		verify(repository, times(1)).save(account);
		verifyNoMoreInteractions(repository);
	}

	@Test
	@SneakyThrows
	public void findById() {

		given(repository.findById(-1L)).willReturn(Optional.of(Account.of(-1L)));

		mockMvc.perform(get("/accounts/-1")).andExpect(status().isOk());

		verify(repository, times(1)).findById(-1L);
		verifyNoMoreInteractions(repository);
	}

	@Test
	@SneakyThrows
	public void avaliableCreditLimit() {

		Account account = Account.of(1L);
		account.setAvaliableCreditLimit(BigDecimal.TEN);

		given(repository.findById(1L)).willReturn(Optional.of(account));

		mockMvc.perform(get("/accounts/avaliable-credit/1")).andExpect(status().isOk());

		verify(repository, times(1)).findById(1L);
	}

}
