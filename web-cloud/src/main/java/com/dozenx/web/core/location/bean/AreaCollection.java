package com.dozenx.web.core.location.bean;

import com.dozenx.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:38 2018/5/8
 * @Modified By:
 */
public class AreaCollection {

    private List<AreaCompareBean> areaBeanList = new ArrayList<>();

    public static void main(String args[]) {
        AreaCollection areaCollection = new AreaCollection();
        areaCollection.add(new AreaCompareBean(31, 2, null));
        areaCollection.add(new AreaCompareBean(31, 2, null));
        String s = (areaCollection.toString());
        AreaCompareBean otherArea = new AreaCompareBean(31, 0, 0);
        if (areaCollection.contain(otherArea)) {
            System.out.print("hello");
        }
        areaCollection.parse("31,2,;31,2,;");
        System.out.print(areaCollection.toString());
    }

    /**
     * 添加地区要求
     *
     * @param areaBean
     */
    public void add(AreaCompareBean areaBean) {
        areaBeanList.add(areaBean);
    }

    /**
     * 是否一个地区集合包含另外一个地区
     *
     * @param areaBean
     * @return
     */
    public boolean contain(AreaCompareBean areaBean) {
        if (areaBeanList.size() == 0) {
            return false;
        }
        for (int i = 0, length = areaBeanList.size(); i < length; i++) {
            AreaCompareBean areaRequest = areaBeanList.get(i);
            if (areaRequest.contain(areaBean)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析
     * @param content
     */
    public void parse(String content) {
        this.areaBeanList.clear();
        StringTokenizer st = new StringTokenizer(content, ";");
        while (st.hasMoreElements()) {
            String token = st.nextToken().toString();
            if (StringUtil.isBlank(token)) {
                continue;
            }
            AreaCompareBean areaBean = new AreaCompareBean();
            String arg[] = token.toString().split(",");
            Integer province = StringUtil.isBlank(arg[0]) ? 0 : Integer.valueOf(arg[0]);
            areaBean.setProvince(province);
            if (arg.length > 1) {
                Integer city = StringUtil.isBlank(arg[1]) ? 0 : Integer.valueOf(arg[1]);
                areaBean.setCity(city);
                if (arg.length > 2) {
                    Integer county = StringUtil.isBlank(arg[2]) ? 0 : Integer.valueOf(arg[2]);
                    areaBean.setCounty(county);
                }
            }
            this.add(areaBean);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < areaBeanList.size(); i++) {
            sb.append(areaBeanList.get(i).toString()).append(";");
        }
        return sb.toString();
    }

}
