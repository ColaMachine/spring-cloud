/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-9-4 8:29:19
 * 文件说明:
 */

package com.dozenx.web.module.pubImage.action;

import com.dozenx.swagger.annotation.*;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.Config;
import com.dozenx.common.config.SysConfig;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.ValidException;
import com.dozenx.common.util.*;
import com.dozenx.common.util.encrypt.Base64Util;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.DateValue;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.Length;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.web.module.pubImage.service.PubImageService;
import com.dozenx.web.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.*;


@APIs(description = "公用图片模块")
@Controller
@RequestMapping("/pubimage/")
public class PubImageController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(PubImageController.class);
    /**
     * 权限service
     **/
    @Autowired
    private PubImageService pubImageService;


    /**
     * 说明:ajax请求PubImage信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @API(summary = "图片列表接口",
            description = "图片列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "path", description = "文件的绝对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "oriName", description = "原始名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "name", description = "文件名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "remark", description = "备注", dataType = DataType.STRING, required = false),// false
                    @Param(name = "absPath", description = "绝对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "relPath", description = "相对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "figure", description = "指纹", dataType = DataType.STRING, required = false),// false
                    @Param(name = "uploadIp", description = "照片上传时的Ip", dataType = DataType.STRING, required = false),// false
                    @Param(name = "creator", description = "上传照片人的Id", dataType = DataType.STRING, required = false),// false
                    @Param(name = "creatorName", description = "上传人的姓名", dataType = DataType.STRING, required = false),// false
                    @Param(name = "createDate", description = "上传照片的时间", dataType = DataType.DATE, required = false),// false
                    @Param(name = "lastModify", description = "照片的创建时间", dataType = DataType.DATE, required = false),// false
                    @Param(name = "status", description = "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "order", description = "顺序id", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "pid", description = "父组件id", dataType = DataType.INTEGER, required = false),// false
            })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
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
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            params.put("path", path);
        }
        String pathLike = request.getParameter("pathLike");
        if (!StringUtil.isBlank(pathLike)) {
            params.put("pathLike", pathLike);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            params.put("oriName", oriName);
        }
        String oriNameLike = request.getParameter("oriNameLike");
        if (!StringUtil.isBlank(oriNameLike)) {
            params.put("oriNameLike", oriNameLike);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            params.put("absPath", absPath);
        }
        String absPathLike = request.getParameter("absPathLike");
        if (!StringUtil.isBlank(absPathLike)) {
            params.put("absPathLike", absPathLike);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            params.put("relPath", relPath);
        }
        String relPathLike = request.getParameter("relPathLike");
        if (!StringUtil.isBlank(relPathLike)) {
            params.put("relPathLike", relPathLike);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            params.put("figure", figure);
        }
        String figureLike = request.getParameter("figureLike");
        if (!StringUtil.isBlank(figureLike)) {
            params.put("figureLike", figureLike);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            params.put("uploadIp", uploadIp);
        }
        String uploadIpLike = request.getParameter("uploadIpLike");
        if (!StringUtil.isBlank(uploadIpLike)) {
            params.put("uploadIpLike", uploadIpLike);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            params.put("creator", creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if (!StringUtil.isBlank(creatorLike)) {
            params.put("creatorLike", creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            params.put("creatorName", creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if (!StringUtil.isBlank(creatorNameLike)) {
            params.put("creatorNameLike", creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                params.put("createDate", createDate);
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                params.put("createDate", DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if (!StringUtil.isBlank(createDateBegin)) {
            if (StringUtil.checkNumeric(createDateBegin)) {
                params.put("createDateBegin", createDateBegin);
            } else if (StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")) {
                params.put("createDateBegin", DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if (!StringUtil.isBlank(createDateEnd)) {
            if (StringUtil.checkNumeric(createDateEnd)) {
                params.put("createDateEnd", createDateEnd);
            } else if (StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")) {
                params.put("createDateEnd", DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                params.put("lastModify", lastModify);
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                params.put("lastModify", DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String lastModifyBegin = request.getParameter("lastModifyBegin");
        if (!StringUtil.isBlank(lastModifyBegin)) {
            if (StringUtil.checkNumeric(lastModifyBegin)) {
                params.put("lastModifyBegin", lastModifyBegin);
            } else if (StringUtil.checkDateStr(lastModifyBegin, "yyyy-MM-dd")) {
                params.put("lastModifyBegin", DateUtil.parseToDate(lastModifyBegin, "yyyy-MM-dd"));
            }
        }
        String lastModifyEnd = request.getParameter("lastModifyEnd");
        if (!StringUtil.isBlank(lastModifyEnd)) {
            if (StringUtil.checkNumeric(lastModifyEnd)) {
                params.put("lastModifyEnd", lastModifyEnd);
            } else if (StringUtil.checkDateStr(lastModifyEnd, "yyyy-MM-dd")) {
                params.put("lastModifyEnd", DateUtil.parseToDate(lastModifyEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            params.put("order", order);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }

        params.put("page", page);
        List<PubImage> pubImages = pubImageService.listByParams4Page(params);
        return ResultUtil.getResult(pubImages, page);
    }

    /**
     * 说明:ajax请求PubImage信息 无分页版本
     *
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            params.put("path", path);
        }
        String pathLike = request.getParameter("pathLike");
        if (!StringUtil.isBlank(pathLike)) {
            params.put("pathLike", pathLike);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            params.put("oriName", oriName);
        }
        String oriNameLike = request.getParameter("oriNameLike");
        if (!StringUtil.isBlank(oriNameLike)) {
            params.put("oriNameLike", oriNameLike);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            params.put("absPath", absPath);
        }
        String absPathLike = request.getParameter("absPathLike");
        if (!StringUtil.isBlank(absPathLike)) {
            params.put("absPathLike", absPathLike);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            params.put("relPath", relPath);
        }
        String relPathLike = request.getParameter("relPathLike");
        if (!StringUtil.isBlank(relPathLike)) {
            params.put("relPathLike", relPathLike);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            params.put("figure", figure);
        }
        String figureLike = request.getParameter("figureLike");
        if (!StringUtil.isBlank(figureLike)) {
            params.put("figureLike", figureLike);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            params.put("uploadIp", uploadIp);
        }
        String uploadIpLike = request.getParameter("uploadIpLike");
        if (!StringUtil.isBlank(uploadIpLike)) {
            params.put("uploadIpLike", uploadIpLike);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            params.put("creator", creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if (!StringUtil.isBlank(creatorLike)) {
            params.put("creatorLike", creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            params.put("creatorName", creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if (!StringUtil.isBlank(creatorNameLike)) {
            params.put("creatorNameLike", creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                params.put("createDate", createDate);
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                params.put("createDate", DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if (!StringUtil.isBlank(createDateBegin)) {
            if (StringUtil.checkNumeric(createDateBegin)) {
                params.put("createDateBegin", createDateBegin);
            } else if (StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")) {
                params.put("createDateBegin", DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if (!StringUtil.isBlank(createDateEnd)) {
            if (StringUtil.checkNumeric(createDateEnd)) {
                params.put("createDateEnd", createDateEnd);
            } else if (StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")) {
                params.put("createDateEnd", DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                params.put("lastModify", lastModify);
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                params.put("lastModify", DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String lastModifyBegin = request.getParameter("lastModifyBegin");
        if (!StringUtil.isBlank(lastModifyBegin)) {
            if (StringUtil.checkNumeric(lastModifyBegin)) {
                params.put("lastModifyBegin", lastModifyBegin);
            } else if (StringUtil.checkDateStr(lastModifyBegin, "yyyy-MM-dd")) {
                params.put("lastModifyBegin", DateUtil.parseToDate(lastModifyBegin, "yyyy-MM-dd"));
            }
        }
        String lastModifyEnd = request.getParameter("lastModifyEnd");
        if (!StringUtil.isBlank(lastModifyEnd)) {
            if (StringUtil.checkNumeric(lastModifyEnd)) {
                params.put("lastModifyEnd", lastModifyEnd);
            } else if (StringUtil.checkDateStr(lastModifyEnd, "yyyy-MM-dd")) {
                params.put("lastModifyEnd", DateUtil.parseToDate(lastModifyEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            params.put("order", order);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }

        List<PubImage> pubImages = pubImageService.listByParams(params);
        return ResultUtil.getDataResult(pubImages);
    }

    /**
     * 说明:查看单条信息
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @API(summary = "根据id查询单个用户信息",
            description = "根据id查询单个用户信息",
            parameters = {
                    @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Integer id, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap</*String,Object*/>();
        if (id > 0) {
            PubImage bean = pubImageService.selectByPrimaryKey(Integer.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        PubImage bean = pubImageService.selectByPrimaryKey(Integer.valueOf(id));
        HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    /**
     * 说明:查看单条信息
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @API(summary = "根据id查询单个用户信息",
            description = "根据id查询单个用户信息",
            parameters = {
                    @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap</*String,Object*/>();
//        if(id > 0){
//            PubImage bean = pubImageService.selectByPrimaryKey(Integer.valueOf(id));
//            result.put("bean", bean);
//        }
        //   return this.getResult(result);


        String id = request.getParameter("id");
        PubImage bean = pubImageService.selectByPrimaryKey(Integer.valueOf(id));
//            HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
//            result.put("bean", bean);
        return this.getResult(bean);
    }


    /**
     * 说明:更新PubImage信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @API(summary = "根据id更新单个用户信息",
            description = "根据id更新单个用户信息",
            parameters = {
                    @Param(name = "id", description = "编号", type = "INTEGER", required = false),
                    @Param(name = "path", description = "文件的绝对路径", type = "STRING", required = false),
                    @Param(name = "oriName", description = "原始名称", type = "STRING", required = false),
                    @Param(name = "name", description = "文件名称", type = "STRING", required = false),
                    @Param(name = "remark", description = "备注", type = "STRING", required = false),
                    @Param(name = "absPath", description = "绝对路径", type = "STRING", required = false),
                    @Param(name = "relPath", description = "相对路径", type = "STRING", required = false),
                    @Param(name = "figure", description = "指纹", type = "STRING", required = false),
                    @Param(name = "uploadIp", description = "照片上传时的Ip", type = "STRING", required = false),
                    @Param(name = "creator", description = "上传照片人的Id", type = "STRING", required = false),
                    @Param(name = "creatorName", description = "上传人的姓名", type = "STRING", required = false),
                    @Param(name = "createDate", description = "上传照片的时间", type = "DATE", required = false),
                    @Param(name = "lastModify", description = "照片的创建时间", type = "DATE", required = false),
                    @Param(name = "status", description = "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", type = "INTEGER", required = false),
                    @Param(name = "order", description = "顺序id", type = "INTEGER", required = false),
                    @Param(name = "pid", description = "父组件id", type = "INTEGER", required = false),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update", method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Integer id,
        PubImage pubImage = new PubImage();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            pubImage.setId(Integer.valueOf(id)) ;
        }
        
        String path = request.getParameter("path");
        if(!StringUtil.isBlank(path)){
            pubImage.setPath(String.valueOf(path)) ;
        }
        
        String oriName = request.getParameter("oriName");
        if(!StringUtil.isBlank(oriName)){
            pubImage.setOriName(String.valueOf(oriName)) ;
        }
        
        String name = request.getParameter("name");
        if(!StringUtil.isBlank(name)){
            pubImage.setName(String.valueOf(name)) ;
        }
        
        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            pubImage.setRemark(String.valueOf(remark)) ;
        }
        
        String absPath = request.getParameter("absPath");
        if(!StringUtil.isBlank(absPath)){
            pubImage.setAbsPath(String.valueOf(absPath)) ;
        }
        
        String relPath = request.getParameter("relPath");
        if(!StringUtil.isBlank(relPath)){
            pubImage.setRelPath(String.valueOf(relPath)) ;
        }
        
        String figure = request.getParameter("figure");
        if(!StringUtil.isBlank(figure)){
            pubImage.setFigure(String.valueOf(figure)) ;
        }
        
        String uploadIp = request.getParameter("uploadIp");
        if(!StringUtil.isBlank(uploadIp)){
            pubImage.setUploadIp(String.valueOf(uploadIp)) ;
        }
        
        String creator = request.getParameter("creator");
        if(!StringUtil.isBlank(creator)){
            pubImage.setCreator(String.valueOf(creator)) ;
        }
        
        String creatorName = request.getParameter("creatorName");
        if(!StringUtil.isBlank(creatorName)){
            pubImage.setCreatorName(String.valueOf(creatorName)) ;
        }
        
        String createDate = request.getParameter("createDate");
        if(!StringUtil.isBlank(createDate)){
            pubImage.setCreateDate(Date.valueOf(createDate)) ;
        }
        
        String lastModify = request.getParameter("lastModify");
        if(!StringUtil.isBlank(lastModify)){
            pubImage.setLastModify(Date.valueOf(lastModify)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            pubImage.setStatus(Integer.valueOf(status)) ;
        }
        
        String order = request.getParameter("order");
        if(!StringUtil.isBlank(order)){
            pubImage.setOrder(Integer.valueOf(order)) ;
        }
        
        String pid = request.getParameter("pid");
        if(!StringUtil.isBlank(pid)){
            pubImage.setPid(Integer.valueOf(pid)) ;
        }
        */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            pubImage.setId(Integer.valueOf(id));
        }
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            pubImage.setPath(path);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            pubImage.setOriName(oriName);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            pubImage.setName(name);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            pubImage.setRemark(remark);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            pubImage.setAbsPath(absPath);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            pubImage.setRelPath(relPath);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            pubImage.setFigure(figure);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            pubImage.setUploadIp(uploadIp);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            pubImage.setCreator(creator);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            pubImage.setCreatorName(creatorName);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                pubImage.setCreateDate(new Date(createDate));
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                pubImage.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                pubImage.setLastModify(new Date(lastModify));
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                pubImage.setLastModify(DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            pubImage.setStatus(Integer.valueOf(status));
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            pubImage.setOrder(Integer.valueOf(order));
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            pubImage.setPid(Integer.valueOf(pid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(11, 0)});
        vu.add("path", path, "文件的绝对路径", new Rule[]{new Length(200)});
        vu.add("oriName", oriName, "原始名称", new Rule[]{new Length(50)});
        vu.add("name", name, "文件名称", new Rule[]{new Length(50)});
        vu.add("remark", remark, "备注", new Rule[]{new Length(50)});
        vu.add("absPath", absPath, "绝对路径", new Rule[]{new Length(50)});
        vu.add("relPath", relPath, "相对路径", new Rule[]{new Length(50)});
        vu.add("figure", figure, "指纹", new Rule[]{new Length(50)});
        vu.add("uploadIp", uploadIp, "照片上传时的Ip", new Rule[]{new Length(50)});
        vu.add("creator", creator, "上传照片人的Id", new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名", new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("lastModify", lastModify, "照片的创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", new Rule[]{new Digits(1, 0)});
        vu.add("order", order, "顺序id", new Rule[]{new Digits(3, 0)});
        vu.add("pid", pid, "父组件id", new Rule[]{new Digits(11, 0)});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return pubImageService.save(pubImage);

    }


    /**
     * 说明:添加PubImage信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API(summary = "添加单个用户信息",
            description = "添加单个用户信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.INTEGER, required = false),
                    @Param(name = "path", description = "文件的绝对路径", dataType = DataType.STRING, required = false),
                    @Param(name = "oriName", description = "原始名称", dataType = DataType.STRING, required = false),
                    @Param(name = "name", description = "文件名称", dataType = DataType.STRING, required = false),
                    @Param(name = "remark", description = "备注", dataType = DataType.STRING, required = false),
                    @Param(name = "absPath", description = "绝对路径", dataType = DataType.STRING, required = false),
                    @Param(name = "relPath", description = "相对路径", dataType = DataType.STRING, required = false),
                    @Param(name = "figure", description = "指纹", dataType = DataType.STRING, required = false),
                    @Param(name = "uploadIp", description = "照片上传时的Ip", dataType = DataType.STRING, required = false),
                    @Param(name = "creator", description = "上传照片人的Id", dataType = DataType.STRING, required = false),
                    @Param(name = "creatorName", description = "上传人的姓名", dataType = DataType.STRING, required = false),
                    @Param(name = "createDate", description = "上传照片的时间", dataType = DataType.DATE, required = false),
                    @Param(name = "lastModify", description = "照片的创建时间", dataType = DataType.DATE, required = false),
                    @Param(name = "status", description = "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", dataType = DataType.INTEGER, required = false),
                    @Param(name = "order", description = "顺序id", dataType = DataType.INTEGER, required = false),
                    @Param(name = "pid", description = "父组件id", dataType = DataType.INTEGER, required = false),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        PubImage pubImage = new PubImage();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                pubImage.setId(Integer.valueOf(id)) ;
            }
            
            String path = request.getParameter("path");
            if(!StringUtil.isBlank(path)){
                pubImage.setPath(String.valueOf(path)) ;
            }
            
            String oriName = request.getParameter("oriName");
            if(!StringUtil.isBlank(oriName)){
                pubImage.setOriName(String.valueOf(oriName)) ;
            }
            
            String name = request.getParameter("name");
            if(!StringUtil.isBlank(name)){
                pubImage.setName(String.valueOf(name)) ;
            }
            
            String remark = request.getParameter("remark");
            if(!StringUtil.isBlank(remark)){
                pubImage.setRemark(String.valueOf(remark)) ;
            }
            
            String absPath = request.getParameter("absPath");
            if(!StringUtil.isBlank(absPath)){
                pubImage.setAbsPath(String.valueOf(absPath)) ;
            }
            
            String relPath = request.getParameter("relPath");
            if(!StringUtil.isBlank(relPath)){
                pubImage.setRelPath(String.valueOf(relPath)) ;
            }
            
            String figure = request.getParameter("figure");
            if(!StringUtil.isBlank(figure)){
                pubImage.setFigure(String.valueOf(figure)) ;
            }
            
            String uploadIp = request.getParameter("uploadIp");
            if(!StringUtil.isBlank(uploadIp)){
                pubImage.setUploadIp(String.valueOf(uploadIp)) ;
            }
            
            String creator = request.getParameter("creator");
            if(!StringUtil.isBlank(creator)){
                pubImage.setCreator(String.valueOf(creator)) ;
            }
            
            String creatorName = request.getParameter("creatorName");
            if(!StringUtil.isBlank(creatorName)){
                pubImage.setCreatorName(String.valueOf(creatorName)) ;
            }
            
            String createDate = request.getParameter("createDate");
            if(!StringUtil.isBlank(createDate)){
                pubImage.setCreateDate(Date.valueOf(createDate)) ;
            }
            
            String lastModify = request.getParameter("lastModify");
            if(!StringUtil.isBlank(lastModify)){
                pubImage.setLastModify(Date.valueOf(lastModify)) ;
            }
            
            String status = request.getParameter("status");
            if(!StringUtil.isBlank(status)){
                pubImage.setStatus(Integer.valueOf(status)) ;
            }
            
            String order = request.getParameter("order");
            if(!StringUtil.isBlank(order)){
                pubImage.setOrder(Integer.valueOf(order)) ;
            }
            
            String pid = request.getParameter("pid");
            if(!StringUtil.isBlank(pid)){
                pubImage.setPid(Integer.valueOf(pid)) ;
            }
            */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            pubImage.setId(Integer.valueOf(id));
        }
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            pubImage.setPath(path);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            pubImage.setOriName(oriName);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            pubImage.setName(name);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            pubImage.setRemark(remark);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            pubImage.setAbsPath(absPath);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            pubImage.setRelPath(relPath);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            pubImage.setFigure(figure);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            pubImage.setUploadIp(uploadIp);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            pubImage.setCreator(creator);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            pubImage.setCreatorName(creatorName);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                pubImage.setCreateDate(new Date(createDate));
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                pubImage.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                pubImage.setLastModify(new Date(lastModify));
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                pubImage.setLastModify(DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            pubImage.setStatus(Integer.valueOf(status));
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            pubImage.setOrder(Integer.valueOf(order));
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            pubImage.setPid(Integer.valueOf(pid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(11, 0)});
        vu.add("path", path, "文件的绝对路径", new Rule[]{new Length(200)});
        vu.add("oriName", oriName, "原始名称", new Rule[]{new Length(50)});
        vu.add("name", name, "文件名称", new Rule[]{new Length(50)});
        vu.add("remark", remark, "备注", new Rule[]{new Length(50)});
        vu.add("absPath", absPath, "绝对路径", new Rule[]{new Length(50)});
        vu.add("relPath", relPath, "相对路径", new Rule[]{new Length(50)});
        vu.add("figure", figure, "指纹", new Rule[]{new Length(50)});
        vu.add("uploadIp", uploadIp, "照片上传时的Ip", new Rule[]{new Length(50)});
        vu.add("creator", creator, "上传照片人的Id", new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名", new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("lastModify", lastModify, "照片的创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", new Rule[]{new Digits(1, 0)});
        vu.add("order", order, "顺序id", new Rule[]{new Digits(3, 0)});
        vu.add("pid", pid, "父组件id", new Rule[]{new Digits(11, 0)});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return pubImageService.save(pubImage);

    }


    /**
     * 说明:添加PubImage信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个用户信息",
            description = "添加单个用户信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.INTEGER, required = false),
                    @Param(name = "path", description = "文件的绝对路径", dataType = DataType.STRING, required = false),
                    @Param(name = "oriName", description = "原始名称", dataType = DataType.STRING, required = false),
                    @Param(name = "name", description = "文件名称", dataType = DataType.STRING, required = false),
                    @Param(name = "remark", description = "备注", dataType = DataType.STRING, required = false),
                    @Param(name = "absPath", description = "绝对路径", dataType = DataType.STRING, required = false),
                    @Param(name = "relPath", description = "相对路径", dataType = DataType.STRING, required = false),
                    @Param(name = "figure", description = "指纹", dataType = DataType.STRING, required = false),
                    @Param(name = "uploadIp", description = "照片上传时的Ip", dataType = DataType.STRING, required = false),
                    @Param(name = "creator", description = "上传照片人的Id", dataType = DataType.STRING, required = false),
                    @Param(name = "creatorName", description = "上传人的姓名", dataType = DataType.STRING, required = false),
                    @Param(name = "createDate", description = "上传照片的时间", dataType = DataType.DATE, required = false),
                    @Param(name = "lastModify", description = "照片的创建时间", dataType = DataType.DATE, required = false),
                    @Param(name = "status", description = "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", dataType = DataType.INTEGER, required = false),
                    @Param(name = "order", description = "顺序id", dataType = DataType.INTEGER, required = false),
                    @Param(name = "pid", description = "父组件id", dataType = DataType.INTEGER, required = false),
            })
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO save(HttpServletRequest request) throws Exception {
        PubImage pubImage = new PubImage();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        pubImage.setId(Integer.valueOf(id)) ;
                    }
                    
                    String path = request.getParameter("path");
                    if(!StringUtil.isBlank(path)){
                        pubImage.setPath(String.valueOf(path)) ;
                    }
                    
                    String oriName = request.getParameter("oriName");
                    if(!StringUtil.isBlank(oriName)){
                        pubImage.setOriName(String.valueOf(oriName)) ;
                    }
                    
                    String name = request.getParameter("name");
                    if(!StringUtil.isBlank(name)){
                        pubImage.setName(String.valueOf(name)) ;
                    }
                    
                    String remark = request.getParameter("remark");
                    if(!StringUtil.isBlank(remark)){
                        pubImage.setRemark(String.valueOf(remark)) ;
                    }
                    
                    String absPath = request.getParameter("absPath");
                    if(!StringUtil.isBlank(absPath)){
                        pubImage.setAbsPath(String.valueOf(absPath)) ;
                    }
                    
                    String relPath = request.getParameter("relPath");
                    if(!StringUtil.isBlank(relPath)){
                        pubImage.setRelPath(String.valueOf(relPath)) ;
                    }
                    
                    String figure = request.getParameter("figure");
                    if(!StringUtil.isBlank(figure)){
                        pubImage.setFigure(String.valueOf(figure)) ;
                    }
                    
                    String uploadIp = request.getParameter("uploadIp");
                    if(!StringUtil.isBlank(uploadIp)){
                        pubImage.setUploadIp(String.valueOf(uploadIp)) ;
                    }
                    
                    String creator = request.getParameter("creator");
                    if(!StringUtil.isBlank(creator)){
                        pubImage.setCreator(String.valueOf(creator)) ;
                    }
                    
                    String creatorName = request.getParameter("creatorName");
                    if(!StringUtil.isBlank(creatorName)){
                        pubImage.setCreatorName(String.valueOf(creatorName)) ;
                    }
                    
                    String createDate = request.getParameter("createDate");
                    if(!StringUtil.isBlank(createDate)){
                        pubImage.setCreateDate(Date.valueOf(createDate)) ;
                    }
                    
                    String lastModify = request.getParameter("lastModify");
                    if(!StringUtil.isBlank(lastModify)){
                        pubImage.setLastModify(Date.valueOf(lastModify)) ;
                    }
                    
                    String status = request.getParameter("status");
                    if(!StringUtil.isBlank(status)){
                        pubImage.setStatus(Integer.valueOf(status)) ;
                    }
                    
                    String order = request.getParameter("order");
                    if(!StringUtil.isBlank(order)){
                        pubImage.setOrder(Integer.valueOf(order)) ;
                    }
                    
                    String pid = request.getParameter("pid");
                    if(!StringUtil.isBlank(pid)){
                        pubImage.setPid(Integer.valueOf(pid)) ;
                    }
                    */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            pubImage.setId(Integer.valueOf(id));
        }
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            pubImage.setPath(path);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            pubImage.setOriName(oriName);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            pubImage.setName(name);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            pubImage.setRemark(remark);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            pubImage.setAbsPath(absPath);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            pubImage.setRelPath(relPath);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            pubImage.setFigure(figure);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            pubImage.setUploadIp(uploadIp);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            pubImage.setCreator(creator);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            pubImage.setCreatorName(creatorName);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                pubImage.setCreateDate(new Date(createDate));
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                pubImage.setCreateDate(DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                pubImage.setLastModify(new Date(lastModify));
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                pubImage.setLastModify(DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            pubImage.setStatus(Integer.valueOf(status));
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            pubImage.setOrder(Integer.valueOf(order));
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            pubImage.setPid(Integer.valueOf(pid));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(11, 0)});
        vu.add("path", path, "文件的绝对路径", new Rule[]{new Length(200)});
        vu.add("oriName", oriName, "原始名称", new Rule[]{new Length(50)});
        vu.add("name", name, "文件名称", new Rule[]{new Length(50)});
        vu.add("remark", remark, "备注", new Rule[]{new Length(50)});
        vu.add("absPath", absPath, "绝对路径", new Rule[]{new Length(50)});
        vu.add("relPath", relPath, "相对路径", new Rule[]{new Length(50)});
        vu.add("figure", figure, "指纹", new Rule[]{new Length(50)});
        vu.add("uploadIp", uploadIp, "照片上传时的Ip", new Rule[]{new Length(50)});
        vu.add("creator", creator, "上传照片人的Id", new Rule[]{new Length(50)});
        vu.add("creatorName", creatorName, "上传人的姓名", new Rule[]{new Length(50)});
        vu.add("createDate", createDate, "上传照片的时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("lastModify", lastModify, "照片的创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", new Rule[]{new Digits(1, 0)});
        vu.add("order", order, "顺序id", new Rule[]{new Digits(3, 0)});
        vu.add("pid", pid, "父组件id", new Rule[]{new Digits(11, 0)});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        return pubImageService.save(pubImage);

    }

    /**
     * 说明:删除PubImage信息
     *
     * @param request
     * @return ResultDTO
     * @throws Exception
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @API(summary = "根据id删除单个用户信息",
            description = "根据id删除单个用户信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.INTEGER, required = true),
            })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Integer id,
        String idStr = request.getParameter("id");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        pubImageService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 多行删除
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public ResultDTO multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[] = idStr.split(",");
        Integer idAry[] = new Integer[idStrAry.length];
        for (int i = 0, length = idAry.length; i < length; i++) {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            String id = idStrAry[i];
            vu.add("id", id, "编号", new Rule[]{new Digits(11, 0)});

            try {
                validStr = vu.validateString();
            } catch (Exception e) {
                e.printStackTrace();
                validStr = "验证程序异常";
                return ResultUtil.getResult(302, validStr);
            }

            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }
            idAry[i] = Integer.valueOf(idStrAry[i]);
        }
        return pubImageService.multilDelete(idAry);
    }

    /**
     * 导出
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public ResultDTO exportExcel(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            params.put("path", path);
        }
        String pathLike = request.getParameter("pathLike");
        if (!StringUtil.isBlank(pathLike)) {
            params.put("pathLike", pathLike);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            params.put("oriName", oriName);
        }
        String oriNameLike = request.getParameter("oriNameLike");
        if (!StringUtil.isBlank(oriNameLike)) {
            params.put("oriNameLike", oriNameLike);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            params.put("absPath", absPath);
        }
        String absPathLike = request.getParameter("absPathLike");
        if (!StringUtil.isBlank(absPathLike)) {
            params.put("absPathLike", absPathLike);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            params.put("relPath", relPath);
        }
        String relPathLike = request.getParameter("relPathLike");
        if (!StringUtil.isBlank(relPathLike)) {
            params.put("relPathLike", relPathLike);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            params.put("figure", figure);
        }
        String figureLike = request.getParameter("figureLike");
        if (!StringUtil.isBlank(figureLike)) {
            params.put("figureLike", figureLike);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            params.put("uploadIp", uploadIp);
        }
        String uploadIpLike = request.getParameter("uploadIpLike");
        if (!StringUtil.isBlank(uploadIpLike)) {
            params.put("uploadIpLike", uploadIpLike);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            params.put("creator", creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if (!StringUtil.isBlank(creatorLike)) {
            params.put("creatorLike", creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            params.put("creatorName", creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if (!StringUtil.isBlank(creatorNameLike)) {
            params.put("creatorNameLike", creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                params.put("createDate", createDate);
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                params.put("createDate", DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if (!StringUtil.isBlank(createDateBegin)) {
            if (StringUtil.checkNumeric(createDateBegin)) {
                params.put("createDateBegin", createDateBegin);
            } else if (StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")) {
                params.put("createDateBegin", DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if (!StringUtil.isBlank(createDateEnd)) {
            if (StringUtil.checkNumeric(createDateEnd)) {
                params.put("createDateEnd", createDateEnd);
            } else if (StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")) {
                params.put("createDateEnd", DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                params.put("lastModify", lastModify);
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                params.put("lastModify", DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String lastModifyBegin = request.getParameter("lastModifyBegin");
        if (!StringUtil.isBlank(lastModifyBegin)) {
            if (StringUtil.checkNumeric(lastModifyBegin)) {
                params.put("lastModifyBegin", lastModifyBegin);
            } else if (StringUtil.checkDateStr(lastModifyBegin, "yyyy-MM-dd")) {
                params.put("lastModifyBegin", DateUtil.parseToDate(lastModifyBegin, "yyyy-MM-dd"));
            }
        }
        String lastModifyEnd = request.getParameter("lastModifyEnd");
        if (!StringUtil.isBlank(lastModifyEnd)) {
            if (StringUtil.checkNumeric(lastModifyEnd)) {
                params.put("lastModifyEnd", lastModifyEnd);
            } else if (StringUtil.checkDateStr(lastModifyEnd, "yyyy-MM-dd")) {
                params.put("lastModifyEnd", DateUtil.parseToDate(lastModifyEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            params.put("order", order);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }

        // 查询list集合
        List<PubImage> list = pubImageService.listByParams(params);
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
        colTitle.put("path", "文件的绝对路径");
        colTitle.put("oriName", "原始名称");
        colTitle.put("name", "文件名称");
        colTitle.put("remark", "备注");
        colTitle.put("absPath", "绝对路径");
        colTitle.put("relPath", "相对路径");
        colTitle.put("figure", "指纹");
        colTitle.put("uploadIp", "照片上传时的Ip");
        colTitle.put("creator", "上传照片人的Id");
        colTitle.put("creatorName", "上传人的姓名");
        colTitle.put("createDate", "上传照片的时间");
        colTitle.put("lastModify", "照片的创建时间");
        colTitle.put("status", "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态");
        colTitle.put("order", "顺序id");
        colTitle.put("pid", "父组件id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            PubImage sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("path", list.get(i).getPath());
            map.put("oriName", list.get(i).getOriName());
            map.put("name", list.get(i).getName());
            map.put("remark", list.get(i).getRemark());
            map.put("absPath", list.get(i).getAbsPath());
            map.put("relPath", list.get(i).getRelPath());
            map.put("figure", list.get(i).getFigure());
            map.put("uploadIp", list.get(i).getUploadIp());
            map.put("creator", list.get(i).getCreator());
            map.put("creatorName", list.get(i).getCreatorName());
            map.put("createDate", list.get(i).getCreateDate());
            map.put("lastModify", list.get(i).getLastModify());
            map.put("status", list.get(i).getStatus());
            map.put("order", list.get(i).getOrder());
            map.put("pid", list.get(i).getPid());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, fileName, "导出成功");
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
    public void importExcel() {

    }


    /**
     * 说明: 跳转到PubImage列表页面
     *
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/PubImageList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/PubImageListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @RequestMapping(value = "/edit.htm", method = RequestMethod.GET)
    public String editHtml(HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/PubImageEdit.html";
    }

    /**
     * 说明:跳转查看页面
     *
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @RequestMapping(value = "/view.htm", method = RequestMethod.GET)
    public String viewHtml(HttpServletRequest request) {
        return "/static/html/PubImageView.html";
    }


    /**
     * 1.图片--分页查询接口
     *
     * @param accessToken 安全令牌
     * @param params      json格式参数
     * @return json
     * @author 王作品
     * @date 2017年8月29日 下午3:12:25
     */
    @RequestMapping(method = RequestMethod.GET, value = "list", produces = "application/json")
    @ResponseBody
    public ResultDTO getListByParam(HttpServletRequest request, @RequestParam(value = "access_token", required = true) String accessToken,
                                    @RequestParam(value = "params", required = true) String params) throws UnsupportedEncodingException {
        logger.debug("图片查询view接口，Controller传入参数params-----:: " + params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Page page = RequestUtil.getPage(request);
        String imgOrgnalName = CastUtil.toString(paramsMap.get("imgOrgnalName"));
        String createByName = CastUtil.toString(paramsMap.get("createByName"));
        String updateByName = CastUtil.toString(paramsMap.get("updateByName"));

        Integer pageNo = MapUtils.getInteger(paramsMap,"pageNo", 1);//页码，数字，允许为空，默认第1页
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        ValidUtil.valid("开始页数[pageNo]", pageNo, "{'required':true,'numeric':{'pageNo':" + pageNo + "}}");//开始页数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'createTime':" + pageSize + "}}");//记录数量
        HashMap<String, Object> newParams = new HashMap();
        newParams.put("oriName", imgOrgnalName);
        newParams.put("createByName", createByName);
        newParams.put("page", page);
        List<PubImage> list = pubImageService.listByParams4Page(newParams);
        // List<PubImage> list=  pubImageService.getListByParam(imgOrgnalName,createByName,updateByName,page);
        return this.getResult(list, page);
    }


    /**
     * 2.图片上传
     *
     * @author: 王作品
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultDTO pubImageUpload(@RequestParam(value = "access_token", required = true) String accessToken, @RequestParam(value = "file", required = true) MultipartFile image,
                                    @RequestParam(value = "width", required = true) Integer width, @RequestParam(value = "height", required = true) Integer height,
                                    HttpServletRequest request) throws Exception {
        logger.debug("PubImageController传入参数" + "width+height" + width + height);
        String imgOrgnalFilePath = Config.getInstance().getImage().getServerDir();  // 从配置文件中读取储存文件路径
        String imgWidthAndHeightPath = Config.getInstance().getImage().getServerDir() + "/" + width + "_" + height; //生成具有宽高的文件路径
        String originalFileName = image.getOriginalFilename(); //读取原始文件名称
        long imageSize = image.getSize(); //文件大小
        BufferedImage thumbImg, img = ImageIO.read(image.getInputStream());//读取image的流
        String contentType = image.getContentType();//文件类型 image/jpeg ...
//        SessionUser user = SessionUtil.getCurSessionUser(request);

        Integer max_img_size = CastUtil.toInteger(ConfigUtil.getConfig("max_img_size"));//获取配置文件大小
        //判断 image 是否 符合规范
        if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
            throw new ValidException("E3300002", "上传的图片为空!");
        }
        if (StringUtil.isBlank(contentType)) {
            throw new ValidException("E5071010", "图片格式不正确!");
        }
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType) && !"image/bmp".equals(contentType)) {
            throw new ValidException("E5071010", "图片格式不正确!");
        }
        if (imageSize > max_img_size * 1024) {
            throw new ValidException("E3300003", "文件查过大小限制:" + max_img_size);
        }
        //得到原始文件路径
        File imgNamePath = PathManager.getInstance().getWebRootPath().resolve(imgOrgnalFilePath + "original").toFile();
        //得到图片缩略图路径
        File imgThumbNameePath = PathManager.getInstance().getWebRootPath().resolve(imgWidthAndHeightPath).toFile();
        if (!imgNamePath.exists()) {
            imgNamePath.mkdirs();
        }
        if (!imgThumbNameePath.exists()) {
            imgThumbNameePath.mkdirs();
        }
        String imgNameUploadPath = imgNamePath.getAbsolutePath();
        String imgThumbNameeUploadPath = imgThumbNameePath.getAbsolutePath();
        //这里要对文件名提供加密方式System.currentTimeMillis()
        String fileName = MD5Util.getStringMD5String(CastUtil.toString(System.currentTimeMillis() + "Awifi")) + ".jpg";
        String aliasName = MD5Util.getStringMD5String(MD5Util.getStringMD5String(CastUtil.toString(System.currentTimeMillis() + "Awifi"))) + ".jpg";
        String imgUnique = WebImageUtil.getImageMD5(image, Long.valueOf(0), null);
        PubImage pubImage = new PubImage();
        //通过唯一表示判断是否已经上传这张图片
        HashMap<String, Object> params = new HashMap<>();
        params.put("figure", imgUnique);

        List<PubImage> pubImageList = pubImageService.listByParams(params);
        String imgPathName = null;//pubImageService.judgeByImgUnique(imgUnique);
        //如果不存在 则从新将图片 宽高图片写入到磁盘
        if (pubImageList != null && pubImageList.size() != 0) {
            imgPathName = pubImageList.get(0).getAbsPath();
        }
        if (pubImageList == null || pubImageList.size() == 0) {
            try {
                img = ImageUtil.fixSize(img, img.getWidth(), img.getHeight());
                thumbImg = ImageUtil.fixSize(img, width, height);
                ImageIO.write(img, "jpg", new File(imgNameUploadPath, fileName));
                ImageIO.write(thumbImg, "jpg", new File(imgThumbNameeUploadPath, fileName));
            } catch (Exception e) {
                logger.error("图片保存到磁盘失败!" + e.getMessage());
                e.printStackTrace();
                return this.getResult(30102005, "图片保存到磁盘失败");
            }
            pubImage.setName(fileName);
        }

        //为对象添加属性
        pubImage.setAbsPath(imgOrgnalFilePath);
        pubImage.setOriName(originalFileName);
        pubImage.setRemark(aliasName);
        if (imgPathName != null) {
            pubImage.setAbsPath(imgPathName);
        }
        pubImage.setFigure(imgUnique);
//        merchantImage.setCreateBy(user.getId());
//        merchantImage.setCreateByName(user.getUserName());
        pubImage.setCreateDate(new Timestamp(new Date().getTime()));
        pubImage.setUploadIp(request.getRemoteAddr());
        pubImage.setStatus(0);
        pubImageService.save(pubImage);


        //返回前端用户的信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("imgName", fileName);
        resultMap.put("imgAliasName", aliasName);
        resultMap.put("imgPath", pubImage.getAbsPath());
//        resultMap.put("imgTimePathName", imgWidthAndHeightPath+"/"+imgTimePathName);
        return this.getResult(resultMap);
    }


    @API(summary = "文件上传",
            description = "批量关联公司组织关系信息",
            parameters = {
                    @Param(name = "file", description = "文件", in = InType.form, dataType = DataType.FILE, required = false),
                    @Param(name = "width", description = "宽度", in = InType.form, dataType = DataType.INTEGER, required = false),
                    @Param(name = "height", description = "高度", in = InType.form, dataType = DataType.INTEGER, required = false),
                    @Param(name = "fileNamePrefix", description = "文件名称", in = InType.form, dataType = DataType.STRING, required = false),
                    @Param(name = "relativePath", description = "相对路径", in = InType.form, dataType = DataType.STRING, required = false),

            })
    /**
     * 2.图片上传
     *
     * @author: 王作品
     */
    @RequestMapping(value = "file/upload", method = RequestMethod.POST, produces =MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultDTO fileUpload(@RequestParam(value = "access_token", required = false) String accessToken, @RequestParam(value = "file", required = true) MultipartFile image,
                                @RequestParam(value = "width", required = false) Integer width, @RequestParam(value = "height", required = false) Integer height,
                                HttpServletRequest request) throws Exception {
        SysConfig.PATH= request.getContextPath();

        //=============参数获取========================
        String fileNamePrefix = request.getParameter("fileNamePrefix");//文件名称
        String relativePath = request.getParameter("relativePath");//相对位置
        logger.debug("PubImageController传入参数" + "width+height" + width + height);
//        String orignalFilePath= Config.getInstance().getImage().getServerDir();  // 从配置文件中读取储存文件路径
//        String widthAndHeightPath=Config.getInstance().getImage().getServerDir()+"/"+width+"_"+height; //生成具有宽高的文件路径
//        String originalFileName=request.getParameter("original_file_name"); //读取原始文件名称
//        long imageSize = image.getSize(); //文件大小
//        BufferedImage thumbImg,img = ImageIO.read(image.getInputStream());//读取image的流
//        String contentType = image.getContentType();//文件类型 image/jpeg ...
//        SessionUser user = SessionUtil.getCurSessionUser(request);
//
        //      Integer max_img_size = CastUtil.toInteger(ConfigUtil.getConfig("max_img_size"));//获取配置文件大小
        //判断 image 是否 符合规范
        //=============参数校验========================
        String contentType = image.getContentType();

        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType) && !"image/bmp".equals(contentType)) {
            throw new ValidException("E5071010", "图片格式不正确!");
        }
        String type = contentType.substring(6);
        //================================
        BufferedImage bufferedImage;
        int success = 0;
        // String message = "";
        //ByteArrayInputStream in = new ByteArrayInputStream(data);    //将b作为输入流；
        // FileUtil.writeFileFromStream(in,"2.png", Paths.get("/home/colamachine/workspace/code/java/dozenx/ui/src/main/webapp/uploadoriginal/"));
        //bufferedImage = ImageIO.read( new FileInputStream(new File("/home/colamachine/workspace/code/java/dozenx/ui/src/main/webapp/uploadoriginal/"+"1.png")));     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
//        bufferedImage = ImageIO.read(in);
        //bufferedImage.flush();
//        in.close();
        //=========================================
//        if(StringUtil.isBlank(contentType)){
//            throw new ValidException("E5071010","图片格式不正确!");
//        }
//        if ( !"image/jpeg".equals(contentType) && !"image/png".equals(contentType) && !"image/bmp".equals(contentType)) {
//            throw new ValidException("E5071010","图片格式不正确!");
//        }
        //这里要对文件名提供加密方式System.currentTimeMillis()
        //=============通过图片指纹判断是否已经上传这张图片========================
        //首先判断文件是否已经上传过了
        byte[] data = image.getBytes();
        String base64 = Base64Util.encode(data);

        return this.processImg(base64, data, fileNamePrefix, type, relativePath, request);

    }


    public ResultDTO processImg(String base64, byte[] data, String fileNamePrefix, String type, String relativePath, HttpServletRequest request) throws Exception {
        BufferedImage bufferedImage;
        String imgUnique = MD5Util.getStringMD5String(base64);
        PubImage pubImage = new PubImage();
        HashMap<String, Object> params = new HashMap<>();
        params.put("figure", imgUnique);
        List<PubImage> pubImageList = pubImageService.listByParams(params);
        String imgPathName = null;//pubImageService.judgeByImgUnique(imgUnique);

        if (pubImageList != null && pubImageList.size() != 0) {
            pubImage = pubImageList.get(0);
        } else {
            //==============如果不存在 则从新将图片 宽高图片写入到磁盘==========================

            //================看看是否有要求存入那个盘符 文件名是否有强制性要求=======================
            String completeFileName = "";
            if (StringUtil.isBlank(fileNamePrefix))//如果有规定的文件名称 就用规定的文件名称
                fileNamePrefix = MD5Util.getStringMD5String(CastUtil.toString(System.currentTimeMillis() + "Awifi"));

            completeFileName = fileNamePrefix + "." + type;
            String aliasName = MD5Util.getStringMD5String(MD5Util.getStringMD5String(CastUtil.toString(System.currentTimeMillis() + "Awifi"))) + ".jpg";

            try {
                //================================
                //得到原始文件路径
                Path rootPath = PathManager.getInstance().getWebRootPath().resolve(Config.getInstance().getImage().getServerDir());
                Path originalPath = rootPath.resolve("original");
                if (StringUtil.isNotBlank(relativePath)) {
                    originalPath = originalPath.resolve(relativePath);
                }
                pubImage.setAbsPath(originalPath.toString());
                File originalDir = originalPath.toFile();
                Path thumbPath = rootPath.resolve("thumb");
                if (StringUtil.isNotBlank(relativePath)) {
                    thumbPath = thumbPath.resolve(relativePath);
                }

                File thumbDir = thumbPath.toFile();
                Path formatPath = rootPath.resolve("jpg");
                if (StringUtil.isNotBlank(relativePath)) {

                    formatPath = formatPath.resolve(relativePath);
                }
                File formatDir = formatPath.toFile();//原始数据先保存一遍


                //得到图片缩略图路径
                if (!originalDir.exists()) {
                    originalDir.mkdirs();
                }
                if (!thumbDir.exists()) {
                    thumbDir.mkdirs();
                }
                if (!formatDir.exists()) {
                    formatDir.mkdirs();
                }
                FileUtil.writeFile(originalPath.resolve(completeFileName).toFile(), data);
                // WebImageUtil.saveUploadFileToDisk(image,originalPath.toString(),fileName+"."+type);
                //ImageUtil.saveBase64Image(originalPath.toString(), fileName + ".png", base64);

                File file = originalPath.resolve(completeFileName).toFile();

                //bufferedImage = ImageUtil.fixSize(bufferedImage, bufferedImage.getWidth(), bufferedImage.getHeight());
//                if(type.equals("jpeg")){
//                    FileUtil.copyFile(  file,formatPath.resolve(fileName + ".jpg").toFile());
//                }else {
                bufferedImage = ImageUtil.saveAsJpg(new FileInputStream(file), formatPath, fileNamePrefix + ".jpg");
//                }
//              //  BufferedImage formatImg = ImageUtil.fixSize(bufferedImage, width, width);
//                ImageIO.write(bufferedImage, "png",  new File(formatDir,fileName));
                logger.info("存储文件位置" + formatPath + "/" + fileNamePrefix);

                BufferedImage thumbImg = ImageUtil.fixSize(bufferedImage, 50, 50);

                ImageIO.write(thumbImg, "jpg", new File(thumbDir, fileNamePrefix + ".jpg"));
                logger.info("存储缩略图文件位置" + thumbPath + "/" + fileNamePrefix);


                pubImage.setAbsPath(file.getAbsolutePath());

                pubImage.setRelPath(FilePathUtil.joinPath(Config.getInstance().getImage().getServerUrl(), "/", "jpg", relativePath));//相对路径
            } catch (Exception e) {
                logger.error("图片保存到磁盘失败!" + e.getMessage());
                e.printStackTrace();
                return this.getResult(30102005, "图片保存到磁盘失败");
            }


            pubImage.setName(fileNamePrefix + ".jpg");
            //为对象添加属性
//        pubImage.setOriName(orig);

            pubImage.setRemark(aliasName);

            pubImage.setFigure(imgUnique);
//        merchantImage.setCreateBy(user.getId());
//        merchantImage.setCreateByName(user.getUserName());
            pubImage.setCreateDate(new Timestamp(new Date().getTime()));

            pubImage.setStatus(0);

            pubImage.setUploadIp(request.getRemoteAddr());
            SessionUser sessionUser = this.getUser(request);
            if (sessionUser != null) {
                pubImage.setCreator(sessionUser.getUserId() + "");


                pubImage.setCreatorName(sessionUser.getUserName());
            }

            pubImageService.save(pubImage);


            //返回前端用户的信息
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("imgName", fileName);
//            resultMap.put("imgAliasName", aliasName);
//            resultMap.put("imgPath", pubImage.getAbsPath());
//            resultMap.put("id", pubImage.getId());
//            resultMap.put("url", Config.getInstance().getImage().getServerUrl() + "/jpg/" + fileName + ".jpg");
        }
//        resultMap.put("imgTimePathName", imgWidthAndHeightPath+"/"+imgTimePathName);

        return this.getResult(FilePathUtil.joinPath(Config.getInstance().getImage().getServerUrl(), "/", "jpg", relativePath, pubImage.getName()));

    }

    /**
     * 2.图片上传
     *
     * @author: 王作品
     */

    @API(summary = "图片列表接口",
            description = "图片列表接口",
            parameters = {
                    @Param(name = "accessToken", description = "accessToken", dataType = DataType.STRING, required = false),
                    @Param(name = "width", description = "width", dataType = DataType.INTEGER, required = false),
                    @Param(name = "height", description = "height", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "base64", description = "base64", dataType = DataType.STRING, required = false),// false
                    @Param(name = "path", description = "文件的绝对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "fileNamePrefix", description = "需要保存的文件名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "relativePath", description = "相对路径", dataType = DataType.STRING, required = false),// false
            })
    @RequestMapping(value = "/base64/upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultDTO base64Upload(@RequestParam(value = "access_token", required = false) String accessToken, @RequestParam(value = "imageData", required = true) String base64,
                                  @RequestParam(value = "width", required = false) Integer width, @RequestParam(value = "height", required = false) Integer height,
                                  HttpServletRequest request) throws Exception {

        //=============参数获取========================
        String fileNamePrefix = request.getParameter("fileNamePrefix");//文件名称
        String relativePath = request.getParameter("relativePath");//相对位置
        logger.debug("PubImageController传入参数" + "width+height" + width + height);
//        String orignalFilePath= Config.getInstance().getImage().getServerDir();  // 从配置文件中读取储存文件路径
//        String widthAndHeightPath=Config.getInstance().getImage().getServerDir()+"/"+width+"_"+height; //生成具有宽高的文件路径
//        String originalFileName=request.getParameter("original_file_name"); //读取原始文件名称
//        long imageSize = image.getSize(); //文件大小
//        BufferedImage thumbImg,img = ImageIO.read(image.getInputStream());//读取image的流
//        String contentType = image.getContentType();//文件类型 image/jpeg ...
//        SessionUser user = SessionUtil.getCurSessionUser(request);
//
        //      Integer max_img_size = CastUtil.toInteger(ConfigUtil.getConfig("max_img_size"));//获取配置文件大小
        //判断 image 是否 符合规范
        //=============参数校验========================
        if (StringUtil.isBlank(base64)) {
            throw new ValidException("E3300002", "上传的图片为空!");
        }
        //================================
        BufferedImage bufferedImage;
        int success = 0;
        String message = "";
        //ByteArrayInputStream in = new ByteArrayInputStream(data);    //将b作为输入流；
        // FileUtil.writeFileFromStream(in,"2.png", Paths.get("/home/colamachine/workspace/code/java/dozenx/ui/src/main/webapp/uploadoriginal/"));
        //bufferedImage = ImageIO.read( new FileInputStream(new File("/home/colamachine/workspace/code/java/dozenx/ui/src/main/webapp/uploadoriginal/"+"1.png")));     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
//        bufferedImage = ImageIO.read(in);
        //bufferedImage.flush();
//        in.close();
        //=========================================
//        if(StringUtil.isBlank(contentType)){
//            throw new ValidException("E5071010","图片格式不正确!");
//        }
//        if ( !"image/jpeg".equals(contentType) && !"image/png".equals(contentType) && !"image/bmp".equals(contentType)) {
//            throw new ValidException("E5071010","图片格式不正确!");
//        }
        //这里要对文件名提供加密方式System.currentTimeMillis()
        //=============通过图片指纹判断是否已经上传这张图片========================
//        String imgUnique = MD5Util.getStringMD5String(base64);
//        PubImage pubImage = new PubImage();
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("figure", imgUnique);
//        List<PubImage> pubImageList = pubImageService.listByParams(params);
//        String imgPathName = null;//pubImageService.judgeByImgUnique(imgUnique);

        try {
            base64 = ImageUtil.processImageBase64(base64);
        } catch (Exception e) {
            logger.error("base64图像有错误", e);
            throw new BizException(301051855, "base64图像有错误");
        }
        String type = "png";
        byte[] data = Base64Util.decode(base64);
        return this.processImg(base64, data, fileNamePrefix, type, relativePath, request);

    }

    /**
     * 3.通过Id和Id的类型获取图片的信息
     * *@author: 王作品
     *
     * @date: 2017/9/20 0020 下午 20:19
     */
    @RequestMapping(value = "chose", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultDTO getPubImageById(@RequestParam(value = "access_token", required = true) String accessToken, @RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "type", required = false) String type,
                                     HttpServletRequest request) throws Exception {
        logger.debug("通过Id和Id的类型获取图片的信息，Controller传入参数--id+type---:: " + id + type);
        PubImage pubImage = new PubImage();
        pubImage = pubImageService.selectByPrimaryKey(id);
        return this.getResult(pubImage);
    }

//    @RequestMapping(method=RequestMethod.GET, value="view/{imgWidthAndHeightPath}/{name}")
//    public String testImgGet(@PathVariable("imgWidthAndHeightPath") String imgWidthAndHeightPath, @PathVariable("name") String name ){
//
//        //TODO size valid
//        name+=".jpg";
//        String[] sizeAry = imgWidthAndHeightPath.split("x");
//        int xSize = Integer.valueOf(sizeAry[0]);
//        int ySize = Integer.valueOf(sizeAry[1]);
//        //==========检查目标尺寸文件是否存在==============
//        String requiredSizeFilePath = configure.getValue("initFileEnv")+imgWidthAndHeightPath+"/"+name;
//        File file =new File(requiredSizeFilePath);
//        if(file.exists()){//存在则直接返回
//            return "/static/"+imgWidthAndHeightPath+"/"+name;
//        }else{
//            //==============根据源文件创建指定大小文件==============
//            String originalFilePath = configure.getValue("initFileEnv")+configure.getValue("orgnal")+"/"+name;
//
//            File originalImg  =new File(originalFilePath);
//            //===============如果原始文件存在==================
//            if(originalImg.exists()){
//                //有原始图片
//                try {
//                    BufferedImage bufImg = ImageIO.read(new FileInputStream(originalImg));
//                    String imgThumbNameeUploadPath = configure.getValue("initFileEnv")+imgWidthAndHeightPath+"/"+name;
//                    BufferedImage thumbImg= ImageUtil.fixSize(bufImg,xSize,ySize);
//                    File destFile = new File(requiredSizeFilePath);
//                    destFile.mkdirs();
//                    ImageIO.write(thumbImg, "jpg", new File(requiredSizeFilePath));
//                }  catch (Exception e) {
//                    logger.error("图片保存到磁盘失败!"+e.getMessage());
//                    e.printStackTrace();
//
//                }
//
//                return "/static/"+imgWidthAndHeightPath+"/"+name;
//            }else{
//                //===============如果原始文件不存在==================
//                //无原始图片
//                throw new BizException("E2500013", ErrorMessage.getErrorMsg("E2500013"));
//
//            }
//
//        }
//
//
//    }
//
//
//    @RequestMapping(value = "test", method = RequestMethod.GET)
//    public String test(){
//        return  "/static/img/a.jpg";
//    }

    /**
     * 4.根据传输的图片名图片宽高查询图片
     *
     * @author: 王作品
     * @date: 2017/9/21 0021 下午 19:37
     */
    @RequestMapping(value = "urlchose", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultDTO urlchose(@RequestParam(value = "access_token", required = true) String accessToken, @RequestParam(value = "imgPaths", required = false) String imgPaths,
                              @RequestParam(value = "width", required = true) Integer width, @RequestParam(value = "height", required = true) Integer height,
                              HttpServletRequest request) throws Exception {
        logger.debug("根据传输的图片名图片宽高查询图片，Controller传入参数--imgPaths+width+height---:: " + imgPaths + width + height);

        //从配置文件中 读取配置信息
        String imgWidthAndHeightPath = Config.getInstance().getImage().getServerDir() + "/" + width + "_" + height;
        File imgFile = new File(Config.getInstance().getImage().getServerDir() + "/" + imgPaths);
        File imgWidthAndHeightImg = new File(imgWidthAndHeightPath + "/" + imgPaths);
        // 获取文件大小
        long imgWidthAndHeightImgSize = imgWidthAndHeightImg.getTotalSpace();
        if (!imgFile.exists()) {
            throw new ValidException("E2000070", "图片不存在!");
        }
        Map<String, Object> resultMap = new HashMap<>();
        //读取到符合宽高的文件则将路径返给前端
        if (!imgWidthAndHeightImg.exists()) {
            resultMap.put("ImgThumbPath", Config.getInstance().getImage().getServerDir() + "/" + width + "_" + height + "/" + imgPaths);
            return this.getResult(resultMap);
        }
        BufferedImage thumbImg = ImageIO.read(new FileInputStream(imgFile));
        File imgThumbNameePath = PathManager.getInstance().getWebRootPath().resolve(imgWidthAndHeightPath).toFile();
        if (!imgThumbNameePath.exists()) {
            imgThumbNameePath.mkdirs();
        }
        String imgThumbNameeUploadPath = imgThumbNameePath.getAbsolutePath();
        // 将粗略图 写入磁盘
        try {
            thumbImg = ImageUtil.fixSize(thumbImg, width, height);
            ImageIO.write(thumbImg, "jpg", new File(imgThumbNameeUploadPath, imgPaths));
        } catch (Exception e) {
            logger.error("图片保存到磁盘失败!" + e.getMessage());
            e.printStackTrace();
            return this.getResult();
        }
        resultMap.put("ImgThumbPath", Config.getInstance().getImage() + "/" + width + "_" + height + "/" + imgPaths);
        return this.getResult(resultMap);
    }

    @RequestMapping(method = RequestMethod.GET, value = "view/{imgWidthAndHeightPath}/{name}")
    public String test(@PathVariable("size") String imgWidthAndHeightPath, @PathVariable("name") String name) {

        //TODO size valid
        String[] sizeAry = imgWidthAndHeightPath.split("x");
        int xSize = Integer.valueOf(sizeAry[0]);
        int ySize = Integer.valueOf(sizeAry[1]);
        //==========检查目标尺寸文件是否存在==============
        String requiredSizeFilePath = Config.getInstance().getImage().getServerDir() + "/" + imgWidthAndHeightPath + "/" + name;
        File file = new File(requiredSizeFilePath);
        if (file.exists()) {//存在则直接返回
            return "/" + imgWidthAndHeightPath + "/" + name;
        } else {
            //==============根据源文件创建指定大小文件==============
            String originalFilePath = Config.getInstance().getImage().getServerDir() + "/" + "original" + "/" + name;

            File originalImg = new File(originalFilePath);
            //===============如果原始文件存在==================
            if (file.exists()) {
                //有原始图片
                try {
                    BufferedImage bufImg = ImageIO.read(new FileInputStream(originalImg));
                    String imgThumbNameeUploadPath = Config.getInstance().getImage().getServerDir() + imgWidthAndHeightPath + "/" + name;
                    BufferedImage thumbImg = ImageUtil.fixSize(bufImg, xSize, ySize);
                    ImageIO.write(thumbImg, "jpg", new File(requiredSizeFilePath));
                } catch (Exception e) {
                    logger.error("图片保存到磁盘失败!" + e.getMessage());
                    e.printStackTrace();

                }

                return "/" + imgWidthAndHeightPath + "/" + name;
            } else {
                //===============如果原始文件不存在==================
                //无原始图片
                throw new BizException("E2500013", ErrorMessage.getErrorMsg("E2500013"));

            }

        }


    }


    /**
     * 5.逻辑删除图片(移动图片 到其他文件夹)
     *@author: 王作品
     *@date: 2017/9/21 0021 下午 19:37
     */
//    @RequestMapping(method=RequestMethod.DELETE, value="slice",produces = "application/json")
//    @ResponseBody
//    public ResultDTO IdMoveToTher(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="ids",required=true)String ids,
//                                  @RequestParam(value="imgPaths",required= false)String imgPathNames,HttpServletRequest request) throws Exception{
//
//        logger.debug("逻辑删除图片(移动图片 到其他文件夹)，Controller传入参数--ids+imgPathNames---:: "+ids+imgPathNames);
//        // 获取 对应的ids 文件路径储存 来移动图片（批量 移动图片）
//        Long[] idsLong = CastUtil.toLongArray(ids.split(","));
//        String[] imgPathNameString=null;
//        File file=new File(configure.getValue("initFileEnv"));
//        if( imgPathNames !=null){
//            imgPathNameString=imgPathNames.split(",");
//            String startFile=configure.getValue("initFileEnv");
//            String endFile=configure.getValue("tempFileEnv");
//            for (int i=0;i<imgPathNameString.length;i++){
//                ImageUtil.removeFile(startFile,endFile,imgPathNameString[i]);
//            }
//        }
//
//        pubImageService.deleteImgTotherFolders(configure.getValue("tempFileEnv"),idsLong);
//        return this.getResult();
//    }


    /**
     * 6.根据传输的文件别名逻辑删除图片(移动图片 到其他文件夹)
     *
     * @author: 王作品
     * @date: 2017/9/21 0021 下午 19:37
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "urlslice", produces = "application/json")
    @ResponseBody
    public ResultDTO urlMoveToTher(@RequestParam(value = "access_token", required = true) String accessToken, @RequestParam(value = "imgAliasName", required = true) String imgAliasName,
                                   HttpServletRequest request) throws Exception {


        logger.debug("逻辑删除图片(移动图片 到其他文件夹)，Controller传入参数--imgPathNames---::" + imgAliasName);
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", imgAliasName);
        String imgPathName = null;
        List<PubImage> list = pubImageService.listByParams(params);                       //首先得到真实的文件存储名称
        File imgFile = new File(Config.getInstance().getImage().getServerDir() + "/" + "original" + "/" + imgPathName);
        long imageSize = imgFile.getTotalSpace();
        if (imageSize <= 0) {
            throw new ValidException("E2000070", ErrorMessage.getErrorMsg("E2000070", "图片已经删除!"));
        }

        int count = list.size();//pubImageService.countByImgPathName(imgPathName);
        if (count > 1) {
            //pubImageService.deleteImgToFoldersByUrl(configure.getValue("tempFileEnv"),imgAliasName);
            pubImageService.delete(list.get(0).getId());
            return this.getResult();
        }
        PubImage pubImage = list.get(0);
        pubImage.setStatus(9);
        pubImage.setAbsPath(Config.getInstance().getImage().getServerDir() + "/" + "temp" + imgPathName);
        ImageUtil.removeFile(Config.getInstance().getImage().getServerDir(), "temp", imgPathName);
        // pubImageService.deleteImgToFoldersByUrl(configure.getValue("tempFileEnv"),imgAliasName);

        return this.getResult();
    }


    /**
     *
     * 7.删除图片（慎用（图片不可恢复））
     *@author: 王作品
     *@date: 2017/9/21 0021 下午 19:37
     */
//    @RequestMapping(method=RequestMethod.DELETE, value="delete",produces = "application/json")
//    @ResponseBody
//    public ResultDTO deleteImgByimgPathName(@RequestParam(value="access_token",required=true)String accessToken,
//                                            HttpServletRequest request) throws Exception{
//
//
//        ImageUtil.deleteFile(configure.getValue("tempFileEnv"),null,false);
//
//        PubImage pubImage=new PubImage();
//        List<String> pubImageList=new ArrayList<String>();
//        pubImageList=pubImageService.getListBySate();
//        if( pubImageList !=null){
//            for (int i=0;i<pubImageList.size();i++){
//                pubImage.setUpdateBy(1111L);
//                pubImage.setUpdateByName("Arrvin");
//                pubImage.setUpdateDate(new Timestamp(new Date().getTime()));
//                pubImage.setImgPathName(pubImageList.get(i));
//                pubImageService.deleteImgByimgPathName(pubImage);
//            }
//        }
//        return this.getResult();
//    }
//
//
//
//    /**
//     * 测试使用 清空 数据库 与图片文件夹
//     * 8.删除图片（慎用（图片不可恢复））
//     *@author: 王作品
//     *@date: 2017/9/21 0021 下午 19:37
//     */
//    @RequestMapping(method=RequestMethod.DELETE, value="deleteAll",produces = "application/json")
//    @ResponseBody
//    public ResultDTO deleteAll(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request) throws Exception{
//
//
//        ImageUtil.deleteFile(configure.getValue("tempFileEnv"),null,false);
//        pubImageService.deleteAll();
//
//        return this.getResult();
//    }


}
