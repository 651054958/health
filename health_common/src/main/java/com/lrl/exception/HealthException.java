package com.lrl.exception;

import java.text.MessageFormat;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/26 20:52
 */
public class HealthException extends RuntimeException{
    private static final long serialVersionUID = 1127151684998116941L;
    private String info;

    public HealthException(String message){
        super(message);
        info = message;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return info;
    }

}