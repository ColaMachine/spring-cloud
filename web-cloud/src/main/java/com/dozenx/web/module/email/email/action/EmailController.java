/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-2-21 17:06:29
 * 文件说明: 
 */

package com.dozenx.web.module.email.email.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import com.dozenx.swagger.annotation.*;
import java.util.LinkedHashMap;
import com.dozenx.common.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.slf4j.Logger;
import com.dozenx.common.exception.ParamException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.DataType;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.module.email.email.service.EmailService;
import com.dozenx.web.module.email.email.bean.Email;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.common.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.exception.BizException;
import java.nio.file.Files;
import com.dozenx.common.config.SysConfig;

@APIs(description = "邮件服务")
@Controller
@RequestMapping("/email")
public class EmailController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(EmailController.class);
    /** 权限service **/
    @Autowired
    private EmailService emailService;
    

//
//    /**
//     * 说明:ajax请求Email信息
//     * @author dozen.zhang
//     * @date 2019-2-21 17:06:29
//     * @return String
//     */
//       @API(summary="邮件服务列表接口",
//                 description="邮件服务列表接口",
//                 parameters={
//                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
//                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
//                    @Param(name="id" , description="编号",dataType = DataType.LONG,required =false),// false
//                    @Param(name="to" , description="收件人",dataType = DataType.STRING,required =false),// true
//                    @Param(name="title" , description="标题",dataType = DataType.STRING,required =false),// true
//                    @Param(name="content" , description="内容",dataType = DataType.STRING,required =false),// true
//                    @Param(name="status" , description="状态",dataType = DataType.INTEGER,required =false),// true
//                    @Param(name="module" , description="模块",dataType = DataType.INTEGER,required =false),// true
//                    @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required =false),// false
//         })
//    @RequestMapping(value = "/list.json" , method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO list(HttpServletRequest request) throws Exception{
//        Page page = RequestUtil.getPage(request);
//        if(page ==null){
//             return this.getWrongResultFromCfg("err.param.page");
//        }
//
//        HashMap<String,Object> params= new HashMap<String,Object>();
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            params.put("to",to);
//        }
//        String toLike = request.getParameter("toLike");
//        if(!StringUtil.isBlank(toLike)){
//            params.put("toLike",toLike);
//        }
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            params.put("title",title);
//        }
//        String titleLike = request.getParameter("titleLike");
//        if(!StringUtil.isBlank(titleLike)){
//            params.put("titleLike",titleLike);
//        }
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            params.put("content",content);
//        }
//        String contentLike = request.getParameter("contentLike");
//        if(!StringUtil.isBlank(contentLike)){
//            params.put("contentLike",contentLike);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            params.put("module",module);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        params.put("page",page);
//        List<Email> emails = emailService.listByParams4Page(params);
//        return ResultUtil.getResult(emails, page);
//    }
//
//   /**
//    * 说明:ajax请求Email信息 无分页版本
//    * @return ResultDTO 返回结果
//    * @author dozen.zhang
//    * @date 2019-2-21 17:06:29
//    */
//    @RequestMapping(value = "/listAll.json")
//    @ResponseBody
//    public ResultDTO listAll(HttpServletRequest request) {
//                HashMap<String,Object> params= new HashMap<String,Object>();
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            params.put("to",to);
//        }
//        String toLike = request.getParameter("toLike");
//        if(!StringUtil.isBlank(toLike)){
//            params.put("toLike",toLike);
//        }
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            params.put("title",title);
//        }
//        String titleLike = request.getParameter("titleLike");
//        if(!StringUtil.isBlank(titleLike)){
//            params.put("titleLike",titleLike);
//        }
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            params.put("content",content);
//        }
//        String contentLike = request.getParameter("contentLike");
//        if(!StringUtil.isBlank(contentLike)){
//            params.put("contentLike",contentLike);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            params.put("module",module);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        List<Email> emails = emailService.listByParams(params);
//        return ResultUtil.getDataResult(emails);
//    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date 2019-2-21 17:06:29
    */
     @API( summary="根据id查询单个邮件服务信息",
               description = "根据id查询单个邮件服务信息",
               parameters={
                       @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable Long id,HttpServletRequest request) {
                     HashMap<String,Object> result =new HashMap</*String,Object*/>();
            Email bean = emailService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
            return this.getResult(result);

        }


//
//     /**
//        * 说明:查看单条信息
//        * @param request 发请求
//        * @return String
//        * @author dozen.zhang
//        * @date 2019-2-21 17:06:29
//        */
//      @API( summary="根据id查询单个邮件服务信息",
//               description = "根据id查询单个邮件服务信息",
//               parameters={
//                       @Param(name="id" , description="id",dataType= DataType.LONG,required = true),
//               })
//        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
//        @ResponseBody
//        public ResultDTO getById(HttpServletRequest request) {
//         String id = request.getParameter("id");
//            Email bean = emailService.selectByPrimaryKey(Long.valueOf(id));
//          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
//           // result.put("bean", bean);
//            return this.getResult(bean);
//        }
//
//
//    /**
//     * 说明:更新Email信息
//     *
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2019-2-21 17:06:29
//     */
//      @API( summary="更新id更新单个邮件服务信息",
//        description = "更新id更新单个邮件服务信息",
//        parameters={
//           @Param(name="id" , description="编号",type="LONG",required = false),
//           @Param(name="to" , description="收件人",type="STRING",required = true),
//           @Param(name="title" , description="标题",type="STRING",required = true),
//           @Param(name="content" , description="内容",type="STRING",required = true),
//           @Param(name="status" , description="状态",type="INTEGER",required = true),
//           @Param(name="module" , description="模块",type="INTEGER",required = true),
//           @Param(name="createTime" , description="创建时间",type="DATE_TIME",required = false),
//        })
//    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
//    @ResponseBody
//    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
//        Email email =new  Email();
//        /*
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            email.setId(Long.valueOf(id)) ;
//        }
//
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            email.setTo(String.valueOf(to)) ;
//        }
//
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            email.setTitle(String.valueOf(title)) ;
//        }
//
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            email.setContent(String.valueOf(content)) ;
//        }
//
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            email.setStatus(Integer.valueOf(status)) ;
//        }
//
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            email.setModule(Integer.valueOf(module)) ;
//        }
//
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            email.setCreateTime(Timestamp.valueOf(createTime)) ;
//        }
//        */
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            email.setId(Long.valueOf(id));
//        }
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            email.setTo(to);
//        }
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            email.setTitle(title);
//        }
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            email.setContent(content);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            email.setStatus(Integer.valueOf(status));
//        }
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            email.setModule(Integer.valueOf(module));
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                email.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                email.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        //valid
//        ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("to", to, "收件人",  new Rule[]{new Length(100),new NotEmpty()});
//        vu.add("title", title, "标题",  new Rule[]{new Length(51),new NotEmpty()});
//        vu.add("content", content, "内容",  new Rule[]{new Length(51),new NotEmpty()});
//        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
//        vu.add("module", module, "模块",  new Rule[]{new Digits(1,0),new NotEmpty()});
//        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        validStr = vu.validateString();
//        if(StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302,validStr);
//        }
//
//        return emailService.save(email);
//
//    }
//
//
//        /**
//         * 说明:添加Email信息
//         * @param request
//         * @throws Exception
//         * @return ResultDTO
//         * @author dozen.zhang
//         * @date 2019-2-21 17:06:29
//         */
//        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//        @API( summary="添加单个邮件服务信息",
//            description = "添加单个邮件服务信息",
//            parameters={
//               @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
//               @Param(name="to" , description="收件人",dataType = DataType.STRING,required = true),
//               @Param(name="title" , description="标题",dataType = DataType.STRING,required = true),
//               @Param(name="content" , description="内容",dataType = DataType.STRING,required = true),
//               @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
//               @Param(name="module" , description="模块",dataType = DataType.INTEGER,required = true),
//               @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
//            })
//        @RequestMapping(value = "add.json",method = RequestMethod.POST)
//        @ResponseBody
//        public ResultDTO add(HttpServletRequest request) throws Exception {
//            Email email =new  Email();
//            /*
//            String id = request.getParameter("id");
//            if(!StringUtil.isBlank(id)){
//                email.setId(Long.valueOf(id)) ;
//            }
//
//            String to = request.getParameter("to");
//            if(!StringUtil.isBlank(to)){
//                email.setTo(String.valueOf(to)) ;
//            }
//
//            String title = request.getParameter("title");
//            if(!StringUtil.isBlank(title)){
//                email.setTitle(String.valueOf(title)) ;
//            }
//
//            String content = request.getParameter("content");
//            if(!StringUtil.isBlank(content)){
//                email.setContent(String.valueOf(content)) ;
//            }
//
//            String status = request.getParameter("status");
//            if(!StringUtil.isBlank(status)){
//                email.setStatus(Integer.valueOf(status)) ;
//            }
//
//            String module = request.getParameter("module");
//            if(!StringUtil.isBlank(module)){
//                email.setModule(Integer.valueOf(module)) ;
//            }
//
//            String createTime = request.getParameter("createTime");
//            if(!StringUtil.isBlank(createTime)){
//                email.setCreateTime(Timestamp.valueOf(createTime)) ;
//            }
//            */
//            String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            email.setId(Long.valueOf(id));
//        }
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            email.setTo(to);
//        }
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            email.setTitle(title);
//        }
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            email.setContent(content);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            email.setStatus(Integer.valueOf(status));
//        }
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            email.setModule(Integer.valueOf(module));
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                email.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                email.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//            //valid
//            ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("to", to, "收件人",  new Rule[]{new Length(100),new NotEmpty()});
//        vu.add("title", title, "标题",  new Rule[]{new Length(51),new NotEmpty()});
//        vu.add("content", content, "内容",  new Rule[]{new Length(51),new NotEmpty()});
//        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
//        vu.add("module", module, "模块",  new Rule[]{new Digits(1,0),new NotEmpty()});
//        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        validStr = vu.validateString();
//        if(StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302,validStr);
//        }
//
//            return emailService.save(email);
//
//        }
//
//
//          /**
//                 * 说明:添加Email信息
//                 * @param request
//                 * @throws Exception
//                 * @return ResultDTO
//                 * @author dozen.zhang
//                 * @date 2019-2-21 17:06:29
//                 */
//                // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//                @API( summary="添加单个邮件服务信息",
//                    description = "添加单个邮件服务信息",
//                    parameters={
//                       @Param(name="id" , description="编号",dataType = DataType.LONG,required = false),
//                       @Param(name="to" , description="收件人",dataType = DataType.STRING,required = true),
//                       @Param(name="title" , description="标题",dataType = DataType.STRING,required = true),
//                       @Param(name="content" , description="内容",dataType = DataType.STRING,required = true),
//                       @Param(name="status" , description="状态",dataType = DataType.INTEGER,required = true),
//                       @Param(name="module" , description="模块",dataType = DataType.INTEGER,required = true),
//                       @Param(name="createTime" , description="创建时间",dataType = DataType.DATE_TIME,required = false),
//                    })
//                @RequestMapping(value = "save.json",method = RequestMethod.POST)
//                @ResponseBody
//                public ResultDTO save(HttpServletRequest request) throws Exception {
//                    Email email =new  Email();
//                    /*
//                    String id = request.getParameter("id");
//                    if(!StringUtil.isBlank(id)){
//                        email.setId(Long.valueOf(id)) ;
//                    }
//
//                    String to = request.getParameter("to");
//                    if(!StringUtil.isBlank(to)){
//                        email.setTo(String.valueOf(to)) ;
//                    }
//
//                    String title = request.getParameter("title");
//                    if(!StringUtil.isBlank(title)){
//                        email.setTitle(String.valueOf(title)) ;
//                    }
//
//                    String content = request.getParameter("content");
//                    if(!StringUtil.isBlank(content)){
//                        email.setContent(String.valueOf(content)) ;
//                    }
//
//                    String status = request.getParameter("status");
//                    if(!StringUtil.isBlank(status)){
//                        email.setStatus(Integer.valueOf(status)) ;
//                    }
//
//                    String module = request.getParameter("module");
//                    if(!StringUtil.isBlank(module)){
//                        email.setModule(Integer.valueOf(module)) ;
//                    }
//
//                    String createTime = request.getParameter("createTime");
//                    if(!StringUtil.isBlank(createTime)){
//                        email.setCreateTime(Timestamp.valueOf(createTime)) ;
//                    }
//                    */
//                    String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            email.setId(Long.valueOf(id));
//        }
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            email.setTo(to);
//        }
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            email.setTitle(title);
//        }
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            email.setContent(content);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            email.setStatus(Integer.valueOf(status));
//        }
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            email.setModule(Integer.valueOf(module));
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                email.setCreateTime(Timestamp.valueOf(createTime));
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                email.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//                    //valid
//                    ValidateUtil vu = new ValidateUtil();
//        String validStr="";
//        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
//        vu.add("to", to, "收件人",  new Rule[]{new Length(100),new NotEmpty()});
//        vu.add("title", title, "标题",  new Rule[]{new Length(51),new NotEmpty()});
//        vu.add("content", content, "内容",  new Rule[]{new Length(51),new NotEmpty()});
//        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
//        vu.add("module", module, "模块",  new Rule[]{new Digits(1,0),new NotEmpty()});
//        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        validStr = vu.validateString();
//        if(StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302,validStr);
//        }
//
//                    return emailService.save(email);
//
//                }
//
//    /**
//     * 说明:删除Email信息
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2019-2-21 17:06:29
//     */
//     @API( summary="根据id删除单个邮件服务信息",
//        description = "根据id删除单个邮件服务信息",
//        parameters={
//         @Param(name="id" , description="编号",dataType= DataType.LONG,required = true),
//        })
//    @RequestMapping(value = "/delete.json",method = RequestMethod.DELETE)//{id}
//    @ResponseBody
//    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
//        String idStr = request.getParameter("id");
//        if(StringUtil.isBlank(idStr)){
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        Long id = Long.valueOf(idStr);
//        emailService.delete(id);
//        return this.getResult(SUCC);
//    }
//
//     /**
//         * 说明:删除Email信息
//         * @param request
//         * @throws Exception
//         * @return ResultDTO
//         * @author dozen.zhang
//         * @date 2019-2-21 17:06:29
//         */
//         @API( summary="根据id删除单个邮件服务信息",
//            description = "根据id删除单个邮件服务信息",
//            parameters={
//             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.LONG,required = true),
//            })
//        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
//        @ResponseBody
//        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
//            emailService.delete(id);
//            return this.getResult(SUCC);
//        }
//     /**
//     * 多行删除
//     * @param request
//     * @return
//     * @author dozen.zhang
//     */
//    @RequestMapping(value = "/mdel.json")
//    @ResponseBody
//    public ResultDTO multiDelete(HttpServletRequest request) {
//        String idStr = request.getParameter("ids");
//        if(StringUtil.isBlank(idStr)){
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        String idStrAry[]= idStr.split(",");
//        Long idAry[]=new Long[idStrAry.length];
//        for(int i=0,length=idAry.length;i<length;i++){
//            ValidateUtil vu = new ValidateUtil();
//            String validStr="";
//            String id = idStrAry[i];
//                    vu.add("id", id, "编号",  new Rule[]{});
//
//            try{
//                validStr=vu.validateString();
//            }catch(Exception e){
//                e.printStackTrace();
//                validStr="验证程序异常";
//                return ResultUtil.getResult(302,validStr);
//            }
//
//            if(StringUtil.isNotBlank(validStr)) {
//                return ResultUtil.getResult(302,validStr);
//            }
//            idAry[i]=Long.valueOf(idStrAry[i]);
//        }
//       return  emailService.multilDelete(idAry);
//    }
//
//    /**
//     * 导出
//     * @param request
//     * @return
//     * @author dozen.zhang
//     */
//    @RequestMapping(value = "/export.json")
//    @ResponseBody
//    public ResultDTO exportExcel(HttpServletRequest request){
//               HashMap<String,Object> params= new HashMap<String,Object>();
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String to = request.getParameter("to");
//        if(!StringUtil.isBlank(to)){
//            params.put("to",to);
//        }
//        String toLike = request.getParameter("toLike");
//        if(!StringUtil.isBlank(toLike)){
//            params.put("toLike",toLike);
//        }
//        String title = request.getParameter("title");
//        if(!StringUtil.isBlank(title)){
//            params.put("title",title);
//        }
//        String titleLike = request.getParameter("titleLike");
//        if(!StringUtil.isBlank(titleLike)){
//            params.put("titleLike",titleLike);
//        }
//        String content = request.getParameter("content");
//        if(!StringUtil.isBlank(content)){
//            params.put("content",content);
//        }
//        String contentLike = request.getParameter("contentLike");
//        if(!StringUtil.isBlank(contentLike)){
//            params.put("contentLike",contentLike);
//        }
//        String status = request.getParameter("status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String module = request.getParameter("module");
//        if(!StringUtil.isBlank(module)){
//            params.put("module",module);
//        }
//        String createTime = request.getParameter("createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        // 查询list集合
//        List<Email> list =emailService.listByParams(params);
//        // 存放临时文件
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "list.xlsx");
//        String folder = request.getSession().getServletContext()
//                .getRealPath("/")
//                + "xlstmp";
//        File folder_file = new File(folder);
//        if (!folder_file.exists()) {
//            folder_file.mkdir();
//        }
//        String fileName = folder + File.separator
//                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
//                + ".xlsx";
//        // 得到导出Excle时清单的英中文map
//        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
//        colTitle.put("id", "编号");
//        colTitle.put("to", "收件人");
//        colTitle.put("title", "标题");
//        colTitle.put("content", "内容");
//        colTitle.put("status", "状态");
//        colTitle.put("module", "模块");
//        colTitle.put("createTime", "创建时间");
//        List<Map> finalList = new ArrayList<Map>();
//        for (int i = 0; i < list.size(); i++) {
//            Email sm = list.get(i);
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("id",  list.get(i).getId());
//            map.put("to",  list.get(i).getTo());
//            map.put("title",  list.get(i).getTitle());
//            map.put("content",  list.get(i).getContent());
//            map.put("status",  list.get(i).getStatus());
//            map.put("module",  list.get(i).getModule());
//            map.put("createTime",  list.get(i).getCreateTime());
//            finalList.add(map);
//        }
//        try {
//            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                return this.getResult(SUCC,fileName,"导出成功");
//            }
//            /*
//             * return new ResponseEntity<byte[]>(
//             * FileUtils.readFileToByteArray(new File(fileName)), headers,
//             * HttpStatus.CREATED);
//             */
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this.getResult(0, "数据为空，导出失败");
//
//    }
//
//     @API(summary = "批量导入信息",
//                description = "批量导入信息",
//                consumes = "multipart/form-data",
//                parameters = {
//                        @Param(name = "file", description = "编号", in = InType.form, dataType = DataType.FILE, required = true),
//                })
//    @RequestMapping(value = "/import", method = RequestMethod.POST)
//     @ResponseBody
//    public ResultDTO importExcel(@RequestParam("file") MultipartFile file){
//          // 将spring 的file 装成 普通file
//                File xlsFile = null;
//                if (file != null) {
//                    try {
//                        String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
//                        String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
//                        Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
//                        xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
//                    } catch (Exception e) {
//                        logger.error("文件上传出错", e);
//                        throw new BizException("E041412312", "文件上传出错");
//                    }
//                }
//                String result = "";
//                try {
//
//                    // 将导入的中文列名匹配至数据库对应字段
//                    int success = 0;
//                    int fail = 0;
//                    StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
//        //            Map<String, String> colMatch = new HashMap<String, String>();
//        //            colMatch.put("姓名", "name");
//        //            colMatch.put("单位", "org");
//        //            colMatch.put("邮箱", "email");
//
//
//                    List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
//                    for (int i = 0; i < list.size(); i++) {
//
//                        Map<String, String> map = list.get(i);
//                        String email = MapUtils.getStringValue(map, "邮箱");
//                        // 检验手机号是否符合规范,不符合continue
//                        if (!StringUtil.isEmail(email)) {
//        //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
//                            logger.info(" import conf ==> the telphone:" + email + " is not email");
//                            fail++;
//                            errorMsg.append("" + email + " 不是邮箱地址;");
//                            continue;
//                        }
//                        HashMap params = new HashMap();
//                        params.put("email", email);
//                      //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在
//
//                        Email bean = getInfoFromMap(params);
//
//                      //  if (count > 0) {
//
//                      //      logger.warn("邮箱已经存在:" + email);
//                       //     errorMsg.append("邮箱已经存在:" + email);
//                         //   continue;
//
//                       // }
//
//                        try {
//                            emailService.save(bean);
//                            success++;//成功数增加
//                        } catch (Exception e) {
//
//                            fail++;//失败数增加
//                            logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
//                            errorMsg.append("the telphone:" + email + " update fail;");
//                        }
//
//                    }
//                    if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
//                        throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
//                    }
//                    return this.getResult(3090182,"导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    logger.error("导入失败", e);
//                    if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
//                        throw new BizException("E041412313", "导入的excel需为2003版本");
//                    } else {
//                        throw new BizException("E041412313", e.getMessage());
//                    }
//                }
//
//
//    }
//
//
//      /**
//         * 说明: 跳转到Email列表页面
//         *
//         * @return
//         * @return String
//         * @author dozen.zhang
//         * @date 2015年11月15日下午12:30:45
//         */
//        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
//        public String listHtml() {
//            return "/static/html/EmailList.html";
//        }
//
//        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
//        public String listMapperHtml() {
//            return "/static/html/EmailListMapper.html";
//        }
//
//
//    /**
//     * 说明:跳转编辑页面
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2019-2-21 17:06:29
//     */
//    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
//    public String editHtml( HttpServletRequest request) {
//        // 查找所有的角色
//        return "/static/html/EmailEdit.html";
//    }
//    /**
//         * 说明:跳转查看页面
//         * @param request 发请求
//         * @return String
//         * @author dozen.zhang
//         * @date 2019-2-21 17:06:29
//         */
//    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
//    public String viewHtml( HttpServletRequest request) {
//        return "/static/html/EmailView.html";
//    }
//
//
//
    private Email getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Email email =new  Email();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                email.setId(Long.valueOf(id));
        }
        String to = MapUtils.getString(bodyParam,"to");
        if(!StringUtil.isBlank(to)){
                email.setTo(String.valueOf(to));
        }
        String title = MapUtils.getString(bodyParam,"title");
        if(!StringUtil.isBlank(title)){
                email.setTitle(String.valueOf(title));
        }
        String content = MapUtils.getString(bodyParam,"content");
        if(!StringUtil.isBlank(content)){
                email.setContent(String.valueOf(content));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                email.setStatus(Integer.valueOf(status));
        }
        String module = MapUtils.getString(bodyParam,"module");
        if(!StringUtil.isBlank(module)){
                email.setModule(Integer.valueOf(module));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                email.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                email.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(15,0)});
        vu.add("to", to, "收件人",  new Rule[]{new Length(100),new NotEmpty()});
        vu.add("title", title, "标题",  new Rule[]{new Length(51),new NotEmpty()});
        vu.add("content", content, "内容",  new Rule[]{new Length(51),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("module", module, "模块",  new Rule[]{new Digits(1,0),new NotEmpty()});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  email;
    }


      /**
                     * 说明:添加Email信息
                     * @param request
                     * @throws Exception
                     * @return ResultDTO
                     * @author dozen.zhang
                     * @date 2019-2-21 17:06:29
                     */
                    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
                    @API( summary="添加单个邮件服务信息",
                        description = "添加单个邮件服务信息",
                        parameters={
                           @Param(name="id" , description="编号"  ,in=InType.body,dataType = DataType.LONG,required = false),
                           @Param(name="to" , description="收件人"  ,in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="title" , description="标题"  ,in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="content" , description="内容"  ,in=InType.body,dataType = DataType.STRING,required = true),
                           @Param(name="status" , description="状态"  ,in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="module" , description="模块"  ,in=InType.body,dataType = DataType.INTEGER,required = true),
                           @Param(name="createTime" , description="创建时间"  ,in=InType.body,dataType = DataType.DATE_TIME,required = false),
                        })
                    @RequestMapping(value = "add",method = RequestMethod.POST)
                    @ResponseBody
                    public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
                        Email email =    getInfoFromMap(bodyParam);


                        return emailService.save(email);

                    }


    /**
    * 说明:添加Email信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-2-21 17:06:29
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个邮件服务信息",
    description = "更新单个邮件服务信息",
    parameters={
        @Param(name="id" , description="编号  ",in=InType.body,dataType = DataType.LONG,required = false),
        @Param(name="to" , description="收件人  ",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="title" , description="标题  ",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="content" , description="内容  ",in=InType.body,dataType = DataType.STRING,required = true),
        @Param(name="status" , description="状态  ",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="module" , description="模块  ",in=InType.body,dataType = DataType.INTEGER,required = true),
        @Param(name="createTime" , description="创建时间  ",in=InType.body,dataType = DataType.DATE_TIME,required = false),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Email email =    getInfoFromMap(bodyParam);
    return emailService.save(email);

    }
/**
     * 说明:ajax请求Email信息
     * @author dozen.zhang
     * @date 2019-2-21 17:06:29
     * @return String
     */
       @API(summary="邮件服务列表接口",
                 description="邮件服务列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                    @Param(name="id" , description="编号  ",in=InType.params,dataType = DataType.LONG,required =false),// false
                    @Param(name="to" , description="收件人  ",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="title" , description="标题  ",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="content" , description="内容  ",in=InType.params,dataType = DataType.STRING,required =false),// true
                    @Param(name="status" , description="状态  ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="module" , description="模块  ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
                    @Param(name="createTime" , description="创建时间  ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
         })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
         Page page = RequestUtil.getPage(params);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }

                String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String to = MapUtils.getString(params,"to");
        if(!StringUtil.isBlank(to)){
            params.put("to",to);
        }
        String toLike = MapUtils.getString(params,"toLike");
        if(!StringUtil.isBlank(toLike)){
            params.put("toLike",toLike);
        }
        String title = MapUtils.getString(params,"title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = MapUtils.getString(params,"titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String content = MapUtils.getString(params,"content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = MapUtils.getString(params,"contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String module = MapUtils.getString(params,"module");
        if(!StringUtil.isBlank(module)){
            params.put("module",module);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<Email> emails = emailService.listByParams4Page(params);
        return ResultUtil.getResult(emails, page);
    }



       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="邮件服务列表导出接口",
          description="邮件服务列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="to" , description="收件人 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="title" , description="标题 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="content" , description="内容 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="module" , description="模块 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="createTime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
          })
        @RequestMapping(value = "/export", method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO exportExcelInBody(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

             HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
              Page page = RequestUtil.getPage(params);
             if(page ==null){
                  return this.getWrongResultFromCfg("err.param.page");
             }

                     String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String to = MapUtils.getString(params,"to");
        if(!StringUtil.isBlank(to)){
            params.put("to",to);
        }
        String toLike = MapUtils.getString(params,"toLike");
        if(!StringUtil.isBlank(toLike)){
            params.put("toLike",toLike);
        }
        String title = MapUtils.getString(params,"title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = MapUtils.getString(params,"titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String content = MapUtils.getString(params,"content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = MapUtils.getString(params,"contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String module = MapUtils.getString(params,"module");
        if(!StringUtil.isBlank(module)){
            params.put("module",module);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

             params.put("page",page);
             List<Email> list = emailService.listByParams4Page(params);
            // 存放临时文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "list.xlsx");
              String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")+".xlsx";

            String folder = request.getSession().getServletContext()
                    .getRealPath("/")
                    + "xlstmp";


            File folder_file = new File(folder);
            if (!folder_file.exists()) {
                folder_file.mkdir();
            }
            String fileName = folder + File.separator
                      + randomName;
            // 得到导出Excle时清单的英中文map
            LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
            colTitle.put("id", "编号");
            colTitle.put("to", "收件人");
            colTitle.put("title", "标题");
            colTitle.put("content", "内容");
            colTitle.put("status", "状态");
            colTitle.put("module", "模块");
            colTitle.put("createTime", "创建时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Email sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("to",  list.get(i).getTo());
                map.put("title",  list.get(i).getTitle());
                map.put("content",  list.get(i).getContent());
                map.put("status",  list.get(i).getStatus());
                map.put("module",  list.get(i).getModule());
                map.put("createTime",  list.get(i).getCreateTime());
                finalList.add(map);
            }
            try {
                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                    return this.getResult(SUCC,SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
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

}
