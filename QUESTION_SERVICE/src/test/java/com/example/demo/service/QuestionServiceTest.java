package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionWrapper;
import com.example.demo.entity.Response;
import com.example.demo.repository.QuestionRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

	@Mock
	private QuestionRepository questionRepo;

	@InjectMocks
	private QuestionService questionService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllQuestions() {
		Question q1 = new Question();
		q1.setId(1);

		when(questionRepo.findAll()).thenReturn(List.of(q1));

		var response = questionService.getAllQuestions();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	void testGetQuestionsByCategory() {
		Question q = new Question();
		q.setId(1);
		q.setCategory("Java");

		when(questionRepo.findByCategory("Java")).thenReturn(List.of(q));

		var response = questionService.getQuestionsByCategory("Java");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	void testAddQuestion() {
		Question q = new Question();
		q.setId(1);

		when(questionRepo.save(q)).thenReturn(q);

		var response = questionService.addQuestion(q);

		assertEquals("success", response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(questionRepo, times(1)).save(q);
	}

	@Test
	void testGetQuestionsForQuiz() {
		Question q1 = new Question();
		q1.setId(100);

		when(questionRepo.findRandomQuestionsByCategory(eq("Java"), any(PageRequest.class))).thenReturn(List.of(q1));

		List<Integer> ids = questionService.getQuestionsForQuiz("Java", 1);

		assertEquals(1, ids.size());
		assertEquals(100, ids.get(0));
	}

	@Test
	void testGetQuestionsFromId() {
		Question q = new Question();
		q.setId(5);
		q.setQuestionTitle("What is Java?");
		q.setOption1("A");
		q.setOption2("B");
		q.setOption3("C");
		q.setOption4("D");

		when(questionRepo.findById(5)).thenReturn(Optional.of(q));

		List<Integer> ids = List.of(5);

		var response = questionService.getQuestionsFromId(ids);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());

		QuestionWrapper wrapper = response.getBody().get(0);
		assertEquals(5, wrapper.getId());
		assertEquals("What is Java?", wrapper.getQuestionTitle());
		assertEquals("A", wrapper.getOption1());
	}

	@Test
	void testGetScore() {

		Response userResponse = new Response();
		userResponse.setId(10);
		userResponse.setResponse("A");

		Question q = new Question();
		q.setId(10);
		q.setRightAnswer("A");

		when(questionRepo.findById(10)).thenReturn(Optional.of(q));

		List<Response> responses = List.of(userResponse);

		var result = questionService.getScore(responses);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(1, result.getBody());
	}
}