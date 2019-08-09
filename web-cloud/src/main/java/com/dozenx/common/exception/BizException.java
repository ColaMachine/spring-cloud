package com.dozenx.common.exception;

import com.dozenx.common.exception.MyException;

/**
 * Created by dozen.zhang on 2017/2/24.
 */
public class BizException extends MyException {
    public BizException(String code, String msg) {
        super(code,msg);

    }
    public BizException(int code, String msg) {
        super(code,msg);

    }


}
