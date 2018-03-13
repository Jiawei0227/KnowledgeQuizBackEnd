package nju.wjw.controller;

import nju.wjw.service.ImportQuestionService;
import nju.wjw.util.ResultMsg;
import nju.wjw.util.StatusUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
@RestController
public class ImportQuestionController {

    private static Log log = LogFactory.getLog(ImportQuestionController.class);
    @Autowired
    private ImportQuestionService excelReaderService;


    @RequestMapping(value = "/hellotest",method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResultMsg deliveryMailByBatch(){
        return new ResultMsg(1,"Success");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/importQuestions", method = RequestMethod.POST)
    public ResultMsg importQuestion(@RequestParam MultipartFile file) throws IOException {
        ResultMsg re = null;
        log.info("ExcelMgmt start to import Question.");
        //判断文件是否为空
        if(file == null)
            return new ResultMsg(StatusUtils.NO_EXCEL_FILE_DETACTED,"No excel file detacted");
        //获取文件名
        String name=file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name == null || ("").equals(name) && size==0)
            return new ResultMsg(StatusUtils.NO_EXCEL_FILE_DETACTED,"No excel file detacted");;

        //批量导入。参数：文件名，文件。
        re = excelReaderService.readExcelQuestionList(file,name);
        return re;
    }

}
