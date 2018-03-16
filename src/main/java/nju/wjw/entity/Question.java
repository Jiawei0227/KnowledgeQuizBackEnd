package nju.wjw.entity;

import nju.wjw.vo.QuestionVO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
@Entity
@Table(name="question")
public class Question implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String choices;

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    private String answer;

    private String difficulty;

    private String type;

    public QuestionVO toQuestionVO(){
        QuestionVO questionVO = new QuestionVO();
        questionVO.setDifficulty(this.getDifficulty());
        questionVO.setQuestionDescription(this.getDescription());
        questionVO.setAnswer(Arrays.asList(this.getAnswer().split(":::")));
        questionVO.setChoices(Arrays.asList(this.getChoices().split(":::")));
        questionVO.setType(this.getType());
        return questionVO;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
