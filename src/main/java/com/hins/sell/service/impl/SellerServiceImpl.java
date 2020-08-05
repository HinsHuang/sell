package com.hins.sell.service.impl;

import com.hins.sell.dataobject.SellerInfo;
import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.repository.SellerInfoRepository;
import com.hins.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findSellerInfoByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public SellerInfo validateLogin(String username, String encrptPassword) {
        SellerInfo sellerInfo = findSellerInfoByUsername(username);
        if (sellerInfo != null && encrptPassword.equals(sellerInfo.getPassword())) {
            return sellerInfo;
        }
        return null;
    }

}
