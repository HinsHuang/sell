package com.hins.sell.handler;

import com.hins.sell.enums.ResultEnum;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.exceptions.SellerAuthorizeException;
import com.hins.sell.utils.ResultVOUtil;
import com.hins.sell.viewobject.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    //拦截登录异常
    @ExceptionHandler(SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("seller/login");
    }

    //拦截订单异常
    @ExceptionHandler(SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(Exception ex) {
        if (ex instanceof SellException) {
            SellException sellException = (SellException) ex;
            return ResultVOUtil.error(sellException.getCode(), sellException.getMessage());
        }
        return ResultVOUtil.error(ResultEnum.UNKNOW_ERROR.getCode(), ResultEnum.UNKNOW_ERROR.getMessage());
    }
}
