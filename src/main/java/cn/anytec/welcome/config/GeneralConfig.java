package cn.anytec.welcome.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeneralConfig {

    @Value("${config.test}")
    private String test;
    //FindFace SDK
    @Value("${sdk.host_ip}")
    private String host_ip;
    @Value("${sdk.port}")
    private short sdk_port;
    @Value("${sdk.token}")
    private String token;
    @Value("${sdk.version}")
    private String sdk_version;
    //SDK_API
    @Value("${sdk.api.identify.threshold}")
    private double sdk_api_identify_threshold;

    //data control
    @Value("${data.threshold}")
    private int data_threshold;
    @Value("${data.thread}")
    private int data_thread;
    @Value("${data.draw}")
    private int data_draw;

    public String getTest() {
        return test;
    }

    public String getHost_ip() {
        return host_ip;
    }

    public short getSdk_port() {
        return sdk_port;
    }

    public String getToken() {
        return token;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public int getData_threshold() {
        return data_threshold;
    }

    public int getData_thread() {
        return data_thread;
    }

    public double getSdk_api_identify_threshold() {
        return sdk_api_identify_threshold;
    }

    public int getData_draw() {
        return data_draw;
    }
}
