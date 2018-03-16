package nju.wjw.vo;

import java.util.List;

/**
 * Created by Jerry Wang on 15/03/2018.
 */
public class RankListVO {

    private ScoreVO self;

    private List<ScoreVO> scoreVOS;

    public ScoreVO getSelf() {
        return self;
    }

    public void setSelf(ScoreVO self) {
        this.self = self;
    }

    public List<ScoreVO> getScoreVOS() {
        return scoreVOS;
    }

    public void setScoreVOS(List<ScoreVO> scoreVOS) {
        this.scoreVOS = scoreVOS;
    }
}
