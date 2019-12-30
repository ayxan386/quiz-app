package com.aykhan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JsonIgnore
  private Question question;

  @Column(name = "content")
  private String content;

  public Answer(int id, String content) {
    this.question = new Question(id, null, null, null, -1);
    this.content = content;
  }
}
