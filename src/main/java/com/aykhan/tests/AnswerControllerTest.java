package com.aykhan.tests;

import com.aykhan.controller.AnswerController;
import com.aykhan.entities.Question;
import com.aykhan.repository.AnswerRepository;
import com.aykhan.repository.QuestionRepository;
import com.aykhan.services.ResultSendingService;
import com.aykhan.services.implementations.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
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
    private MyUserDetailsService userDetailsService;
    @MockBean
    private ResultSendingService mailService;
    @MockBean
    private UserResultsService userResultsService;

    private List<Question> q_storage = new ArrayList<>(Arrays.asList(
            new Question(1, "q1", "s1", Collections.emptyList(), 0),
            new Question(2, "q2", "s2", Collections.emptyList(), 0)
    ));

    @Test
    public void checkMyAnswers() {
        when(questionRepository.findById(Mockito.anyInt()))
                .then(i -> {
                    int id = i.getArgument(0);
                    return q_storage.stream()
                            .filter(q -> q.getId() == id)
                            .findAny();
                });

        when(subjectService.getByName(Mockito.anyString()))
                .thenReturn(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/check")
                .content("{\n\t\"anws\": [{\n\t\t\"question_id\": 1,\n\t\t\"answer_number\": 0\n\t}],\n\t\"ans_weight\": 1\n}")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
        try {
            MvcResult res = mockMvc.perform(request)
                    .andReturn();
            String content = res.getResponse().getContentAsString();
            String ex = "1";
            assertThat(content).isEqualTo(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}