package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QuizTest {
	@Test
	void gettersSettersTest() {
		Quiz quiz=new Quiz();
		quiz.setId(1);
		quiz.setTitle("Hello");
		assertEquals(1, quiz.getId());
		assertEquals("Question", quiz.getTitle());
	}
	@Test
	void testDefaultValues() {
		Quiz quiz=new Quiz();
		assertNotNull(quiz);
		assertNull(quiz.getId());
	}
}
