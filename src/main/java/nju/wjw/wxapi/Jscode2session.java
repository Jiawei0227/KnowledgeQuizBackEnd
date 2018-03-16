package nju.wjw.wxapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import nju.wjw.vo.LoginStatusVO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Created by Jerry Wang on 16/03/2018.
 */
public class Jscode2session {

    @Value("${appinfo.appId}")
    private static String appId;

    @Value("${appinfo.appSecret}")
    private static String appSecret;

    private static String loginURL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public static LoginStatusVO getLoginStatus(String code){
        String finalUrl = String.format(loginURL,appId,appSecret,code);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LoginStatusVO loginStatusVO = null;
        try {
            loginStatusVO = objectMapper.readValue(response.body().toString(), LoginStatusVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginStatusVO;

    }

}
