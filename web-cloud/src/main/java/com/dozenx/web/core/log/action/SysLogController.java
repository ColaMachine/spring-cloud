/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.log.action;

import com.dozenx.common.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.bean.SysLog;
import com.dozenx.web.core.log.service.SysLogService;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.DateValue;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.Rule;
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
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysLogController.class);
    /** 权限service **/
    @Autowired
    private SysLogService sysLogService;
    
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
        return "/static/html/SysLogList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysLogListMapper.html";
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
        String path = request.getParameter("path");
        if(!StringUtil.isBlank(path)){
            params.put("path",path);
        }
        String pathLike = request.getParameter("pathLike");
        if(!StringUtil.isBlank(pathLike)){
            params.put("pathLike",pathLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String param = request.getParameter("param");
        if(!StringUtil.isBlank(param)){
            params.put("param",param);
        }
        String paramLike = request.getParameter("paramLike");
        if(!StringUtil.isBlank(paramLike)){
            params.put("paramLike",paramLike);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String userLike = request.getParameter("userLike");
        if(!StringUtil.isBlank(userLike)){
            params.put("userLike",userLike);
        }
        String msg = request.getParameter("msg");
        if(!StringUtil.isBlank(msg)){
            params.put("msg",msg);
        }
        String msgLike = request.getParameter("msgLike");
        if(!StringUtil.isBlank(msgLike)){
            params.put("msgLike",msgLike);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTime = request.getParameter("startTime");
        if(!StringUtil.isBlank(startTime)){
            if(StringUtil.checkNumeric(startTime)){
                params.put("startTime",startTime);
            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTime",new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeBegin = request.getParameter("startTimeBegin");
        if(!StringUtil.isBlank(startTimeBegin)){
            if(StringUtil.checkNumeric(startTimeBegin)){
                params.put("startTimeBegin",startTimeBegin);
            }else if(StringUtil.checkDateStr(startTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeBegin",new Timestamp( DateUtil.parseToDate(startTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeEnd = request.getParameter("startTimeEnd");
        if(!StringUtil.isBlank(startTimeEnd)){
            if(StringUtil.checkNumeric(startTimeEnd)){
                params.put("startTimeEnd",startTimeEnd);
            }else if(StringUtil.checkDateStr(startTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeEnd",new Timestamp( DateUtil.parseToDate(startTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<SysLog> sysLogs = sysLogService.listByParams4Page(params);
        return ResultUtil.getResult(sysLogs, page);
    }
    
   /**
    * 说明:ajax请求角色信息 无分页版本
    * @return Object
    * @author dozen.zhang
    * @date 2015年11月15日下午12:31:55
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public Object listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String path = request.getParameter("path");
        if(!StringUtil.isBlank(path)){
            params.put("path",path);
        }
        String pathLike = request.getParameter("pathLike");
        if(!StringUtil.isBlank(pathLike)){
            params.put("pathLike",pathLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String param = request.getParameter("param");
        if(!StringUtil.isBlank(param)){
            params.put("param",param);
        }
        String paramLike = request.getParameter("paramLike");
        if(!StringUtil.isBlank(paramLike)){
            params.put("paramLike",paramLike);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String userLike = request.getParameter("userLike");
        if(!StringUtil.isBlank(userLike)){
            params.put("userLike",userLike);
        }
        String msg = request.getParameter("msg");
        if(!StringUtil.isBlank(msg)){
            params.put("msg",msg);
        }
        String msgLike = request.getParameter("msgLike");
        if(!StringUtil.isBlank(msgLike)){
            params.put("msgLike",msgLike);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTime = request.getParameter("startTime");
        if(!StringUtil.isBlank(startTime)){
            if(StringUtil.checkNumeric(startTime)){
                params.put("startTime",startTime);
            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTime",new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeBegin = request.getParameter("startTimeBegin");
        if(!StringUtil.isBlank(startTimeBegin)){
            if(StringUtil.checkNumeric(startTimeBegin)){
                params.put("startTimeBegin",startTimeBegin);
            }else if(StringUtil.checkDateStr(startTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeBegin",new Timestamp( DateUtil.parseToDate(startTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeEnd = request.getParameter("startTimeEnd");
        if(!StringUtil.isBlank(startTimeEnd)){
            if(StringUtil.checkNumeric(startTimeEnd)){
                params.put("startTimeEnd",startTimeEnd);
            }else if(StringUtil.checkDateStr(startTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeEnd",new Timestamp( DateUtil.parseToDate(startTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<SysLog> sysLogs = sysLogService.listByParams(params);
        return ResultUtil.getDataResult(sysLogs);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysLogEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysLogView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysLog bean = sysLogService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysLog bean = sysLogService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,Object> result =new HashMap<String,Object>();
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
        SysLog sysLog =new  SysLog();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysLog.setId(Integer.valueOf(id)) ;
        }
        
        String path = request.getParameter("path");
        if(!StringUtil.isBlank(path)){
            sysLog.setPath(String.valueOf(path)) ;
        }
        
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sysLog.setType(Byte.valueOf(type)) ;
        }
        
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysLog.setCode(Integer.valueOf(code)) ;
        }
        
        String param = request.getParameter("param");
        if(!StringUtil.isBlank(param)){
            sysLog.setParam(String.valueOf(param)) ;
        }
        
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            sysLog.setUser(String.valueOf(user)) ;
        }
        
        String msg = request.getParameter("msg");
        if(!StringUtil.isBlank(msg)){
            sysLog.setMsg(String.valueOf(msg)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            sysLog.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        
        String startTime = request.getParameter("startTime");
        if(!StringUtil.isBlank(startTime)){
            sysLog.setStartTime(Timestamp.valueOf(startTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysLog.setId(Integer.valueOf(id));
        }
        String path = request.getParameter("path");
        if(!StringUtil.isBlank(path)){
            sysLog.setPath(path);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sysLog.setType(Byte.valueOf(type));
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            sysLog.setCode(Integer.valueOf(code));
        }
        String param = request.getParameter("param");
        if(!StringUtil.isBlank(param)){
            sysLog.setParam(param);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            sysLog.setUser(user);
        }
        String msg = request.getParameter("msg");
        if(!StringUtil.isBlank(msg)){
            sysLog.setMsg(msg);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sysLog.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sysLog.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTime = request.getParameter("startTime");
        if(!StringUtil.isBlank(startTime)){
            if(StringUtil.checkNumeric(startTime)){
                sysLog.setStartTime(Timestamp.valueOf(startTime));
            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
                sysLog.setStartTime(new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("path", path, "路径",  new Rule[]{new Length(100)});
        vu.add("type", type, "日志类型",  new Rule[]{new Digits(10,0)});
        vu.add("code", code, "代码",  new Rule[]{new Digits(10,0)});
        vu.add("param", param, "操作参数",  new Rule[]{new Length(200)});
        vu.add("user", user, "用户",  new Rule[]{new Length(40)});
        vu.add("msg", msg, "消息",  new Rule[]{new Length(1000)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("startTime", startTime, "开始时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysLogService.save(sysLog);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        sysLogService.delete(id);
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
                    vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});

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
       return  sysLogService.multilDelete(idAry);
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
        String path = request.getParameter("path");
        if(!StringUtil.isBlank(path)){
            params.put("path",path);
        }
        String pathLike = request.getParameter("pathLike");
        if(!StringUtil.isBlank(pathLike)){
            params.put("pathLike",pathLike);
        }
        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String code = request.getParameter("code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String param = request.getParameter("param");
        if(!StringUtil.isBlank(param)){
            params.put("param",param);
        }
        String paramLike = request.getParameter("paramLike");
        if(!StringUtil.isBlank(paramLike)){
            params.put("paramLike",paramLike);
        }
        String user = request.getParameter("user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String userLike = request.getParameter("userLike");
        if(!StringUtil.isBlank(userLike)){
            params.put("userLike",userLike);
        }
        String msg = request.getParameter("msg");
        if(!StringUtil.isBlank(msg)){
            params.put("msg",msg);
        }
        String msgLike = request.getParameter("msgLike");
        if(!StringUtil.isBlank(msgLike)){
            params.put("msgLike",msgLike);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTime = request.getParameter("startTime");
        if(!StringUtil.isBlank(startTime)){
            if(StringUtil.checkNumeric(startTime)){
                params.put("startTime",startTime);
            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTime",new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeBegin = request.getParameter("startTimeBegin");
        if(!StringUtil.isBlank(startTimeBegin)){
            if(StringUtil.checkNumeric(startTimeBegin)){
                params.put("startTimeBegin",startTimeBegin);
            }else if(StringUtil.checkDateStr(startTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeBegin",new Timestamp( DateUtil.parseToDate(startTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeEnd = request.getParameter("startTimeEnd");
        if(!StringUtil.isBlank(startTimeEnd)){
            if(StringUtil.checkNumeric(startTimeEnd)){
                params.put("startTimeEnd",startTimeEnd);
            }else if(StringUtil.checkDateStr(startTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeEnd",new Timestamp( DateUtil.parseToDate(startTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<SysLog> list =sysLogService.listByParams(params);
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
        colTitle.put("id", "编号");
        colTitle.put("path", "路径");
        colTitle.put("type", "日志类型");
        colTitle.put("code", "代码");
        colTitle.put("param", "操作参数");
        colTitle.put("user", "用户");
        colTitle.put("msg", "消息");
        colTitle.put("createTime", "创建时间");
        colTitle.put("startTime", "开始时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysLog sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("path",  list.get(i).getPath());
            map.put("type",  list.get(i).getType());
            map.put("code",  list.get(i).getCode());
            map.put("param",  list.get(i).getParam());
            map.put("user",  list.get(i).getUser());
            map.put("msg",  list.get(i).getMsg());
            map.put("createTime",  list.get(i).getCreateTime());
            map.put("startTime",  list.get(i).getStartTime());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC,fileName,"导出成功");
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


    @RequestMapping(value = "/getCountEveryDay.json")
    @ResponseBody
    public Object getCountEveryDay(HttpServletRequest request){
        String beginDateStr = request.getParameter("beginDate");
        String code = request.getParameter("code");
        String endDateStr = request.getParameter("endDate");
        Date beginDate = DateUtil.parseToDate(beginDateStr,"yyyy-MM-dd");
        Date endDate =DateUtil.parseToDate(endDateStr,"yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        List<Integer> countAry = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        HashMap params =new HashMap();
        HashMap result =new HashMap();
        params.put("code",code);
        while(calendar.getTime().getTime()<=endDate.getTime()){
            params.put("createTimeBegin",calendar.getTime());
            categories.add(DateUtil.toString(calendar.getTime(),"yyyy-MM-dd"));
            calendar.add(Calendar.DATE,1);
            params.put("createTimeEnd",calendar.getTime());

            countAry.add(sysLogService.countByParams(params));

        }

        result.put("categories",categories);
        result.put("data",countAry);
        return result;
    }

    @RequestMapping(value = "/getCountEveryHour.json")
    @ResponseBody
    public Object getCountEveryHour(HttpServletRequest request){
        String beginDateStr = request.getParameter("beginDate");
        String code = request.getParameter("code");
       String endDateStr = request.getParameter("endDate");
        Date beginDate = DateUtil.parseToDate(beginDateStr,"yyyy-MM-dd");



        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(Calendar.DATE,1);
        Date endDate =DateUtil.parseToDate(endDateStr,"yyyy-MM-dd");
        List<Integer> countAry = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        HashMap params =new HashMap();
        HashMap result =new HashMap();
        params.put("code",code);
        while(calendar.getTime().getTime()<=endDate.getTime()){
            params.put("createTimeBegin",calendar.getTime());
            categories.add(DateUtil.toString(calendar.getTime(),"yyyy-MM-dd:HH"));
            calendar.add(Calendar.HOUR,1);
            params.put("createTimeEnd",calendar.getTime());

            countAry.add(sysLogService.countByParams(params));

        }

        result.put("categories",categories);
        result.put("data",countAry);
        return result;
    }
}
