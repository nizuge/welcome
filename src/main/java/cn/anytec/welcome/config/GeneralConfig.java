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
    //data control
    @Value("${data.threshold}")
    private int data_threshold;
    @Value("${data.thread}")
    private int data_thread;

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
}
