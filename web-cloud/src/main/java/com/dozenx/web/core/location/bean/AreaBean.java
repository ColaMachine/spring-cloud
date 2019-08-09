package com.dozenx.web.core.location.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:36 2018/5/8
 * @Modified By:
 */
public class AreaBean {
    private static final Logger LOG = LoggerFactory.getLogger(AreaBean.class);

    private Integer province;
    private Integer city;
    private Integer county;


    private static final Integer Infinity=10000;
    public AreaBean(){

    }
    public AreaBean(Long province, Long city, Long county){
       this(province==null?null:province.intValue(),
               city==null?null:city.intValue(),
               county==null?null:county.intValue());
    }
    public AreaBean(Integer province, Integer city, Integer county){
        this.province =province;
        this.city=city;
        this.county=county;




    }
    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }



    public String toString(){
        StringBuilder sb =new StringBuilder();
        sb.append(province==null?"":province).append(",").append(city==null?"":city).append(",").append(county==null?"":county);
        return sb.toString();
    }
}
