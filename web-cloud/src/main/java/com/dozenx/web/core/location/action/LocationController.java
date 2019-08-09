package com.dozenx.web.core.location.action;

import com.dozenx.common.util.JsonUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.location.service.LocationService;
import com.dozenx.web.core.log.ResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:13 2018/2/26
 * @Modified By:
 */

@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = Constants.WEBROOT+"/db/location/")
public class LocationController extends BaseController {

    /**地区服务层*/
    @Resource(name = "locationService")
    private LocationService locationService;

    /**
     * 获取所有省
     * @return 省
     * @author 周颖
     * @throws Exception
     * @date 2017年1月22日 下午1:48:35
     */
    @RequestMapping(method = RequestMethod.GET,value = "/dif/provinces")
    @ResponseBody
    public ResultDTO getProvinces() throws Exception{
        List<Map<String,Object>> provinceMap = locationService.getProvinces();//获取所有省
        return this.getDataResult(provinceMap);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/provinces")
    public void  getProvinces( HttpServletResponse response) throws Exception{

        String jsonStr=  locationService.getProvincesJsonStr();//获取所有省
        writeJsonStr(response,"{\"r\":0,\"data\":"+jsonStr+"}");

        return ;
    }

    /**
     * 获取所有市
     * @param parentId 省id
     * @return 市
     * @throws Exception
     * @author 周颖
     * @date 2017年1月22日 下午3:03:30
     */
    @RequestMapping(method = RequestMethod.GET,value = "/dif/cities")
    @ResponseBody
    public ResultDTO getCities(@RequestParam(value="parentid",required=true) String parentId) throws Exception{
        List<Map<String,Object>> cityMap = locationService.getCities(parentId);//获取所有市
        return this.getDataResult(cityMap);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/cities")
    public void getCitiesJsonStr(@RequestParam(value="parentid",required=true) String parentId,HttpServletResponse response) throws Exception{
       String jsonStr=  locationService.getCitiesJsonStr(parentId);//获取所有市
        writeJsonStr(response,"{\"r\":0,\"data\":"+jsonStr+"}");
        return ;
    }

    /**
     * 获取所有区县
     * @param parentId 市id
     * @return 区县
     * @throws Exception
     * @author 周颖
     * @date 2017年1月23日 上午9:07:37
     */
    @RequestMapping(method = RequestMethod.GET,value = "/dif/areas")
    @ResponseBody
    public ResultDTO getAreas(@RequestParam(value="parentid",required=true) String parentId) throws Exception{
        List<Map<String,Object>> areaMap = locationService.getAreas(parentId);//获取所有区县
        return this.getDataResult(areaMap);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/areas")
    public void getAreas(@RequestParam(value="parentid",required=true) String parentId,HttpServletResponse response) throws Exception{

        String jsonStr=  locationService.getAreasJsonStr(parentId);//获取所有市
        writeJsonStr(response,"{\"r\":0,\"data\":"+jsonStr+"}");
        return ;
    }
}
