package com.aykhan.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "question")
  private String question;

  @Column(name = "subject")
  private String subject;

  @OneToMany(cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      mappedBy = "question")
  private List<Answer> options;

  @Column(name = "correct")
  private int answer;

  public Question(Question q, int i) {
    this.id = q.id;
    this.question = q.getQuestion();
    this.subject = q.getSubject();
    this.options = q.getOptions();
    this.answer = i;
  }
}
