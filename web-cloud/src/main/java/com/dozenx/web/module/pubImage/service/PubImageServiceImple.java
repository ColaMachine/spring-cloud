/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.pubImage.service;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.FileUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.web.module.pubImage.dao.PubImageMapper;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("pubImageService")
@Transactional
public class PubImageServiceImple   implements  PubImageService{
    private static final Logger logger = LoggerFactory
            .getLogger(PubImageServiceImple.class);
    @Resource
    private PubImageMapper pubImageMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    @Override
    public List<PubImage> listByParams4Page(HashMap params) {
        return pubImageMapper.listByParams4Page(params);
    }
    @Override
    public List<PubImage> listByParams(HashMap params) {
        return pubImageMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
     @Override
    public int countByParams(HashMap params) {
           return pubImageMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param PubImage
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    @Override
    public ResultDTO save(PubImage pubImage) {
        // 进行字段验证
      /* ValidateUtil<PubImage> vu = new ValidateUtil<PubImage>();
        ResultDTO result = vu.valid(pubImage);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (pubImage.getId()==null ||  this.selectByPrimaryKey(pubImage.getId())==null) {

            pubImageMapper.insert(pubImage);
        } else {
            pubImageMapper.updateByPrimaryKeySelective(pubImage);
        }

        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    @Override
    public void delete(Integer  id){
        pubImageMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    @Override
    public PubImage selectByPrimaryKey(Integer id){
       return pubImageMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    @Override
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            pubImageMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    @Override
    public PubImage getPubImageByFileName(String fileName){
        Map params = new HashMap();
        if(fileName.indexOf("/")>-1){
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        }
        params.put("name", fileName);
        List<PubImage> list = pubImageMapper.listByParams(params);
        if(list==null || list.size()==0)
            return null;
        return list.get(0);
    }
    @Override
    public ResultDTO copyAnotherFoldWithNewName(String url, String fold, String name) {
        //=====先检索是否存在这张图片在图片库中=============
        HashMap params = new HashMap();
        params.put("name", url.substring(url.lastIndexOf("/")+1));
        logger.debug("原始文件名称"+url.substring(url.lastIndexOf("/")+1));
        List<PubImage> list = pubImageMapper.listByParams(params);
        if (list == null || list.size() == 0) {
            logger.debug("图片信息不存在"+url.substring(url.lastIndexOf("/")+1));
            throw new BizException(305105131, "图片信息不存在");
        }
        logger.debug("找到文件"+list.size());
        PubImage pubImage = list.get(0);
        try {

            File sourceFile =  Paths.get(pubImage.getAbsPath()).toFile();
            if(!sourceFile.exists()){
                throw new BizException(305105147, "图片文件不存在");
            }
            File directory =Paths.get(fold).toFile();
            if(!directory.exists()){
                directory.mkdirs();
            }
            logger.debug("拷贝文件从"+pubImage.getAbsPath()+"到"+Paths.get(fold).resolve(name).toFile());
            FileUtil.copyFile(Paths.get(pubImage.getAbsPath()).toFile(), Paths.get(fold).resolve(name).toFile());
        } catch (IOException e) {
            logger.error("",e);
            throw new BizException(305105147, "图片拷贝异常"+e.getMessage());
        }

        return null;
    }
}
