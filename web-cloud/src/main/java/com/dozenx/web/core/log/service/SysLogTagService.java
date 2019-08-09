/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.log.service;


import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.bean.SysLogTag;
import com.dozenx.web.core.log.dao.SysLogTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("sysLogTagService")
public class SysLogTagService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysLogTagService.class);
    @Resource
    private SysLogTagMapper sysLogTagMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysLogTag> listByParams4Page(HashMap params) {
        return sysLogTagMapper.listByParams4Page(params);
    }
    public List<SysLogTag> listByParams(HashMap params) {
        return sysLogTagMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysLogTagMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysLogTag
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysLogTag sysLogTag) {
        // 进行字段验证
      /* ValidateUtil<SysLogTag> vu = new ValidateUtil<SysLogTag>();
        ResultDTO result = vu.valid(sysLogTag);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysLogTag.getId()==null ||  this.selectByPrimaryKey(sysLogTag.getId())==null) {

            sysLogTagMapper.insert(sysLogTag);
        } else {
            sysLogTagMapper.updateByPrimaryKeySelective(sysLogTag);
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
    public void delete(Integer  id){
        sysLogTagMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysLogTag selectByPrimaryKey(Integer id){
       return sysLogTagMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysLogTagMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
