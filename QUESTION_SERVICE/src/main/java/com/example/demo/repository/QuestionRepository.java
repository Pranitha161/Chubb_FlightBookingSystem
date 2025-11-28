package com.example.demo.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory (String category);
    @Query(value = "SELECT * FROM question WHERE category = ?1 ORDER BY RAND()", 
 	       nativeQuery = true)
 	List<Question> findRandomQuestionsByCategory(String category, Pageable pageable);
    


}
