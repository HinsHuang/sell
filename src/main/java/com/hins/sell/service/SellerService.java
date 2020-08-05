package com.hins.sell.service;

import com.hins.sell.dataobject.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo findSellerInfoByUsername(String username);

    SellerInfo validateLogin(String username, String encrptPassword);

}
