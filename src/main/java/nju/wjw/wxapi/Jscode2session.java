package nju.wjw.wxapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import nju.wjw.vo.LoginStatusVO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Jerry Wang on 16/03/2018.
 */
@Component
public class Jscode2session {

    @Value("${appinfo.appid}")
    private String appId;

    @Value("${appinfo.appsecret}")
    private String appSecret;

    private String loginURL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public LoginStatusVO getLoginStatus(String code){
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
            //String test = "{\"session_key\":\"UeSIth7ryc1sAPoREsaeUA==\",\"openid\":\"oyYbD5HB-eoURM6QRnlYptlTAlPU\",\"unionid\":\"onVrk1R7_VHAYdL0dGDwl7-7ym7g\"}";
            String result = response.body().string();
            System.out.println(result);
            loginStatusVO = objectMapper.readValue(result, LoginStatusVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginStatusVO;

    }

    public static void main(String args[]){
        String test = "{\"session_key\":\"UeSIth7ryc1sAPoREsaeUA==\",\"openid\":\"oyYbD5HB-eoURM6QRnlYptlTAlPU\",\"unionid\":\"onVrk1R7_VHAYdL0dGDwl7-7ym7g\"}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginStatusVO user = mapper.readValue(test, LoginStatusVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
