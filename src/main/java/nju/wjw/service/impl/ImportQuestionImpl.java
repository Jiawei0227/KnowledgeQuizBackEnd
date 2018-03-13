package nju.wjw.service.impl;

import nju.wjw.dao.QuestionDao;
import nju.wjw.entity.Question;
import nju.wjw.exception.QuestionAnswerNotMatchException;
import nju.wjw.service.ImportQuestionService;
import nju.wjw.service.util.ExcelUtils;
import nju.wjw.util.ResultMsg;
import nju.wjw.util.StatusUtils;
import nju.wjw.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
@Service
public class ImportQuestionImpl implements ImportQuestionService {

    @Autowired
    QuestionDao questionDao;

    @Override
    public ResultMsg readExcelQuestionList(MultipartFile f, String fileName) {
        //创建处理EXCEL
       ExcelUtils readExcel=new ExcelUtils();
        //解析excel，获取问题信息集合。
        List<QuestionVO> questionVOS;
        try {
            questionVOS = readExcel.getExcelInfo(fileName ,f, ExcelUtils.QUESTION_LIST);
        } catch (QuestionAnswerNotMatchException e) {
            return new ResultMsg(StatusUtils.FAILED, e.getMessage());
        }

        for (QuestionVO q: questionVOS) {
            Question question = new Question();
            question.setDescription(q.getQuestionDescription());
            question.setChoices(q.getChoicesString());
            question.setAnswer(q.getAnswerString());
            question.setType(q.getType());
            question.setDifficulty(q.getDifficulty());

            questionDao.save(question);
        }


        return new ResultMsg(StatusUtils.SUCCESS,questionVOS);
    }
}
