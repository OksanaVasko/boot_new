package com.example.demo;

import com.transaction.demo.Application;
import com.transaction.demo.dto.ResultDTO;
import com.transaction.demo.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    ResultDTO resultDTO = new ResultDTO(new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("90"), new BigDecimal("10"), new Long("2"));
    String expected = "{\"sum\":100,\"avg\":50,\"max\":90,\"min\":10,\"count\":2}";


    @Test
    public void getPaymentsTest() throws Exception {
        Mockito.when(
                paymentService.getPaymensForLastOneMinute(Mockito.any(LocalDateTime.class))).thenReturn(resultDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/statistics").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString()
                , true);

    }

    @Test
    public void addPaymentSuccessfully() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON).content(generateRequestBody(LocalDateTime.now()))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void notAddPaymentIfTimestampIsInPast() throws Exception {
        LocalDateTime timeInPast = LocalDateTime.now().minusSeconds(60);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON).content(generateRequestBody(timeInPast))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void notAddPaymentIfTimestampIsInFuture() throws Exception {
        LocalDateTime timeInFuture = LocalDateTime.now().plusSeconds(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON).content(generateRequestBody(timeInFuture))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    public void deleteAllPaymentsTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/transactions").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    private String generateRequestBody(LocalDateTime timestamp) {
        return "{\"amount\":100,\"timestamp\":\"" + timestamp + "\"}";
    }

}
