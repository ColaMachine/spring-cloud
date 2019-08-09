/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.sysConfig.action;

import com.dozenx.common.util.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.core.sysConfig.bean.SysConfig;
import com.dozenx.web.core.sysConfig.service.SysConfigService;
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
import java.util.*;
@Controller
@RequestMapping("/sysConfig")
public class SysConfigController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysConfigController.class);
    /** 权限service **/
    @Autowired
    private SysConfigService sysConfigService;
    
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
        return "/static/html/SysConfigList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysConfigListMapper.html";
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
        String key = request.getParameter("key");
        if(!StringUtil.isBlank(key)){
            params.put("key",key);
        }
        String keyLike = request.getParameter("keyLike");
        if(!StringUtil.isBlank(keyLike)){
            params.put("keyLike",keyLike);
        }
        String value = request.getParameter("value");
        if(!StringUtil.isBlank(value)){
            params.put("value",value);
        }
        String valueLike = request.getParameter("valueLike");
        if(!StringUtil.isBlank(valueLike)){
            params.put("valueLike",valueLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        params.put("page",page);
        List<SysConfig> sysConfigs = sysConfigService.listByParams4Page(params);
        return ResultUtil.getResult(sysConfigs, page);
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
        String key = request.getParameter("key");
        if(!StringUtil.isBlank(key)){
            params.put("key",key);
        }
        String keyLike = request.getParameter("keyLike");
        if(!StringUtil.isBlank(keyLike)){
            params.put("keyLike",keyLike);
        }
        String value = request.getParameter("value");
        if(!StringUtil.isBlank(value)){
            params.put("value",value);
        }
        String valueLike = request.getParameter("valueLike");
        if(!StringUtil.isBlank(valueLike)){
            params.put("valueLike",valueLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        List<SysConfig> sysConfigs = sysConfigService.listByParams(params);
        return ResultUtil.getDataResult(sysConfigs);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysConfigEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SysConfigView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            SysConfig bean = sysConfigService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysConfig bean = sysConfigService.selectByPrimaryKey(Integer.valueOf(id));
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
        SysConfig sysConfig =new  SysConfig();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysConfig.setId(Integer.valueOf(id)) ;
        }
        
        String key = request.getParameter("key");
        if(!StringUtil.isBlank(key)){
            sysConfig.setKey(String.valueOf(key)) ;
        }
        
        String value = request.getParameter("value");
        if(!StringUtil.isBlank(value)){
            sysConfig.setValue(String.valueOf(value)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysConfig.setRemark(String.valueOf(remark)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysConfig.setId(Integer.valueOf(id));
        }
        String key = request.getParameter("key");
        if(!StringUtil.isBlank(key)){
            sysConfig.setKey(key);
        }
        String value = request.getParameter("value");
        if(!StringUtil.isBlank(value)){
            sysConfig.setValue(value);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysConfig.setRemark(remark);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("key", key, "名称",  new Rule[]{new Length(50)});
        vu.add("value", value, "对应值",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("remark", remark, "说明",  new Rule[]{new Length(250),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return sysConfigService.save(sysConfig);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        sysConfigService.delete(id);
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
       return  sysConfigService.multilDelete(idAry);
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
        String key = request.getParameter("key");
        if(!StringUtil.isBlank(key)){
            params.put("key",key);
        }
        String keyLike = request.getParameter("keyLike");
        if(!StringUtil.isBlank(keyLike)){
            params.put("keyLike",keyLike);
        }
        String value = request.getParameter("value");
        if(!StringUtil.isBlank(value)){
            params.put("value",value);
        }
        String valueLike = request.getParameter("valueLike");
        if(!StringUtil.isBlank(valueLike)){
            params.put("valueLike",valueLike);
        }
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
        }

        // 查询list集合
        List<SysConfig> list =sysConfigService.listByParams(params);
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
        colTitle.put("key", "名称");
        colTitle.put("value", "对应值");
        colTitle.put("remark", "说明");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysConfig sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("key",  list.get(i).getKey());
            map.put("value",  list.get(i).getValue());
            map.put("remark",  list.get(i).getRemark());
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
}
