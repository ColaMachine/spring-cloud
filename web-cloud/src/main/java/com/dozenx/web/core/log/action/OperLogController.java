package com.dozenx.web.core.log.action;
/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-4-26 17:21:31
 * 文件说明:
 */


import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.DataType;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.bean.OperLog;
import com.dozenx.web.core.log.service.OperLogService;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@APIs(description = "操作日志")
@Controller
@RequestMapping(Constants.WEBROOT + "/log/oper/")
public class OperLogController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(OperLogController.class);
    /**
     * 权限service
     **/
    @Autowired
    private OperLogService operLogService;


    /**
     * 说明:ajax请求OperLog信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-4-26 17:21:31
     */
    @API(summary = "操作日志列表接口",
            description = "操作日志列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "主键", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "moduelName", description = "模块名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "compName", description = "操作对象", dataType = DataType.STRING, required = false),// false
                    @Param(name = "detail", description = "操作详情", dataType = DataType.STRING, required = false),// false
                    @Param(name = "userId", description = "操作人", dataType = DataType.LONG, required = false),// false
                    @Param(name = "ip", description = "用户ip", dataType = DataType.STRING, required = false),// false
                    @Param(name = "userName", description = "操作人", dataType = DataType.STRING, required = false),// false
                    @Param(name = "createTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),// true
            })
    //  @RequestMapping(value = "/list" , method = RequestMethod.GET)
    // @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception {
        Page page = RequestUtil.getPage(request);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String moduelName = request.getParameter("moduelName");
        if (!StringUtil.isBlank(moduelName)) {
            params.put("moduelName", moduelName);
        }
        String moduelNameLike = request.getParameter("moduelNameLike");
        if (!StringUtil.isBlank(moduelNameLike)) {
            params.put("moduelNameLike", moduelNameLike);
        }
        String compName = request.getParameter("compName");
        if (!StringUtil.isBlank(compName)) {
            params.put("compName", compName);
        }
        String compNameLike = request.getParameter("compNameLike");
        if (!StringUtil.isBlank(compNameLike)) {
            params.put("compNameLike", compNameLike);
        }
        String detail = request.getParameter("detail");
        if (!StringUtil.isBlank(detail)) {
            params.put("detail", detail);
        }
        String detailLike = request.getParameter("detailLike");
        if (!StringUtil.isBlank(detailLike)) {
            params.put("detailLike", detailLike);
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String ip = request.getParameter("ip");
        if (!StringUtil.isBlank(ip)) {
            params.put("ip", ip);
        }
        String ipLike = request.getParameter("ipLike");
        if (!StringUtil.isBlank(ipLike)) {
            params.put("ipLike", ipLike);
        }
        String userName = request.getParameter("userName");
        if (!StringUtil.isBlank(userName)) {
            params.put("userName", userName);
        }
        String userNameLike = request.getParameter("userNameLike");
        if (!StringUtil.isBlank(userNameLike)) {
            params.put("userNameLike", userNameLike);
        }
        String createTime = request.getParameter("createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                params.put("createTime", createTime);
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTime", new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if (!StringUtil.isBlank(createTimeBegin)) {
            if (StringUtil.checkNumeric(createTimeBegin)) {
                params.put("createTimeBegin", createTimeBegin);
            } else if (StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeBegin", new Timestamp(DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if (!StringUtil.isBlank(createTimeEnd)) {
            if (StringUtil.checkNumeric(createTimeEnd)) {
                params.put("createTimeEnd", createTimeEnd);
            } else if (StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeEnd", new Timestamp(DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<OperLog> operLogs = operLogService.listByParams4Page(params);
        return ResultUtil.getResult(operLogs, page);
    }

    /**
     * 系统日志--分页查询接口
     * // * @param accessToken 安全令牌
     *
     * @param params json格式参数
     * @return json
     * @author 王作品
     * @date 2017年8月29日 下午3:12:25
     */

    @API(summary = "操作日志列表接口",
            description = "操作日志列表接口",
            parameters = {
                    @Param(name = "params", description = "{keywords:'xxxx',userNameLike:'xxx',date:'2018-05-03',createTimeBegin:'2019-1-3',createTimeEnd:'2019-1-4',curPage:13,pageSize:20}", dataType = DataType.STRING, required = true),
            })
    @RequestMapping(method = RequestMethod.GET, value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultDTO getListByParam(HttpServletRequest request,
                                    @RequestParam(value = "params", required = true) String params//json格式参数
    ) throws UnsupportedEncodingException {
        logger.debug("系统操作日志，Controller传入参数params-----:: " + params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String keywords = (String) paramsMap.get("keywords");//关键字，允许为空， 支持[名称|key|value|备注]模糊查询
        String userName = (String) paramsMap.get("userName"); //用户名
        String createTimeBegin = (String) paramsMap.get("createTimeBegin"); //开始时间
        String createTimeEnd = (String) paramsMap.get("createTimeEnd"); //开始时间

        String date = (String) paramsMap.get("date"); //时间
        Integer userId = (Integer) paramsMap.get("userId");
        Page page = RequestUtil.getPage(paramsMap);

        if(StringUtil.isNotBlank(createTimeBegin)){
            createTimeBegin+=" 00:00:00";
        }
        if(StringUtil.isNotBlank(createTimeEnd)){
            createTimeEnd+=" 23:59:59";
        }
        // ValidUtil.valid("开始日期[startTime]", createTimeBegin, "required");//日期

//        Date date = DateUtil.parseToDate(createTime,"yyyy-MM-dd");
        List<OperLog> list = operLogService.getListByParam(keywords, userName, date, createTimeBegin, createTimeEnd, userId, page);
        return this.getResult(list, page);
    }


    /**
     * 说明:添加OperLog信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-9-10 15:03:36
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个操作日志信息",
            description = "添加单个操作日志信息",
            parameters = {
                    @Param(name = "id", description = "主键", dataType = DataType.INTEGER, required = false),
                    @Param(name = "moduleName", description = "模块名称", dataType = DataType.STRING, required = false),
                    @Param(name = "compName", description = "操作对象", dataType = DataType.STRING, required = false),
                    @Param(name = "detail", description = "操作详情", dataType = DataType.STRING, required = false),
                    @Param(name = "userId", description = "操作人", dataType = DataType.LONG, required = false),
                    @Param(name = "ip", description = "用户ip", dataType = DataType.STRING, required = false),
                    @Param(name = "userName", description = "操作人", dataType = DataType.STRING, required = false),
                    @Param(name = "createTime", description = "创建时间", dataType = DataType.DATE_TIME, required = true),
            })
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO save(HttpServletRequest request) throws Exception {
        OperLog operLog = new OperLog();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        operLog.setId(Integer.valueOf(id)) ;
                    }

                    String moduleName = request.getParameter("moduleName");
                    if(!StringUtil.isBlank(moduleName)){
                        operLog.setModuleName(String.valueOf(moduleName)) ;
                    }

                    String compName = request.getParameter("compName");
                    if(!StringUtil.isBlank(compName)){
                        operLog.setCompName(String.valueOf(compName)) ;
                    }

                    String detail = request.getParameter("detail");
                    if(!StringUtil.isBlank(detail)){
                        operLog.setDetail(String.valueOf(detail)) ;
                    }

                    String userId = request.getParameter("userId");
                    if(!StringUtil.isBlank(userId)){
                        operLog.setUserId(Long.valueOf(userId)) ;
                    }

                    String ip = request.getParameter("ip");
                    if(!StringUtil.isBlank(ip)){
                        operLog.setIp(String.valueOf(ip)) ;
                    }

                    String userName = request.getParameter("userName");
                    if(!StringUtil.isBlank(userName)){
                        operLog.setUserName(String.valueOf(userName)) ;
                    }

                    String createTime = request.getParameter("createTime");
                    if(!StringUtil.isBlank(createTime)){
                        operLog.setCreateTime(Timestamp.valueOf(createTime)) ;
                    }
                    */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            operLog.setId(Integer.valueOf(id));
        }
        String moduleName = request.getParameter("moduleName");
        if (!StringUtil.isBlank(moduleName)) {
            operLog.setModuleName(moduleName);
        }
        String compName = request.getParameter("compName");
        if (!StringUtil.isBlank(compName)) {
            operLog.setCompName(compName);
        }
        String detail = request.getParameter("detail");
        if (!StringUtil.isBlank(detail)) {
            operLog.setDetail(detail);
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            operLog.setUserId(Long.valueOf(userId));
        }
        String ip = request.getParameter("ip");
        if (!StringUtil.isBlank(ip)) {
            operLog.setIp(ip);
        }
        String userName = request.getParameter("userName");
        if (!StringUtil.isBlank(userName)) {
            operLog.setUserName(userName);
        }
        String createTime = request.getParameter("createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                operLog.setCreateTime(Timestamp.valueOf(createTime));
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                operLog.setCreateTime(new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "主键", new Rule[]{new Digits(10, 0)});
        vu.add("moduleName", moduleName, "模块名称", new Rule[]{new Length(40)});
        vu.add("compName", compName, "操作对象", new Rule[]{new Length(40)});
        vu.add("detail", detail, "操作详情", new Rule[]{new Length(5000)});
        vu.add("userId", userId, "操作人", new Rule[]{new Digits(11, 0)});
        vu.add("ip", ip, "用户ip", new Rule[]{new Length(30)});
        vu.add("userName", userName, "操作人", new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"), new NotEmpty()});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        operLogService.save(operLog);
        return this.getResult();
    }

}
