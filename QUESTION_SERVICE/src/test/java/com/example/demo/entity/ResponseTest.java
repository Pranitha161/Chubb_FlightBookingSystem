package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ResponseTest {
	@Test
	void gettersSettersTest() {
		Response response=new Response();
		response.setId(1);
		response.setResponse("yes");
		assertEquals(1, response.getId());
		assertEquals("yes",response.getResponse());
	}
	@Test
	void testDefaults() {
		Response response=new Response();
		assertNotNull(response);
		assertNull(response.getId());
		assertNull(response.getResponse());
	}
}
