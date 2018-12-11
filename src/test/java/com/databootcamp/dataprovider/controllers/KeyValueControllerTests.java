package com.databootcamp.dataprovider.controllers;

import com.databootcamp.dataprovider.models.ModelKeyValue;
import com.databootcamp.dataprovider.repositories.KeyValueRepository;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.util.Assert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.UUID;
import static junit.framework.Assert.*;

/*
 * missing check of expected result specified in json body
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = KeyValueController.class)
public class KeyValueControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private KeyValueRepository keyValueRepository;

	@Test
	public void shouldReturnNotFound() throws Exception {
		String uri = "/v1/key/test";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}

	@Test
	public void shouldInsertEntry() throws Exception {
		String postUri = "/v1/key/test/value/toto";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(postUri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void shouldRefuseLongKeys() throws Exception {
		String uuid = UUID.randomUUID().toString();
		String repeatedUUID = IntStream.range(0, 2).mapToObj(i -> uuid).collect(Collectors.joining(""));
		String postUri = "/v1/key/" + repeatedUUID + "/value/titi";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(postUri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(403, status);
	}

	@Test
	public void shouldRefuseEmptyKeys() throws Exception {
		String postUri = "/v1/key//value/titi";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(postUri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}

	@Test
	public void shouldRefuseUnknownUri() throws Exception {
		String postUri = "/v1/keys/ti/val";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(postUri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);

		String getUri = "/v1/keysss";
		mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);

	}

}
