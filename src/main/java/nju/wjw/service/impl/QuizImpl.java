package nju.wjw.service.impl;

import nju.wjw.dao.QuestionDao;
import nju.wjw.dao.RecordDao;
import nju.wjw.entity.Question;
import nju.wjw.entity.Record;
import nju.wjw.service.QuizService;
import nju.wjw.service.util.Difficulty;
import nju.wjw.service.util.UserLevel;
import nju.wjw.util.ResultMsg;
import nju.wjw.util.StatusUtils;
import nju.wjw.vo.LoginStatusVO;
import nju.wjw.vo.QuestionVO;
import nju.wjw.vo.RankListVO;
import nju.wjw.vo.ScoreVO;
import nju.wjw.wxapi.Jscode2session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jerry Wang on 15/03/2018.
 */
@Service
public class QuizImpl implements QuizService{


    @Autowired
    RecordDao recordDao;
    @Autowired
    QuestionDao questionDao;

    @Override
    public String getOpenId(String code) {
        LoginStatusVO loginStatusVO = Jscode2session.getLoginStatus(code);
        return loginStatusVO.getOpenid();

    }

    @Override
    public ResultMsg recordScore(ScoreVO scoreVO) {
        String info = "";
        Record newRecord = new Record();
        newRecord.setOpenId(scoreVO.getOpenId());
        newRecord.setScore(Integer.parseInt(scoreVO.getScore()));
        newRecord.setUserName(scoreVO.getUserName());
        Record record = recordDao.findByOpenId(scoreVO.getOpenId());
        if(record == null){
            newRecord.setCount(1);
            newRecord.setTotalScore(Integer.parseInt(scoreVO.getScore()));
            recordDao.save(newRecord);
            recordDao.flush();
            info = "Insert Record Success";
        }else{
            if(newRecord.getScore()>record.getScore()){
                record.setScore(newRecord.getScore());
                info = "Update Record Success";
            }else{
                info = "Score not high enough, update total score success";
            }
            record.setTotalScore(record.getScore()+Integer.parseInt(scoreVO.getScore()));
            record.setCount(record.getCount()+1);
            recordDao.save(record);
            recordDao.flush();
        }

        return new ResultMsg(StatusUtils.SUCCESS,info);
    }

    @Override
    public RankListVO getRankList(String openId) {
        List<Record> records = recordDao.findFirst50ByOrderByScoreDesc();

        ScoreVO self = new ScoreVO();
        List<ScoreVO> scoreLists = new ArrayList<>();
        boolean isFindSelf = false;
        for(int i=0; i<records.size(); i++){
            if(i>15 && isFindSelf)
                break;
            ScoreVO scoreVO = new ScoreVO();
            scoreVO.setUserName(records.get(i).getUserName());
            scoreVO.setRank(String.valueOf(i+1));
            scoreVO.setScore(String.valueOf(records.get(i).getScore()));
            scoreVO.setOpenId(records.get(i).getOpenId());

            scoreLists.add(scoreVO);

            if (records.get(i).getOpenId().equals(openId)){
                self.setUserName(records.get(i).getUserName());
                self.setRank(String.valueOf(i+1));
                self.setScore(String.valueOf(records.get(i).getScore()));
                self.setOpenId(openId);
                isFindSelf = true;
            }

        }

        RankListVO rankListVO = new RankListVO();
        rankListVO.setScoreVOS(scoreLists);
        rankListVO.setSelf(self);

        return rankListVO;
    }

    @Override
    public List<QuestionVO> generateQuiz(String openId) {

        List<QuestionVO> questionVOS = new ArrayList<>();

        List<Long> easyQuestions = questionDao.searchRandomQuestionIds(Difficulty.easy.name());
        List<Long> normalQuestions = questionDao.searchRandomQuestionIds(Difficulty.normal.name());
        List<Long> hardQuestions = questionDao.searchRandomQuestionIds(Difficulty.hard.name());

        Record record = recordDao.findByOpenId(openId);

        UserLevel userLevel = null;

        if(record == null){
            userLevel = UserLevel.STAR;
        }else{
            if(record.getScore() < 100)
                userLevel = UserLevel.STAR;
            else if (record.getScore() < 250)
                userLevel = UserLevel.MOON;
            else if (record.getScore() < 500)
                userLevel = UserLevel.SUN;
        }

        for(int i=0; i<userLevel.getEasy(); i++) {
            Long easyId = easyQuestions.remove(new Random().nextInt(easyQuestions.size()));
            Question question = questionDao.getOne(easyId);

            questionVOS.add(question.toQuestionVO());
        }

        for(int i=0; i<userLevel.getNormal(); i++) {
            Long normalId = normalQuestions.remove(new Random().nextInt(normalQuestions.size()));
            Question question = questionDao.getOne(normalId);

            questionVOS.add(question.toQuestionVO());
        }

        for(int i=0; i<userLevel.getHard(); i++) {
            Long hardId = hardQuestions.remove(new Random().nextInt(hardQuestions.size()));
            Question question = questionDao.getOne(hardId);

            questionVOS.add(question.toQuestionVO());
        }


        return questionVOS;
    }
}
