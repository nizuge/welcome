package cn.anytec.welcome.controller;



import cn.anytec.welcome.config.GeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MainController{

    @Autowired
    GeneralConfig generalConfig;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value = "/test")
    public String test(Map<String,String> map){
        map.put("test",generalConfig.getTest());
        return "index";
    }

    @RequestMapping(value = "/ws/test")
    @ResponseBody
    public void ws(@RequestParam("info")String info){

        simpMessagingTemplate.convertAndSend("/topic/greetings",info);
    }

}
