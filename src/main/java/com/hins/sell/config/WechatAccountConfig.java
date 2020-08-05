package com.hins.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatAccountConfig {

    /**
     * 测试公众号
     */
    private String mpAppidHins;

    private String mpAppSecretHins;

    /**
     * 公司公众号
     */
    private String mpAppid;

    private String mpAppSecret;

    /**
     * 公司开放号
     */
    private String openAppid;

    private String openAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;

    /**
     * 微信模板消息
     */
    private Map<String, String> templateId;

}
