package com.dozenx.web.core.location.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:36 2018/5/8
 * @Modified By:
 */
public class AreaCompareBean {
    private static final Logger LOG = LoggerFactory.getLogger(AreaCompareBean.class);

    private Integer province;
    private Integer city;
    private Integer county;

    private Integer minProvince;

    private Integer maxProvince;

    private Integer maxCity;
    private Integer minCity;
    private Integer minCounty;
    private Integer maxCounty;
    private static final Integer Infinity=10000;
    public AreaCompareBean(){

    }
    public AreaCompareBean(Long province, Long city, Long county){
       this(province==null?null:province.intValue(),
               city==null?null:city.intValue(),
               county==null?null:county.intValue());
    }

    public void init(){
        if( this.minProvince==null || this.maxProvince==null ||  this .minCity==null || this.maxCity ==null
                || this.minCounty ==null || this.maxCounty ==null){
            this.minProvince=0;
            this.maxProvince=Infinity;

            this.minCity=0;
            this.maxCity=Infinity;

            this.minCounty=0;
            this.maxCounty=Infinity;

            if(this.province!=null && this.province!=0){
                this.minProvince=this.province;
                this.maxProvince=this.province;

                if(this.city!=null && this.city!=0){
                    this.minCity=this.city;
                    this.maxCity=this.city;

                    if(this.county!=null && this.county!=0){
                        this.minCounty=this.county;
                        this.maxCounty=this.county;

                    }
                }


            }
        }
    }
    public AreaCompareBean(Integer province, Integer city, Integer county){
        this.province =province;
        this.city=city;
        this.county=county;

        init();
    }
    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
        this.minProvince=0;
        this.maxProvince=Infinity;

        if(this.province!=null && this.province!=0) {
            this.minProvince = this.province;
            this.maxProvince = this.province;
        }

    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;

        this.minCity=0;
        this.maxCity=Infinity;

        if(this.city!=null && this.city!=0){
            this.minCity=this.city;
            this.maxCity=this.city;
        }
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;

        this.minCounty=0;
        this.maxCounty=Infinity;

        if(this.county!=null && this.county!=0){
            this.minCounty=this.county;
            this.maxCounty=this.county;
        }
    }

    public boolean contain(AreaCompareBean otherArea){
            //如果是通过json 转过来 minProinvce可能是为空的
        if(this.minProvince<=otherArea.minProvince
                && this.maxProvince >= otherArea.maxProvince
                &&this.minCity<=otherArea.minCity
                && this.maxCity >= otherArea.maxCity
                && this.minCounty<=otherArea.minCounty
                && this.maxCounty>=otherArea.maxCounty){
            return true;
        }
        return   false;
//        if(this.getProvince()==null || otherArea.getProvince()==null){
//            LOG.error("the areabean's province should not be null  value");
//            return false;
//        }
//        //在有值的基础上
//        if(this.getProvince()!=null && otherArea.getProvince()!=null){
//            if(this.getProvince()==0){//如果 本项目是全国 那么 就是包含子项的
//                return true;
//            }
//            if(this.getProvince()!=otherArea.getProvince()){
//                return false;//排除省不对
//            }
//            //省都一样
//            if(this.getCity() !=null && otherArea.getCity()==null){
//                return false;//不包含
//            }
//        }
    }

    public String toString(){
        StringBuilder sb =new StringBuilder();
        sb.append(province==null?"":province).append(",").append(city==null?"":city).append(",").append(county==null?"":county);
        return sb.toString();
    }
}
