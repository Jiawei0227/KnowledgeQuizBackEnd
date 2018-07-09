package nju.wjw.vo;

import java.util.List;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
public class QuestionVO {

    private String questionId;

    private String questionDescription;

    private String difficulty;

    private String type;

    private List<String> choices;

    private List<String> answer;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getChoicesString(){
        return String.join(":::",choices);
    }

    public String getAnswerString(){
        return String.join(":::",answer);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }
}
