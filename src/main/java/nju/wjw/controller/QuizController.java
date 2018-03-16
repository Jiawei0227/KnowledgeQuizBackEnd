package nju.wjw.controller;

import nju.wjw.service.QuizService;
import nju.wjw.util.ResultMsg;
import nju.wjw.util.StatusUtils;
import nju.wjw.vo.ScoreVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jerry Wang on 15/03/2018.
 */
@RestController
public class QuizController {

    private static Log log = LogFactory.getLog(QuizController.class);
    @Autowired
    private QuizService quizService;

    @RequestMapping(value = "/getOpenId",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResultMsg getOpenId(@RequestParam String code){
        return new ResultMsg(StatusUtils.SUCCESS, quizService.getOpenId(code));
    }

    @RequestMapping(value = "/getQuizContent",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResultMsg getQuizContent(@RequestParam String openId){
        return new ResultMsg(1,"Success");
    }

    @RequestMapping(value = "/submitScore",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResultMsg submitScore(@RequestBody ScoreVO scoreVO){
        return quizService.recordScore(scoreVO);
    }

    @RequestMapping(value = "/getAllRankList",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResultMsg getAllRankList(String openId){

        return new ResultMsg(StatusUtils.SUCCESS,quizService.getRankList(openId));
    }

    @RequestMapping(value = "/generateQuiz",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResultMsg generateQuiz(@RequestParam String openId){

        return new ResultMsg(StatusUtils.SUCCESS, quizService.generateQuiz(openId));
    }

}
