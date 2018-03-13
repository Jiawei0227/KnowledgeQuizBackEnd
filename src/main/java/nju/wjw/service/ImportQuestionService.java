package nju.wjw.service;

import nju.wjw.util.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
public interface ImportQuestionService {

    public ResultMsg readExcelQuestionList(MultipartFile f , String fileName);

}
