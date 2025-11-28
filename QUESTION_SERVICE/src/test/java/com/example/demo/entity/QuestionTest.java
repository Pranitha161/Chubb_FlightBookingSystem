package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
public class QuestionTest {
	@Test
	void gettersSettersTest(){
		Question q=new Question();
		q.setId(1);
		q.setCategory("ComputerScience");
		q.setDifficultylevel("hard");
		assertEquals(1, q.getId());
		assertEquals("ComputerScience", q.getCategory());
		assertEquals("hard", q.getDifficultylevel());
	}
	@Test
	void testDefaults() {
		Question q=new Question();
		assertNotNull(q);
		assertNull(q.getDifficultylevel());
		assertNull(q.getCategory());
	}
}
