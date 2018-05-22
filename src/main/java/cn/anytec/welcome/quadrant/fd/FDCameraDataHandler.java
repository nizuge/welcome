package cn.anytec.welcome.quadrant.fd;

import cn.anytec.welcome.config.GeneralConfig;
import cn.anytec.welcome.quadrant.findface.FindFaceRunnable;
import cn.anytec.welcome.quadrant.pojo.FDCameraData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FDCameraDataHandler {

    private ThreadLocal<Integer> counts = new ThreadLocal<>();
    private ExecutorService dataThreadPool = null;
    @Autowired
    private GeneralConfig config;
    @Autowired
    private FindFaceRunnable findFaceRunnable;

    public void init(){
        dataThreadPool = Executors.newFixedThreadPool(config.getData_thread(), (runnable)-> {
            Thread thread = new Thread(runnable);
            thread.setPriority(5);
            thread.setDaemon(true);
            return thread;
        });
        findFaceRunnable.setSemaphore(config.getData_thread());
    }


    public void OnCameraData(FDCameraData data) {
        Integer count = counts.get();
        if(count == null) {
            count = 1;
        }else {
            count++;
        }
        if(count == config.getData_threshold()) {
            count = 0;
            findFaceRunnable.setData(data);
            dataThreadPool.execute(findFaceRunnable);
        }
        counts.set(count);
    }

}
