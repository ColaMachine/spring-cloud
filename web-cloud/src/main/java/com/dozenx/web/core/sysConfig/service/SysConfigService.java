/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.sysConfig.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.sysConfig.bean.SysConfig;
import com.dozenx.web.core.sysConfig.dao.SysConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("sysConfigService")
public class SysConfigService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysConfigService.class);
    @Resource
    private SysConfigMapper sysConfigMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysConfig> listByParams4Page(HashMap params) {
        return sysConfigMapper.listByParams4Page(params);
    }
    public List<SysConfig> listByParams(HashMap params) {
        return sysConfigMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return sysConfigMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysConfig
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysConfig sysConfig) {
        // 进行字段验证
      /* ValidateUtil<SysConfig> vu = new ValidateUtil<SysConfig>();
        ResultDTO result = vu.valid(sysConfig);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (sysConfig.getId()==null ||  this.selectByPrimaryKey(sysConfig.getId())==null) {

            sysConfigMapper.insert(sysConfig);
        } else {
            sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
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
        sysConfigMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SysConfig selectByPrimaryKey(Integer id){

       return sysConfigMapper.selectByPrimaryKey(id);
    }
    /**
     * 说明:根据name获取数据
     * descripton:delete by key
     * @param name
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public SysConfig selectByName(String name){
        HashMap param =new HashMap();
        param.put("name",name);
        List<SysConfig> configs =  sysConfigMapper.listByParams(param);
        if(configs!=null && configs.size()>0){
            if(configs.size()>1){
                logger.error("sysconfig key:"+name+" has more than one result");
            }
            return configs.get(0);
        }else{
            return null;
        }
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysConfigMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
