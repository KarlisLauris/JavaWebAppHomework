package io.karlis.homework;

import io.karlis.homework.controllers.CalculatorController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class HomeworkApplicationTests {
	@Value("${api.key}")
	private String apiKey;
	@Autowired
	private CalculatorController controller;

	@Autowired
	private MockMvc mockMvc;
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
	@Test
	public void shouldNotAllowToUseWithoutAuthorization() throws Exception {
		this.mockMvc.perform(post("/calculator/short")).andExpect(status().isUnauthorized());
	}
	@Test
	public void doesNotAllowToSendEmptyRequest() throws Exception {
		this.mockMvc.perform(post("/calculator/short").header("ApiKey", apiKey)).andDo(print()).andExpect(status().isBadRequest());
	}
	@Test
	public void doesAllowToSendRequestWithCorrectEquation() throws Exception {
		this.mockMvc.perform(post("/calculator/short").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"1+1\"}")).andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void doesNotAllowToSendRequestWithIncorrectEquation() throws Exception {
		this.mockMvc.perform(post("/calculator/short").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"1+1+\"}")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void allowsOnlyShortEquations() throws Exception {
		this.mockMvc.perform(post("/calculator/short").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"1+1+1\"}")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void allowsLongEquations() throws Exception {
		this.mockMvc.perform(post("/calculator/long").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"1+1+4+5*-5\"}")).andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void canStartWithMinus() throws Exception {
		this.mockMvc.perform(post("/calculator/short").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"-1+1\"}")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void returnsCorrectResult() throws Exception {
		this.mockMvc.perform(post("/calculator/short").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"1*-1\"}")).andDo(print()).andExpect(status().isOk()).andExpect(result -> containsString("-1"));
	}
	@Test
	public void considersPriority() throws Exception {
		this.mockMvc.perform(post("/calculator/long").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"equation\": \"1+1*2\"}")).andDo(print()).andExpect(status().isOk()).andExpect(result -> containsString("3"));
	}

	@Test
	public void returnsCorrectHighLow() throws Exception {
		this.mockMvc.perform(post("/calculator/high-low").contentType(MediaType.APPLICATION_JSON).header("ApiKey", apiKey).content("{\"numbers\": [1,2,3,4,5,6,7,8,9,10]}")).andDo(print()).andExpect(status().isOk()).andExpect(result -> containsString("10,1"));
	}
}
