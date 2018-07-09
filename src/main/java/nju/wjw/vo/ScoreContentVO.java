package nju.wjw.vo;

import java.util.List;

/**
 * Created by Jerry Wang on 19/04/2018.
 */
public class ScoreContentVO {

    private String openId;

    private List<QuestionChoice> choices;

    private String score;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
