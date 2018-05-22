package cn.anytec.welcome.quadrant.findface;


import cn.anytec.welcome.config.GeneralConfig;
import cn.anytec.welcome.quadrant.pojo.DetectPojo;
import cn.anytec.welcome.quadrant.pojo.FDCameraData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.Semaphore;


@Component
public class FindFaceRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(FindFaceRunnable.class);
    private static Semaphore semaphore = null;
    private static final ArrayDeque<FDCameraData> dataArrayDeque = new ArrayDeque<>(2);

    @Autowired
    private FindFaceService findFaceService;
    @Autowired
    private GeneralConfig config;


    @Override
    public void run() {
        if(!semaphore.tryAcquire()){
            return;
        }
        try {
            if(dataArrayDeque.isEmpty())
                return;
            FDCameraData data = dataArrayDeque.poll();
            System.out.println("get:"+data.mStrMac);
            DetectPojo detectPojo = findFaceService.imageDetect(data.mJpgData);
            if(detectPojo != null){
                List<DetectPojo.FaceInfo> faceList = detectPojo.getFaces();
            }
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    public void setSemaphore(int permits){
        semaphore = new Semaphore(permits);
    }
    public void setData(FDCameraData data){
        synchronized (dataArrayDeque){
            if(dataArrayDeque.size() > 1){
                dataArrayDeque.pollLast();
            }
            data.mStrMac = System.currentTimeMillis()+"";
            System.out.println("set:"+data.mStrMac);
            dataArrayDeque.addFirst(data);
        }
    }
}
