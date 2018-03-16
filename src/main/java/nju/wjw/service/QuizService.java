package nju.wjw.service;

import nju.wjw.util.ResultMsg;
import nju.wjw.vo.QuestionVO;
import nju.wjw.vo.RankListVO;
import nju.wjw.vo.ScoreVO;

import java.util.List;

/**
 * Created by Jerry Wang on 15/03/2018.
 */
public interface QuizService {

    public String getOpenId(String code);

    public ResultMsg recordScore(ScoreVO scoreVO);

    public RankListVO getRankList(String openId);

    public List<QuestionVO> generateQuiz(String openId);

}
