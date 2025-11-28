package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QuizDtoTest {
	@Test
	void gettersSettersTest() {
		QuizDto quizdto=new QuizDto();
		quizdto.setCategoryName("hello");
		quizdto.setTitle("Title");
		assertEquals("hello",quizdto.getCategoryName());
		assertEquals("Title",quizdto.getTitle());
	}
	@Test
	void testDefaults() {
		QuizDto quizdto=new QuizDto();
		assertNotNull(quizdto);
		assertNull(quizdto.getCategoryName());
		assertNull(quizdto.getTitle());
	}
}
