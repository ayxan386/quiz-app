package com.aykhan.tests;

import com.aykhan.entities.Question;
import com.aykhan.repository.AnswerRepository;
import com.aykhan.repository.MyUserRepository;
import com.aykhan.repository.QuestionRepository;
import com.aykhan.repository.SubjectRepository;
import com.aykhan.services.implementations.AnswerService;
import com.aykhan.services.implementations.MyUserDetailsService;
import com.aykhan.services.implementations.QuestionService;
import com.aykhan.services.implementations.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestControllerTest {

    @Autowired
    private QuestionService questionService;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerRepository answerRepository;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private SubjectRepository subjectRepository;
    @MockBean
    private MyUserDetailsService userDetailsService;
    @MockBean
    private MyUserRepository userRepository;

    private ArrayList<Question> storage = new ArrayList<>(Arrays.asList(
            new Question(1, "q1", "s1", Collections.emptyList(), 0),
            new Question(2, "q2", "s2", Collections.emptyList(), 0)
    ));

    @Test
    public void add() {
        Question q = new Question(3, "q3", "s1", Collections.emptyList(), 0);
        when(questionRepository.save(Mockito.any(Question.class))).then(invocationOnMock -> {
            storage.add(q);
            return null;
        });
        when(
                questionRepository.findAll()
        )
                .thenReturn(storage);
        questionService.saveOne(q);
        long size = StreamSupport.stream(questionService.getAll().spliterator(), false).count();
        assertEquals(3, size);
    }

    @Test
    public void getAllNoSubject() {
        when(
                questionRepository.findAll()
        )
                .thenReturn(storage);

        long size = StreamSupport.stream(questionService.getAll().spliterator(), false).count();
        assertEquals(2, size);
    }

    @Test
    public void getAll() {
        String subject = "s2";
        when(
                questionRepository.findBySubject(Mockito.anyString())
        ).thenReturn(
                storage.stream()
                        .filter(
                                question -> question.getSubject().equalsIgnoreCase(subject)
                        )
                        .collect(Collectors.toList())
        );

        List<Question> grouped = questionService.getAll(subject);

        assertEquals(1, grouped.size());
    }
}