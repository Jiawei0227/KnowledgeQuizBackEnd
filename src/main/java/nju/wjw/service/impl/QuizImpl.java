package nju.wjw.service.impl;

import nju.wjw.dao.EveryQuizRecordDao;
import nju.wjw.dao.QuestionDao;
import nju.wjw.dao.RecordDao;
import nju.wjw.dao.WeekRankingDao;
import nju.wjw.entity.EveryQuizRecord;
import nju.wjw.entity.Question;
import nju.wjw.entity.Record;
import nju.wjw.entity.WeekRanking;
import nju.wjw.service.QuizService;
import nju.wjw.service.util.Difficulty;
import nju.wjw.service.util.UserLevel;
import nju.wjw.util.ResultMsg;
import nju.wjw.util.StatusUtils;
import nju.wjw.vo.*;
import nju.wjw.wxapi.Jscode2session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
    @Autowired
    EveryQuizRecordDao everyQuizRecordDao;
    @Autowired
    WeekRankingDao weekRankingDao;
    @Autowired
    Jscode2session jscode2session;

    @Override
    public LoginStatusVO getOpenId(String code) {
        LoginStatusVO loginStatusVO = jscode2session.getLoginStatus(code);
        return loginStatusVO;

    }

    @Override
    public ResultMsg recordScore(ScoreVO scoreVO) {
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
        }else{
            if(newRecord.getScore()>record.getScore()){
                record.setScore(newRecord.getScore());
            }
            record.setTotalScore(record.getTotalScore()+Integer.parseInt(scoreVO.getScore()));
            record.setCount(record.getCount()+1);
            recordDao.save(record);
            recordDao.flush();
        }

        Calendar c=Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        WeekRanking weekRanking = weekRankingDao.getByOpenIdAndYearAndWeek(scoreVO.getOpenId(),year,week);
        if(weekRanking == null){
            System.out.println("yessss");
            WeekRanking newweekRanking = new WeekRanking();
            newweekRanking.setOpenId(scoreVO.getOpenId());
            newweekRanking.setYear(year);
            newweekRanking.setUserName(scoreVO.getUserName());
            newweekRanking.setWeek(week);
            newweekRanking.setTotalScore(Integer.parseInt(scoreVO.getScore()));
            weekRankingDao.save(newweekRanking);
        }else{
            weekRanking.setTotalScore(weekRanking.getTotalScore()+Integer.parseInt(scoreVO.getScore()));
            weekRankingDao.flush();
        }


        return new ResultMsg(StatusUtils.SUCCESS,this.getRankingInfo(scoreVO.getOpenId()));
    }

    @Override
    public ResultMsg recordQuizContent(ScoreContentVO scoreContentVO) {
        StringBuilder questionList = new StringBuilder();
        StringBuilder choiceList = new StringBuilder();
        for(QuestionChoice e: scoreContentVO.getChoices()){
            questionList.append(e.getId());
            questionList.append(":::");

            choiceList.append(e.getChoice());
            choiceList.append(":::");
        }
        EveryQuizRecord everyQuizRecord = new EveryQuizRecord();
        everyQuizRecord.setQuestions(questionList.toString());
        everyQuizRecord.setChoices(choiceList.toString());
        everyQuizRecord.setScore(scoreContentVO.getScore());
        everyQuizRecord.setTimestamp(new Timestamp(System.currentTimeMillis()));
        everyQuizRecord.setOpenId(scoreContentVO.getOpenId());
        everyQuizRecordDao.save(everyQuizRecord);

        return new ResultMsg(StatusUtils.SUCCESS,"SUCCESS "+everyQuizRecord.getId());
    }

    @Override
    public RankListVO getWeekList(String openId) {
        Calendar c=Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        List<WeekRanking> weekRankings = weekRankingDao.findFirst10ByYearAndWeekOrderByTotalScoreDesc(year,week);

        List<ScoreVO> scoreLists = new ArrayList<>();
        boolean isFindSelf = false;
        for(int i=0; i<weekRankings.size(); i++){

            ScoreVO scoreVO = new ScoreVO();
            scoreVO.setUserName(weekRankings.get(i).getUserName());
            scoreVO.setRank(String.valueOf(i+1));
            scoreVO.setScore(String.valueOf(weekRankings.get(i).getTotalScore()));
            scoreVO.setOpenId(weekRankings.get(i).getOpenId());

            scoreLists.add(scoreVO);

            if (weekRankings.get(i).getOpenId().equals(openId)){
                isFindSelf = true;
            }

           // System.out.println(weekRankings.get(i).getOpenId() + "  "+ openId);

        }

        RankListVO rankListVO = new RankListVO();
        rankListVO.setScoreVOS(scoreLists);
        rankListVO.setReward(isFindSelf);

        return rankListVO;

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
            if(record.getTotalScore() < 1000)
                userLevel = UserLevel.STAR;
            else if (record.getTotalScore() < 2000)
                userLevel = UserLevel.MOON;
            else if (record.getTotalScore() < 3000)
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

    @Override
    public SelfRankingVO getRankingInfo(String openId) {
        SelfRankingVO selfRankingVO = new SelfRankingVO();
        Record record = recordDao.findByOpenId(openId);
        if(record == null){
            selfRankingVO.setRank(UserLevel.STAR.name());
            selfRankingVO.setStar(0);
        }else{
            selfRankingVO = UserLevel.getLevel(record.getTotalScore());
        }
        selfRankingVO.setOpenId(openId);
        return selfRankingVO;
    }
}
