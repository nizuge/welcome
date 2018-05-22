package cn.anytec.welcome.quadrant.findface;

import cn.anytec.welcome.config.GeneralConfig;
import cn.anytec.welcome.quadrant.pojo.DetectPojo;
import cn.anytec.welcome.quadrant.pojo.IdentifyPojo;
import cn.anytec.welcome.quadrant.pojo.VerifyPojo;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FindFaceImpl implements FindFaceService{
    private static Logger logger = LoggerFactory.getLogger(FindFaceService.class);
    private static Gson gson = new Gson();

    @Autowired
    GeneralConfig config;

    @Override
    public DetectPojo imageDetect(byte[] photo){

        HttpResponse response;
        HttpEntity entity;

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().addBinaryBody("photo",photo, ContentType.DEFAULT_BINARY, "photo");
        entity = multipartEntityBuilder.build();
        try {
            response = Request.Post("http://"+config.getHost_ip() +":"+config.getSdk_port()+ "/"+config.getSdk_version()+"/detect")
                    .connectTimeout(10000)
                    .socketTimeout(30000)
                    .addHeader("Authorization", "Token " + config.getToken())
                    .body(entity)
                    .execute().returnResponse();
            String reply = EntityUtils.toString(response.getEntity());
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                return gson.fromJson(reply,DetectPojo.class);
            }else {
                logger.warn("请求未正确响应：" + responseCode);
                logger.warn(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VerifyPojo imagesVerify(byte[] photo1, byte[] photo2){

        HttpResponse response;
        HttpEntity entity;

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().addBinaryBody("photo1",photo1, ContentType.DEFAULT_BINARY, "photo1");
        multipartEntityBuilder.addBinaryBody("photo2",photo2, ContentType.DEFAULT_BINARY, "photo2");
        entity = multipartEntityBuilder.build();
        try {
            response = Request.Post("http://"+config.getHost_ip() +":"+config.getSdk_port()+ "/"+config.getSdk_version()+"/verify")
                    .connectTimeout(10000)
                    .socketTimeout(30000)
                    .addHeader("Authorization", "Token " + config.getToken())
                    .body(entity)
                    .execute().returnResponse();
            String reply = EntityUtils.toString(response.getEntity());
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                return gson.fromJson(reply,VerifyPojo.class);
            }else {
                logger.warn("请求未正确响应：" + responseCode);
                logger.warn(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public IdentifyPojo imageIdentify(byte[] photo, DetectPojo.FaceInfo face) {
        HttpResponse response;
        HttpEntity entity;

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().addBinaryBody("photo",photo, ContentType.DEFAULT_BINARY, "photo");
        if(face != null){
            multipartEntityBuilder.addTextBody("bbox",face.toBbox());
        }else {
            multipartEntityBuilder.addTextBody("mf_selector","biggest");
        }
        entity = multipartEntityBuilder.build();
        try {
            response = Request.Post("http://"+config.getHost_ip() + ":" +config.getSdk_port()+ "/"+config.getSdk_version()+"/identify")
                    .connectTimeout(10000)
                    .socketTimeout(30000)
                    .addHeader("Authorization", "Token " + config.getToken())
                    .body(entity)
                    .execute().returnResponse();
            String reply = EntityUtils.toString(response.getEntity());
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                return gson.fromJson(reply,IdentifyPojo.class);
            }else {
                logger.warn("请求未正确响应：" + responseCode);
                logger.warn(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
