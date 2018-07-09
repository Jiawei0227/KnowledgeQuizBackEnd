package nju.wjw.service;

import nju.wjw.util.ResultMsg;
import nju.wjw.vo.*;

import java.util.List;

/**
 * Created by Jerry Wang on 15/03/2018.
 */
public interface QuizService {

    public LoginStatusVO getOpenId(String code);

    public ResultMsg recordScore(ScoreVO scoreVO);

    public ResultMsg recordQuizContent(ScoreContentVO scoreContentVO);

    public RankListVO getRankList(String openId);

    public RankListVO getWeekList(String openId);

    public List<QuestionVO> generateQuiz(String openId);

    public SelfRankingVO getRankingInfo(String openId);

}
