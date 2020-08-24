package com.evan.miaosha.exception;

import com.evan.miaosha.result.CodeMsg;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-02
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
