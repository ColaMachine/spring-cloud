package com.dozenx.web.util;

import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:26 2018/3/12
 * @Modified By:
 */
public class SpringUtil {
    /**
     * 日志
     **/
    private static final Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    private static String getRequestMappingValue(RequestMapping requestMapping) {
        if (requestMapping == null) {
            return null;
        }
        String[] values = requestMapping.value();
        if (values == null || values.length <= 0) {
            return null;
        }
        return values[0];
    }

    public static void main(String args[]) {
//        System.out.println("获取class 头部注解");
//        RequestMapping classRequestMapping = SysUserController.class.getAnnotation(RequestMapping.class);
//        String classMappingValue = getRequestMappingValue(classRequestMapping);
//        logger.debug("class  request mapping "+classMappingValue);
    }

    public static String getRequestMappingUrl(Method method) {


        StringBuffer interfaceCode = new StringBuffer();
        //1.获取类中@RequestMapping配置的路径
        RequestMapping classRequestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        String classMappingValue = getRequestMappingValue(classRequestMapping);
        logger.debug("class  request mapping " + classMappingValue);
        if (classMappingValue != null) {
            interfaceCode.append(classMappingValue);
        }
        //2.获取方法中@RequestMapping配置的路径
        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
        String methodMappingValue = getRequestMappingValue(methodRequestMapping);
        System.out.println();

        if (methodMappingValue != null) {
            interfaceCode.append(methodMappingValue);
        }
        String annotationMethod = getRequestMappingMethod(methodRequestMapping);//http请求方式
        if (StringUtil.isBlank(annotationMethod)) {
            interfaceCode.append(":").append(annotationMethod);
        }
        return interfaceCode.toString();
    }


    /**
     * 获取 @RequestMapping中的method
     *
     * @param requestMapping requestMapping
     * @return method
     * @author zhangzw
     * @date 2018年3月12日19:58:35
     */
    private static String getRequestMappingMethod(RequestMapping requestMapping) {
        if (requestMapping == null) {
            return null;
        }
        RequestMethod[] requestMethod = requestMapping.method();
        if (requestMethod == null || requestMethod.length <= 0) {
            return null;
        }
        return requestMethod[0].name();
    }

    /**
     * @param packageSearchPath //"classpath*:com/dozenx/web/xxxx.class"
     * @throws IOException
     */
    public static Resource[] getResource(String packageSearchPath) throws IOException {
        //String packageSearchPath = "classpath*:com/dozenx/web/**/*.class";
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        System.out.println();
        for (Resource resource : resources) {
            System.out.println(resource.toString());
        }
        return resources;
    }
}
