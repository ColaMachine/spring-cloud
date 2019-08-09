package com.dozenx.common.util;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 9:51 2018/7/4
 * @Modified By:
 */
public enum FileType {
    ZIP("zip"),UNKNOWN("")
    ;
    String type;
     FileType(String typeName){
        type=typeName;
    }
}
