package ca.gbc.userentity;

import ca.gbc.userentity.dto.UserRequest;
import ca.gbc.userentity.model.User;
import ca.gbc.userentity.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserEntityApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository repository;


	UserRequest getUserRequest() {
		return UserRequest.builder()
				.username("taikai")
				.email("taikai@domain.com")
				.password("1201")
				.build();
	}

	private List<User> getUserList() {
		List<User> userList = new ArrayList<>();
		UUID uuid = UUID.randomUUID();

		User user = User.builder()
				.id((int) uuid.timestamp())
				.username("taikai")
				.email("taikai@domain.com")
				.password("1201")
				.build();

		userList.add(user);
		return userList;
	}

	@Test
	void createUser() throws Exception {
		UserRequest userRequest = getUserRequest();
		String userRequestString = objectMapper.writeValueAsString(userRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userRequestString))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		System.out.println(repository.findAll().size());

		Assertions.assertTrue(repository.findAll().size() > 0);
	}

	@Test
	void getUserById() throws Exception {

		repository.saveAll(getUserList());


		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
				.get("/api/user")
				.accept(MediaType.APPLICATION_JSON));

		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andDo(MockMvcResultHandlers.print());

		MvcResult result = response.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);

		int actualSize = jsonNodes.size();
		int expectedSize = getUserList().size();

		assertEquals(expectedSize, actualSize);
	}

	@Test
	void updateUser() throws Exception {

		Integer id = Math.toIntExact(UUID.randomUUID().timestamp());
		// Prepare saved user
		User savedUser = User.builder()
				.id(id)
				.username("linh")
				.email("linh@domain.com")
				.password("1105")
				.build();

		repository.save(savedUser);

		savedUser.setUsername("Testing integration");
		String userRequestString = objectMapper.writeValueAsString(savedUser);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
				.put("/api/user/" + savedUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(userRequestString));

		response.andExpect(MockMvcResultMatchers.status().isNoContent());
		response.andDo(MockMvcResultHandlers.print());

		User query = User.builder().id(id).build();
		Optional<User> dbUser = repository.findOne(Example.of(query));
		Assertions.assertTrue(dbUser.isPresent());
		Assertions.assertEquals("Testing integration", dbUser.get().getUsername());
	}

	@Test
	void deleteUser() throws Exception {
		// Prepare saved user
		User savedUser = User.builder()
				.id((int) UUID.randomUUID().timestamp())
				.username("taikai")
				.email("taikai@domain.com")
				.password("1201")
				.build();

        repository.save(savedUser);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/user/" + savedUser.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(MockMvcResultMatchers.status().isNoContent());
		response.andDo(MockMvcResultHandlers.print());
	}

}
