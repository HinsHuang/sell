package com.hins.sell.controller;

import com.hins.sell.config.ProjectUrlConfig;
import com.hins.sell.constant.CookieConstant;
import com.hins.sell.constant.RedisConstant;
import com.hins.sell.dataobject.SellerInfo;
import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.service.SellerService;
import com.hins.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @RequestMapping("/index")
    public ModelAndView login() {
        return new ModelAndView("seller/login");
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse response,
                              Map<String, Object> model) {

        // 1. 验证账号密码
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.put("message", ResultEnum.USER_LOGIN_FAIL.getMessage());
            model.put("returnUrl", "/sell/seller/order/list");
            return new ModelAndView("common/error", model);
        }
        String encrptPassword;
        try {
             encrptPassword = encodeByMD5(password);
        } catch (Exception ex) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        SellerInfo sellerInfo = sellerService.validateLogin(username, encrptPassword);
        if (sellerInfo == null) {
            model.put("message", ResultEnum.USER_LOGIN_FAIL.getMessage());
            model.put("returnUrl", "/sell/seller/order/list");
            return new ModelAndView("common/error", model);
        }

        // 2. 设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        String openId = sellerInfo.getOpenid();
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),
                openId, expire, TimeUnit.SECONDS);

        // 3. 设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        //跳转用绝对路径
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() +"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> model) {
        // 1. 从cookie查询token
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            // 2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            // 3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        model.put("message", ResultEnum.USER_LOGOUT_SUCCESS.getMessage());
        model.put("returnUrl", "/sell/seller/order/list");
        return new ModelAndView("common/success", model);
    }

    private String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定加密算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String encrpStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return encrpStr;
    }
}
