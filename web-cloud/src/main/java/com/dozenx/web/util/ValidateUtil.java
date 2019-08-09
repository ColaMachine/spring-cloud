package com.dozenx.web.util;

import com.dozenx.common.exception.ValidException;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 版权所有：bean验证工具类
 * 项目名称:kaqm
 * 创建者: 宋展辉
 * 创建日期: 2015年7月10日
 * 文件说明: 
 */
public class ValidateUtil<T> {
    Logger logger =LoggerFactory.getLogger(ValidateUtil.class);
    public Map<String, Rule[]> ruleMap = null;
    public Map<String, String> nameMap = null;
    public Map<String, Object> valueMap = null;

    public ValidateUtil() {
        ruleMap = new HashMap<String,Rule[]>();
        nameMap = new HashMap<String, String>();
        valueMap = new HashMap<String, Object>();
    }

    public Map<String, Object> validate(String key, String value, String name) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Rule[] rules = ruleMap.get(key);
        if(rules!=null && rules.length>0){
            for(Rule rule : rules){
                rule.setValue(value);
                if(!rule.valid()){
                    resultMap.put("result", false);
                    resultMap.put("message", name+rule.getMessage());
                    break;
                }
            }
        }
        return resultMap;
    }
    
    public void init(){
        ruleMap = new HashMap<String,Rule[]>();
    }

    /**
     * 添加参数规则
     * @author zhangzhiwei
     * @param key
     * @param value
     * @param name
     * @param ruleArr
     */
    public void add(String key, Object value, String name, Rule[] ruleArr){
        nameMap.put(key, name);
        valueMap.put(key, value);
        ruleMap.put(key, ruleArr);
    }
/*    public void add(String key, String value, String name, Rule[] ruleArr){
        nameMap.put(key, name);
        valueMap.put(key, value);
        ruleMap.put(key, ruleArr);
    }*/

    
    public Map<String, Object> validateAll() throws Exception{
        Map<String, Object> resultMap = null;
        for (Entry<String, Rule[]> entry : ruleMap.entrySet()) {
            String key = entry.getKey();
            resultMap = validate(key);
            boolean result = (Boolean)resultMap.get("result");
            if(!result){
                resultMap.put("result", result);
                break;
            }
        }
        return resultMap;
    }
    
    
    public String validateString() throws Exception{
        String message = null;
        Map<String, Object> resultMap = null;
        for (Entry<String, Rule[]> entry : ruleMap.entrySet()) {
            String key = entry.getKey();
            resultMap = validate(key);
            boolean result = (Boolean)resultMap.get("result");
            if(!result){

                message = (String)resultMap.get("message");
                logger.error(message);

                break;
            }
        }
        this.clear();

        return message;
    }
    
    public Map<String, Object> validate(String key) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", true);
        Rule[] rules = ruleMap.get(key);
        if(rules!=null && rules.length>0){
            for(Rule rule : rules){
               // rule.setValue(valueMap.get(key));
                if(!rule.valid(valueMap.get(key))){
                    resultMap.put("result", false);
                    resultMap.put("message", nameMap.get(key)+rule.getMessage());
                    break;
                }
            }
        }
        return resultMap;
    }

    public ResultDTO  validate(String name ,String value ,String cnName,Rule rule ) throws Exception{
        ResultDTO result =new ResultDTO();


            rule.setValue(value);
            if(!rule.valid()){
                result.setR(302);
                result.setMsg(rule.getMessage());
                return result;
            }


        return result;
    }
    
    public void clear(){
        //ruleMap.clear();
        //nameMap.clear();
        //valueMap.clear();
        ruleMap = null;
        nameMap = null;
        valueMap = null;
    }

    /**
     * 
     */
    private static final Logger log = LoggerFactory.getLogger(ValidateUtil.class);
    /**
     * @param object 对象
     * @return  ValidateResult
     */
  /*  public ValidateResult valid (T object){
        ValidateResult vr = new ValidateResult();
        vr.setStatus(true);
        //bean 验证
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        java.util.Set<ConstraintViolation<T>> constraintViolations = validator
                .validate(object);
        String errormsg ="";
        Map<String,String> errormap =vr.getErrorMap();
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                //getMessage的内容为属性上配置的message，getPropertyPath的内容为属性名
                //因为在处理的过程中同一个属性可能会报出多个错误，因此还是使用message名作为key来处理错误集合
                errormap.put(constraintViolation.getMessage(),constraintViolation.getPropertyPath().toString() );
                errormsg+=constraintViolation.getMessage()+";";
                log.info("新增"+object.getClass().getName()+"表单验证未通过:"+errormsg);
            }
            vr.setStatus(false);
            vr.setMsg(errormsg);
        }
        return vr;
    }*/
    
    
   
    /**
     * 说明:主要是继承jsr303 hibernate 的基于bean注解的bean校验
     * @param object
     * @return
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2015年12月12日下午4:28:52
     */
//    public ResultDTO valid (T object){
//        ResultDTO result =new ResultDTO();
//        result.setR(1);
//        //bean 验证
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        java.util.Set<ConstraintViolation<T>> constraintViolations = validator
//                .validate(object);
//        String errormsg ="";
//        Map<String,String> errormap =new HashMap();;
//        if (!constraintViolations.isEmpty()) {
//
//            result.setR(300);
//            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
//                //getMessage的内容为属性上配置的message，getPropertyPath的内容为属性名
//                //因为在处理的过程中同一个属性可能会报出多个错误，因此还是使用message名作为key来处理错误集合
//                errormap.put(constraintViolation.getPropertyPath().toString(),
//                        ErrorMessage.getErrorMsg(constraintViolation
//                                .getMessage()));
//                log.info("新增"+object.getClass().getName()+"表单验证未通过:"+errormsg);
//            }
//            result.setData(errormap);
//            result.setMsg("参数校验失败");
//        }
//        return result;
//    }

    public static void valid(Object value,String name,Rule[] rules) throws Exception {
        ValidateUtil vu =new ValidateUtil();
        vu.add(name,value,name,rules);
        String validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            throw new ValidException("302",validStr);
        }
    }


    public static void valid(Object value,String name,Rule rule) throws Exception {
        rule.setValue(value);
        if(!rule.valid()){
            throw new ValidException("30405110",rule.getMessage());
        }
    }

}
