package nju.wjw.service.util;

/**
 * Created by Jerry Wang on 13/03/2018.
 */

import nju.wjw.exception.QuestionAnswerNotMatchException;
import nju.wjw.vo.QuestionVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExcelUtils {
    public static final String QUESTION_LIST = "QUESTION_LIST";

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ExcelUtils(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    // @描述：是否是2003的excel，返回true是2003
    private static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    private static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }


    /**
     * 读EXCEL文件，获取客户信息集合
     */
    public List getExcelInfo(String fileName, MultipartFile Mfile, String type) throws QuestionAnswerNotMatchException {

        //初始化客户信息的集合
        List customerList = new ArrayList<>();
        //初始化输入流
        InputStream is = null;
        try {
            //验证文件名是否合格
            if (!validateExcel(fileName)) {
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = Mfile.getInputStream();
            //根据excel里面的内容读取信息
            if (type.equals(QUESTION_LIST))
                customerList = getQuestionExcelInfo(is, isExcel2003);

            is.close();
        }catch (QuestionAnswerNotMatchException ex){
            throw ex;
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return customerList;
    }


    /**
     * 根据excel里面的内容读取客户信息
     * @param is 输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public  List<QuestionVO> getQuestionExcelInfo(InputStream is, boolean isExcel2003) throws QuestionAnswerNotMatchException {
        List<QuestionVO> customerList=null;
        try{
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            //当excel是2003时
            if(isExcel2003){
                wb = new HSSFWorkbook(is);
            }
            else{//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面客户的信息
            customerList=readQuestionExcelValue(wb);
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
        return customerList;
    }


    /**
     * 读取Excel里面客户的信息
     * @param wb
     * @return
     */
    private List<QuestionVO> readQuestionExcelValue(Workbook wb) throws QuestionAnswerNotMatchException {
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<QuestionVO> questionVOList=new ArrayList<QuestionVO>();
        QuestionVO questionVO;
        //循环Excel行数,从第二行开始。标题不入库
        for(int r=1;r<totalRows;r++){
            Row row = sheet.getRow(r);
            if (row == null) continue;
            questionVO = new QuestionVO();

            //循环Excel的列
            for(int c = 0; c <this.totalCells; c++){
                Cell cell = row.getCell(c);
                if (null != cell){
                    if(c==0){
                        questionVO.setQuestionDescription(cell.getStringCellValue());
                    }else if(c==1){
                        String[] choices = cell.getStringCellValue().split(":::");
                        questionVO.setChoices(Arrays.asList(choices));
                    }else if(c==2){
                        String[] answers = cell.getStringCellValue().split(":::");
                        for (String answer: answers) {
                            if(questionVO.getChoices().contains(answer))
                                continue;
                            else
                                throw new QuestionAnswerNotMatchException(answer+" does not find match choice with question "+questionVO.getQuestionDescription());
                        };
                        if(answers.length == 1)
                            questionVO.setType("Single");
                        else
                            questionVO.setType("Multiple");
                        questionVO.setAnswer(Arrays.asList(answers));
                    }else if(c==3){
                        questionVO.setDifficulty(cell.getStringCellValue());
                    }
                }
            }
            if(questionVO.getQuestionDescription()!=null)
                questionVOList.add(questionVO);
        }
        return questionVOList;
    }

}
