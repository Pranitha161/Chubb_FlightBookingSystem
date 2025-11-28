package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QuestionWrapperTest {

	@Test
	void gettersSettersTest() {
		QuestionWrapper questionWrapper=new QuestionWrapper();
		questionWrapper.setId(1);
		questionWrapper.setQuestionTitle("Question");
		assertEquals(1, questionWrapper.getId());
		assertEquals("Question", questionWrapper.getQuestionTitle());
	}
	@Test
	void testDefaultValues() {
		QuestionWrapper questionWrapper=new QuestionWrapper();
		assertNotNull(questionWrapper);
		assertNull(questionWrapper.getId());
	}
}
