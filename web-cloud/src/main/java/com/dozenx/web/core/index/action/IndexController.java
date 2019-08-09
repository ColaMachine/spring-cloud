package com.dozenx.web.core.index.action;

import com.dozenx.common.config.SysConfig;
import com.dozenx.web.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:26 2018/3/28
 * @Modified By:
 */
@Controller

public class IndexController extends BaseController {

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        // System.out.println(request.getParameter("path"));
        // System.out.println(request.getSession().getAttribute("path"));
        // System.out.println(request.getServletContext().getAttribute("path"));
        request.setAttribute("path", SysConfig.PATH);
        // logger.debug("s");
        // LogUtil.debug("nihao");
        // System.out.println(123);
        return "/jsp/index.jsp";
    }

    @RequestMapping(value = "/manage.htm", method = RequestMethod.GET)
    public String manage(HttpServletRequest request) {
        // System.out.println(request.getParameter("path"));
        // System.out.println(request.getSession().getAttribute("path"));
        // System.out.println(request.getServletContext().getAttribute("path"));
        request.setAttribute("path", SysConfig.PATH);
        // logger.debug("s");
        // LogUtil.debug("nihao");
        // System.out.println(123);
        return "/jsp/manage.jsp";
    }


}
