package com.hins.sell.viewobject;

import lombok.Data;

import javax.jnlp.IntegrationService;
import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 7241212423406731493L;

    private Integer code;

    private String msg;

    private T data;

}
