package cn.anytec.welcome.quadrant.findface;


import cn.anytec.welcome.config.GeneralConfig;
import cn.anytec.welcome.quadrant.pojo.DetectPojo;
import cn.anytec.welcome.quadrant.pojo.FDCameraData;
import cn.anytec.welcome.quadrant.pojo.IdentifyPojo;
import cn.anytec.welcome.util.HttpUtil;
import cn.anytec.welcome.util.ImageUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;


@Component
public class FindFaceRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(FindFaceRunnable.class);
    private static Semaphore semaphore = null;
    private static final ArrayDeque<FDCameraData> dataArrayDeque = new ArrayDeque<>(2);

    @Autowired
    private GeneralConfig config;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private FindFaceService findFaceService;



    @Override
    public void run() {
        if(!semaphore.tryAcquire()){
            return;
        }
        try {
            if(dataArrayDeque.isEmpty())
                return;
            FDCameraData data = dataArrayDeque.poll();
            System.out.println("get:"+data.timestamp);
            IdentifyPojo identifyPojo = findFaceService.imageIdentify(data.mJpgData,null);
            if(identifyPojo == null)
                return;
            identifyPojo.getResults().keySet().forEach((key)->{
                try {
                    List<IdentifyPojo.MatchFace> matchFaces = identifyPojo.getResults().get(key);
                    Map<String,Object> replyInfo = new HashMap<>();
                    if(null != matchFaces && matchFaces.size() > 0){
                        IdentifyPojo.MatchFace matchFace= matchFaces.get(0);
                        if(matchFace.getConfidence() > config.getSdk_api_identify_threshold()){
                            byte[] identified = HttpUtil.getBinaryDataByURL(matchFace.getFace().getPhoto());
                            replyInfo.put("identified",Base64.getEncoder().encodeToString(identified));
                            replyInfo.put("meta",matchFace.getFace().getMeta());
                            simpMessagingTemplate.convertAndSend("/topic/"+data.mStrMac,new JSONObject(replyInfo).toJSONString());
                        }
                    }else {
                        String[] coordinate = key.substring(1,key.length()-1).replace(" ","").split(",");
                        int x1 = Integer.parseInt(coordinate[0]);
                        int y1 = Integer.parseInt(coordinate[1]);
                        int x2 = Integer.parseInt(coordinate[2]);
                        int y2 = Integer.parseInt(coordinate[3]);
                        x1 = x1 < 0 ? 0 : x1;
                        y1 = y1 < 0 ? 0 : y1;
                        byte[] unrecognized = ImageUtil.cutImg(data.mJpgData,"jpeg",x1,y1,x2-x1,y2-y1);
                        replyInfo.put("unrecognized",Base64.getEncoder().encodeToString(unrecognized));
                        simpMessagingTemplate.convertAndSend("/topic/"+data.mStrMac,new JSONObject(replyInfo).toJSONString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }  finally {
            semaphore.release();
            System.out.println("還剩："+semaphore.availablePermits());
        }

    }

    public void setSemaphore(int permits){
        semaphore = new Semaphore(permits);
    }
    public void setData(FDCameraData data){
        if(dataArrayDeque.size() > 1){
            dataArrayDeque.pollLast();
        }
        data.timestamp = System.currentTimeMillis();
        System.out.println("set:"+data.timestamp);
        dataArrayDeque.addFirst(data);
    }

}
