/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.core.sms.service;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.sms.bean.SmsRecord;
import com.dozenx.web.core.sms.dao.SmsRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service("smsRecordService")
public class SmsRecordService {
    private static final Logger logger = LoggerFactory
            .getLogger(SmsRecordService.class);
    @Resource
    private SmsRecordMapper smsRecordMapper;
    /**
     * 说明:list by page and params

     * @return
     * @return List<Role>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SmsRecord> listByParams4Page(HashMap params) {
        return smsRecordMapper.listByParams4Page(params);
    }
     public List<SmsRecord> listByParams(HashMap params) {
        return smsRecordMapper.listByParams(params);
    }

    /*
     * 说明:
     * @param SmsRecord
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SmsRecord smsRecord) {
        // 进行字段验证
       ValidateUtil<SmsRecord> vu = new ValidateUtil<SmsRecord>();
       /* ResultDTO result = vu.valid(smsRecord);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       
       //判断是更新还是插入
        if (smsRecord.getId()==null) {
               
            smsRecordMapper.insert(smsRecord);
        } else {
             smsRecordMapper.updateByPrimaryKey(smsRecord);
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
        smsRecordMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public SmsRecord selectByPrimaryKey(Integer id){
       return smsRecordMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            smsRecordMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
}
