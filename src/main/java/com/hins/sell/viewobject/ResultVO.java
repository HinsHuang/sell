package com.hins.sell.viewobject;

import lombok.Data;

import javax.jnlp.IntegrationService;

@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

}
