package com.hins.sell.controller;

import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;
import java.net.URLEncoder;

@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    //获取微信code
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        String url = "http://hins.natapp4.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, returnUrl);
        log.info("【微信网页授权】获取code. result={}", redirectUrl);
        log.info("returnUrl={}", returnUrl);
        return "redirect:" + redirectUrl;
    }

    //根据微信code获取openid后重定向回主页
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                            @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】");
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("returnUrl={}", returnUrl);
        log.info("openid={}", openId);
        return "redirect:" + returnUrl + "#/?openid=" + openId;
    }

}
