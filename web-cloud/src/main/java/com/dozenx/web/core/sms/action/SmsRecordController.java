/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.sms.action;

import com.dozenx.common.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.sms.bean.SmsRecord;
import com.dozenx.web.core.sms.service.SmsRecordService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;



@Controller
@RequestMapping("/smsRecord")
public class SmsRecordController extends BaseController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SmsRecordController.class);
    /** 权限service **/
    @Autowired
    private SmsRecordService smsRecordService;
    
    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/SmsRecordList.html";
    }

 
    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
            params.put("phone",phone);
        }
        String phoneLike = request.getParameter("phoneLike");
        if(!StringUtil.isBlank(phoneLike)){
            params.put("phoneLike",phoneLike);
        }
        String systemNo = request.getParameter("systemNo");
        if(!StringUtil.isBlank(systemNo)){
            params.put("systemNo",systemNo);
        }
        String systemNoLike = request.getParameter("systemNoLike");
        if(!StringUtil.isBlank(systemNoLike)){
            params.put("systemNoLike",systemNoLike);
        }
        String sendTime = request.getParameter("sendTime");
        if(!StringUtil.isBlank(sendTime)){
            if(StringUtil.checkNumeric(sendTime)){
                params.put("sendTime",sendTime);
            }else if(StringUtil.checkDateStr(sendTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String sendTimeBegin = request.getParameter("sendTimeBegin");
        if(!StringUtil.isBlank(sendTimeBegin)){
            if(StringUtil.checkNumeric(sendTimeBegin)){
                params.put("sendTimeBegin",sendTimeBegin);
            }else if(StringUtil.checkDateStr(sendTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String sendTimeEnd = request.getParameter("sendTimeEnd");
        if(!StringUtil.isBlank(sendTimeEnd)){
            if(StringUtil.checkNumeric(sendTimeEnd)){
                params.put("sendTimeEnd",sendTimeEnd);
            }else if(StringUtil.checkDateStr(sendTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = request.getParameter("contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String reason = request.getParameter("reason");
        if(!StringUtil.isBlank(reason)){
            params.put("reason",reason);
        }
        String reasonLike = request.getParameter("reasonLike");
        if(!StringUtil.isBlank(reasonLike)){
            params.put("reasonLike",reasonLike);
        }

        params.put("page",page);
        List<SmsRecord> smsRecords = smsRecordService.listByParams4Page(params);
        return ResultUtil.getResult(smsRecords, page);
    }
    
    
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SmsRecordEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SmsRecordView.html";
    }
   
      @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
    String id = request.getParameter("id");
HashMap<String,Object> result =new HashMap<String,Object>();
if(!StringUtil.isBlank(id)){
    SmsRecord bean = smsRecordService.selectByPrimaryKey(Integer.valueOf(id));
    result.put("bean", bean);
}
return this.getResult(result);



    
      /*  String id = request.getParameter("id");
        SmsRecord bean = smsRecordService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap result =new HashMap();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:保存角色信息
     * 
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        SmsRecord smsRecord =new  SmsRecord();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            smsRecord.setId(Integer.valueOf(id)) ;
        }
        
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
            smsRecord.setPhone(String.valueOf(phone)) ;
        }
        
        String systemNo = request.getParameter("systemNo");
        if(!StringUtil.isBlank(systemNo)){
            smsRecord.setSystemNo(String.valueOf(systemNo)) ;
        }
        
        String sendTime = request.getParameter("sendTime");
        if(!StringUtil.isBlank(sendTime)){
            smsRecord.setSendTime(Timestamp.valueOf(sendTime)) ;
        }
        
        String content = request.getParameter("content");
        if(!StringUtil.isBlank(content)){
            smsRecord.setContent(String.valueOf(content)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            smsRecord.setStatus(Byte.valueOf(status)) ;
        }
        
        String reason = request.getParameter("reason");
        if(!StringUtil.isBlank(reason)){
            smsRecord.setReason(String.valueOf(reason)) ;
        }
        */
String id = request.getParameter("id");
if(!StringUtil.isBlank(id)){
    smsRecord.setId(Integer.valueOf(id));
}
String phone = request.getParameter("phone");
if(!StringUtil.isBlank(phone)){
    smsRecord.setPhone(phone);
}
String systemNo = request.getParameter("systemNo");
if(!StringUtil.isBlank(systemNo)){
    smsRecord.setSystemNo(systemNo);
}
String sendTime = request.getParameter("sendTime");
if(!StringUtil.isBlank(sendTime)){
    if(StringUtil.checkNumeric(sendTime)){
        smsRecord.setSendTime(Timestamp.valueOf(sendTime));
    }else if(StringUtil.checkDateStr(sendTime, "yyyy-MM-dd HH:mm:ss")){
        smsRecord.setSendTime(new Timestamp( DateUtil.parseToDate(sendTime, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String content = request.getParameter("content");
if(!StringUtil.isBlank(content)){
    smsRecord.setContent(content);
}
String status = request.getParameter("status");
if(!StringUtil.isBlank(status)){
    smsRecord.setStatus(Byte.valueOf(status));
}
String reason = request.getParameter("reason");
if(!StringUtil.isBlank(reason)){
    smsRecord.setReason(reason);
}

        //valid
ValidateUtil vu = new ValidateUtil();
String validStr="";
vu.add("id", id, "id",  new Rule[]{new Digits(10,0)});
vu.add("phone", phone, "手机号码",  new Rule[]{new Length(11),new NotEmpty(),new PhoneRule()});
vu.add("systemNo", systemNo, "系统名称",  new Rule[]{new Length(13),new NotEmpty(),new AlphaRule()});
vu.add("sendTime", sendTime, "发送时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"),new NotEmpty()});
vu.add("content", content, "内容",  new Rule[]{new Length(200),new NotEmpty()});
vu.add("status", status, "发送状态",  new Rule[]{});
vu.add("reason", reason, "失败原因",  new Rule[]{new Length(200)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return smsRecordService.save(smsRecord);
       
    }
    
    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        smsRecordService.delete(id);
        return this.getResult(SUCC);
    }
     /**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public Object multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        Integer idAry[]=new Integer[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
            vu.add("id", id, "id",  new Rule[]{new Digits(10,0)});

            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }
            
            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Integer.valueOf(idStrAry[i]);
        }
       return  smsRecordService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody   
    public Object exportExcel(HttpServletRequest request){
       HashMap<String,Object> params= new HashMap<String,Object>();
String id = request.getParameter("id");
if(!StringUtil.isBlank(id)){
    params.put("id",id);
}
String phone = request.getParameter("phone");
if(!StringUtil.isBlank(phone)){
    params.put("phone",phone);
}
String phoneLike = request.getParameter("phoneLike");
if(!StringUtil.isBlank(phoneLike)){
    params.put("phoneLike",phoneLike);
}
String systemNo = request.getParameter("systemNo");
if(!StringUtil.isBlank(systemNo)){
    params.put("systemNo",systemNo);
}
String systemNoLike = request.getParameter("systemNoLike");
if(!StringUtil.isBlank(systemNoLike)){
    params.put("systemNoLike",systemNoLike);
}
String sendTime = request.getParameter("sendTime");
if(!StringUtil.isBlank(sendTime)){
    if(StringUtil.checkNumeric(sendTime)){
        params.put("sendTime",sendTime);
    }else if(StringUtil.checkDateStr(sendTime, "yyyy-MM-dd HH:mm:ss")){
        params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTime, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String sendTimeBegin = request.getParameter("sendTimeBegin");
if(!StringUtil.isBlank(sendTimeBegin)){
    if(StringUtil.checkNumeric(sendTimeBegin)){
        params.put("sendTimeBegin",sendTimeBegin);
    }else if(StringUtil.checkDateStr(sendTimeBegin, "yyyy-MM-dd HH:mm:ss")){
        params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String sendTimeEnd = request.getParameter("sendTimeEnd");
if(!StringUtil.isBlank(sendTimeEnd)){
    if(StringUtil.checkNumeric(sendTimeEnd)){
        params.put("sendTimeEnd",sendTimeEnd);
    }else if(StringUtil.checkDateStr(sendTimeEnd, "yyyy-MM-dd HH:mm:ss")){
        params.put("sendTime",new Timestamp( DateUtil.parseToDate(sendTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String content = request.getParameter("content");
if(!StringUtil.isBlank(content)){
    params.put("content",content);
}
String contentLike = request.getParameter("contentLike");
if(!StringUtil.isBlank(contentLike)){
    params.put("contentLike",contentLike);
}
String status = request.getParameter("status");
if(!StringUtil.isBlank(status)){
    params.put("status",status);
}
String reason = request.getParameter("reason");
if(!StringUtil.isBlank(reason)){
    params.put("reason",reason);
}
String reasonLike = request.getParameter("reasonLike");
if(!StringUtil.isBlank(reasonLike)){
    params.put("reasonLike",reasonLike);
}

        // 查询list集合
        List<SmsRecord> list =smsRecordService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "id");
        colTitle.put("phone", "手机号码");
        colTitle.put("systemNo", "系统名称");
        colTitle.put("sendTime", "发送时间");
        colTitle.put("content", "内容");
        colTitle.put("status", "发送状态");
        colTitle.put("reason", "失败原因");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SmsRecord sm = list.get(i);
            HashMap map = new HashMap();
            map.put("id",  list.get(i).getId());
            map.put("phone",  list.get(i).getPhone());
            map.put("systemNo",  list.get(i).getSystemNo());
            map.put("sendTime",  list.get(i).getSendTime());
            map.put("content",  list.get(i).getContent());
            map.put("status",  list.get(i).getStatus());
            map.put("reason",  list.get(i).getReason());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, "导出成功", fileName);
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");
    
    }
    @RequestMapping(value = "/import.json")
    public void importExcel(){
        
    }

    @RequestMapping(value = "/test")
    public void test(){
//        YunpianClient clnt = new YunpianClient("apikey").init();
//
////发送短信API
//        Map<String, String> param = clnt.newParam(2);
//        param.put(YunpianClient.MOBILE, "13958173965");
//        param.put(YunpianClient.TEXT, "【云片网】您的验证码是1234");
//        Result<SmsSingleSend> r = clnt.sms().single_send(param);
////获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
//        System.out.println(r.getCode());
////账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
//
////释放clnt
//        clnt.close();
    }

    public static void main(String args[]){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", "f95d47767b4f6773898beaef2930ee32");
        params.put("text", "【云片网】您的验证码是1234");
        params.put("mobile", "13958173965");
       String content = MapUtils.join(params, "=", "&");
        try {
            System.out.println(HttpsConnection.doPost("https://sms.yunpian.com/v2/sms/single_send.json", content,"utf-8",2000,2000,false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
