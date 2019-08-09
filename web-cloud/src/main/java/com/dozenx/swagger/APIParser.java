/*
 * Copyright 2011-2017 CPJIT Group.
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.dozenx.swagger;

import com.alibaba.fastjson.JSONWriter;
import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.annotation.*;
import com.dozenx.swagger.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 接口解析器。
 * <p>
 * <p>可以通过如下的方式来构建一个接口解析器：</p>
 * <p>
 * <pre>
 * 	// 创建一个构建器
 * 	String host = "127.0.0.1/app";
 * 	String file = "c:/apis.json";
 * 	String[] packageToScan = new String[]{"com.cpj.demo.api"};
 * 	APIParser.Builder builder = new APIParser.Builder(host, file, packageToScan);
 *
 * 	// 设置可选参数
 * 	builder.basePath("/");
 *
 * 	// 构建解析器
 * 	APIParser parser = builder.build();
 * 	// 解析
 * 	parser.parse();
 * </pre>
 * <p>或者通过这种方式来构建一个接口解析器：</p>
 * <pre>
 * 	APIParser.newInstance(props);
 * </pre>
 *
 * @author yonghaun
 * @since 1.0.0
 */
public final class APIParser implements APIParseable {
    private final static Logger sLogger = LoggerFactory.getLogger(APIParser.class);
    private final static Logger logger = LoggerFactory.getLogger(APIParser.class);

    /**
     * 创建一个解析器。
     *
     * @param props properties。
     * @throws IOException
     * @see APIParser.Builder
     */
    public final static APIParser newInstance(Properties props) throws IOException {
        String[] packageToScan = props.getProperty("packageToScan").split(";");
        Builder builder = new Builder(props.getProperty("apiHost"), props.getProperty("apiFile"), packageToScan)
                .basePath(props.getProperty("apiBasePath"))
                .description(props.getProperty("apiDescription"))
                .termsOfService(props.getProperty("termsOfService"))
                .title(props.getProperty("apiTitle"))
                .version(props.getProperty("apiVersion"))
                .suffix(props.getProperty("suffix"));
        return new APIParser(builder);
    }

    private APIParser(Builder builder) {
        // 扫描class并生成文件所需要的参数
        this.host = builder.host;
        this.file = builder.file;
        this.packageToScan = builder.packageToScan;
        try {
            packages = ReflectUtils.scanPackages(this.packageToScan, true);
        } catch (Exception e) {
            throw new IllegalStateException("扫描包信息失败", e);
        }
        this.basePath = builder.basePath;
        this.suffix = builder.suffix;

        // API文档信息
        info = new APIDocInfo.Builder()
                .contact(builder.contact)
                .description(builder.description)
                .license(builder.license)
                .termsOfService(builder.termsOfService)
                .title(builder.title)
                .version(builder.version).build();
    }

    private String host;
    private String basePath;
    private String suffix = "";

    /**
     * @author yonghuan
     */
    public static class Builder {
        // required args
        private String host;
        private String file;
        private List<String> packageToScan;

        /**
         * 创建一个构建器。
         *
         * @param host          API访问地址（不包含协议）
         * @param file          解析产生的文件的存放路径
         * @param packageToScan 待扫描的包
         */
        public Builder(String host, String file, String[] packageToScan) {
            this(host, file, Arrays.asList(packageToScan));
        }

        /**
         * 创建一个构建器。
         *
         * @param host          API访问地址（不包含协议）
         * @param file          解析产生的文件的存放路径
         * @param packageToScan 待扫描的包
         */
        public Builder(String host, String file, List<String> packageToScan) {
            this.host = host;
            this.file = file;
            this.packageToScan = packageToScan;
        }

        /**
         * 构建解析器。
         *
         * @return
         */
        public APIParser build() {
            return new APIParser(this);
        }

        private String basePath;

        /**
         * 设置API相对于host（API访问地址）的基路径
         *
         * @param val API相对于host（API访问地址）的基路径
         * @return
         */
        public Builder basePath(String val) {
            this.basePath = val;
            return this;
        }

        private String suffix = "";

        /**
         * 设置请求地址的后缀，如：.do、.action。
         *
         * @param suffix 请求地址的后缀
         * @return
         */
        public Builder suffix(String suffix) {
            if (StringUtil.isNotBlank(suffix)) {
                this.suffix = suffix;
            }
            return this;
        }

        private String description;

        /**
         * 设置API描述
         *
         * @param val API描述
         * @return
         */
        public Builder description(String val) {
            this.description = val;
            return this;
        }

        private String version;

        /**
         * 设置API版本
         *
         * @param val API版本
         * @return
         */
        public Builder version(String val) {
            this.version = val;
            return this;
        }

        private String title;

        /**
         * 设置API标题
         *
         * @param val API标题
         * @return
         */
        public Builder title(String val) {
            this.title = val;
            return this;
        }

        private String termsOfService;

        /**
         * 设置API开发团队的服务地址
         *
         * @param val API开发团队的服务地址
         * @return
         */
        public Builder termsOfService(String val) {
            this.termsOfService = val;
            return this;
        }

        private String contact;

        /**
         * 设置API开发团队的联系人
         *
         * @param val API开发团队的联系人
         * @return
         */
        public Builder contact(String val) {
            this.contact = val;
            return this;
        }

        private License license;

        /**
         * 设置API遵循的协议（如apahce开源协议）
         *
         * @param val API遵循的协议（如apahce开源协议）
         * @return
         */
        public Builder license(License val) {
            try {
                this.license = val.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
            return this;
        }
    }

    private APIDocInfo info;

    private String file;

    /**
     * @return 解析完成后存放JSON数据的文件路径。
     */
    public String getFile() {
        return file;
    }

    private List<String> packageToScan;

    /**
     * @return 待解析接口所在包
     */
    public List<String> getPackageToScan() {
        return packageToScan;
    }

    private List<Package> packages;

    private Map<String, Item> items;

    //@Override
    public void parse() throws Exception {
        /* 将结果写入文件 */
        File f = new File(file);
        sLogger.debug("生成的文件保存在=>" + f.getAbsolutePath());
        JSONWriter writer = new JSONWriter(new FileWriter(f));
        APIDoc api = (APIDoc) parseAndNotStore();
        writer.writeObject(api);
        writer.flush();
        writer.close();
    }

    //@Override
    public Object parseAndNotStore() throws Exception {
        APIDoc api = new APIDoc();
        String[] schemes = new String[]{"http"};
        api.setSchemes(schemes);
        api.setHost(host);
        api.setBasePath(basePath);
        api.setInfo(info);

		/* 解析全部item */
        items = parseItem();

		/* 解析全部tag */
        List<Tag> tags = parseTag();
        api.setTags(tags);

		/* 解析全部path */
        Map<String, Map<String, Path>> paths = parsePath();
        api.setPaths(paths);

		/* 解析全部definition */
        Map<String, Object> definitions = parseDefinition();
        api.setDefinitions(definitions);
        return api;
    }

    private Map<String, Item> parseItem() throws Exception {
        Map<String, Item> items = new HashMap<String, Item>();

        for (Package pk : packages) {
            Items items2 = pk.getAnnotation(Items.class);
            if (items2 == null) {
                continue;
            }
            Item[] is = items2.items();
            for (Item i : is) {
                items.put(i.value(), i);
            }
        }
        return items;
    }


    /**
     * url -> [ path ]
     *
     * @return
     * @throws Exception
     */
    private Map<String, Map<String, Path>> parsePath() throws Exception,
            IllegalArgumentException {
        Map<String, Map<String, Path>> paths = new HashMap<String, Map<String, Path>>();
        List<Class<?>> clazzs = ReflectUtils.newScanClazzs(packageToScan); // 扫描包以获取包中的类
        for (Class<?> clazz : clazzs) {
            APIs apis = clazz.getAnnotation(APIs.class);
            RequestMapping classUrl = clazz.getAnnotation(RequestMapping.class);

            if (apis == null || apis.hide()) {
                continue;
            }
            List<Method> apiMethods = scanAPIMethod(clazz);
            for (Method method : apiMethods) {
                API methodApi = method.getAnnotation(API.class);
                //2019-4-17 08:20:27 增加了扫描元注解的功能 因为 现在spring boot 的注解不只requestMappting了 还有 getMapping postMapping等
                RequestMapping methodUrl = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

                if (methodUrl == null) {
                    continue;
                }
                APIResponse response = method.getAnnotation(APIResponse.class);

                if (methodApi.hide()) {
                    continue;
                }
                boolean isMultipart = hasMultipart(methodApi);
                String url;
                if ("".equals(methodUrl.value())) {
                    url = classUrl.value()[0] + suffix;
                } else {
                    if(methodUrl.value()!=null && methodUrl.value().length>0){
                        url = classUrl.value()[0] + "/" + methodUrl.value()[0] + suffix;
                    }else{
                        url = classUrl.value()[0] + suffix;
                    }

                }


                Map<String, Path> path = paths.get(url); // get/psot/put/delete
                if (path == null) {
                    path = new HashMap<String, Path>();
                    paths.put(url, path);
                }

                String methodType = null;
                //Path p = path.get(methodApi.method());
                if (methodUrl.method() != null && methodUrl.method().length > 0) {
                    methodType = methodUrl.method()[0].toString().toLowerCase();
                } else {
                    methodType = "post";
                    System.out.println(classUrl.value()[0] + "下的" + methodUrl.toString() + "缺失method type");
                }

                Path p = path.get(methodType);

                //p.setBelongTo(apis.description());
                if (p == null) {
                    p = new Path();
                    String postGetName = methodUrl.method().toString();
                    //	if(postGetName)
                    path.put(methodType, p);
                }
                if (StringUtil.isNotBlank(methodApi.description())) {
                    p.setDescription(methodApi.description());
                } else {
                    p.setDescription(methodApi.summary());
                }
                if (StringUtil.isNotBlank(methodApi.operationId())) {
                    p.setOperationId(methodApi.operationId());
                } else { // 未设置operationId，
                    p.setOperationId(method.getName());
                }
                List<String> tags = Arrays.asList(methodApi.tags());
                if (methodApi.tags().length == 0) {
                    String ns = classUrl.value()[0] + apis.description();//apis.value();
                    if (ns.startsWith("/")) {
                        ns = ns.substring(1);
                    }
                    tags = Arrays.asList(ns);
                }
                p.setTags(tags);
                p.setSummary(methodApi.summary());
                if (isMultipart) { // multipart/form-data
                    p.setConsumes(Arrays.asList("multipart/form-data"));
                } else {
                    p.setConsumes(Arrays.asList(methodApi.consumes()));
                }
                p.setProduces(Arrays.asList(methodApi.produces()));
                p.setDeprecated(methodApi.deprecated());
                List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>(); // 请求参数
                /** 解析参数，优先使用schema */
                for (Param requestParamAttrs : methodApi.parameters()) {
                    Map<String, Object> parameter = new HashMap<String, Object>();
                    if (requestParamAttrs.schema() != null && !requestParamAttrs.schema().trim().equals("")) { // 处理复杂类型的参数
                        if (isMultipart) { // 当请求的Content-Type为multipart/form-data将忽略复杂类型的参数
                            throw new IllegalArgumentException(
                                    "请求的Content-Type为multipart/form-data，将忽略复杂类型的请求参数[ "
                                            + requestParamAttrs.schema() + " ]");
                        }
                        parameter.put("in", "body");
                        parameter.put("name", "body");
                        Map<String, Object> $ = new HashMap<String, Object>();
                        $.put("$ref", "#/definitions/" + requestParamAttrs.schema());
                        parameter.put("schema", $);
                    } else { // 简单类型的参数
                        String requestParamType, requestParamFormat;
                        if (requestParamAttrs.dataType() != DataType.UNKNOWN) { // since 1.2.2
                            requestParamType = requestParamAttrs.dataType().type();
                            requestParamFormat = requestParamAttrs.dataType().format();
                        } else {
                            requestParamType = requestParamAttrs.type();
                            requestParamFormat = requestParamAttrs.format();
                        }
                        if (isMultipart && !"path".equals(requestParamAttrs.in()) && !"header".equals(requestParamAttrs.in())) { // 包含文件上传
                            parameter.put("in", "formData");
                            parameter.put("type", requestParamType);
                        } else { // 不包含文件上传
                            String in = requestParamAttrs.in();
                            if (StringUtil.isBlank(in)) {
                                if ("post".equalsIgnoreCase(methodType)) {
                                    in = "formData";
                                } else {
                                    in = "query";
                                }
                            }
                            parameter.put("in", in);
                            parameter.put("type", requestParamType);
                            if (StringUtil.isNotBlank(requestParamFormat)) {
                                parameter.put("format", requestParamFormat);
                            }
                        }
                        parameter.put("name", requestParamAttrs.name());
                        parameter.put("description", requestParamAttrs.description());
                        parameter.put("required", requestParamAttrs.required());
                        if (requestParamAttrs.items() != null && !requestParamAttrs.items().trim().equals("")) {
                            if (!requestParamType.equals("array")) {
                                throw new IllegalArgumentException("请求参数 [ " + requestParamAttrs.name() + " ]存在可选值(items)的时候，请求参数类型(type)的值只能为array");
                            }
                            Item item = items.get(requestParamAttrs.items().trim());
                            if (item != null) { // 可选值

                                Map<String, Object> i = new HashMap<String, Object>();
                                i.put("type", item.type());
                                i.put("default", item.defaultValue());
                                if (item.type().equals("string")) { // string
                                    i.put("enum", item.optionalValue());
                                } else if (item.type().equals("boolean")) { // boolean
                                    List<Boolean> bs = new ArrayList<Boolean>();
                                    for (String v : item.optionalValue()) {
                                        bs.add(Boolean.parseBoolean(v));
                                    }
                                    i.put("enum", bs);
                                } else if (item.type().equals("integer")) { // integer
                                    List<Integer> is = new ArrayList<Integer>();
                                    for (String v : item.optionalValue()) {
                                        is.add(Integer.parseInt(v));
                                    }
                                    i.put("enum", is);
                                } else { // double
                                    List<Double> ds = new ArrayList<Double>();
                                    for (String v : item.optionalValue()) {
                                        ds.add(Double.parseDouble(v));
                                    }
                                    i.put("enum", ds);
                                }
                                parameter.put("items", i);
                            }
                        }
                    }
                    parameters.add(parameter);
                }
                p.setParameters(parameters);
                if (response != null) {
                    p.setResponse(response.value());
                }
            }
        }
        return paths;
    }

    /**
     * 判断接口的请求Content-Type是否为multipart/form-data。
     *
     * @param service
     * @return
     */
    private boolean hasMultipart(API service) {
        for (String consume : service.consumes()) {
            if ("multipart/form-data".equals(consume)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析全部Tag。
     *
     * @return 全部Tag。
     * @throws Exception
     */
    private List<Tag> parseTag() throws Exception {
        Set<Tag> tags = new HashSet<Tag>();

		/* since1.2.2 先扫描被@APITag标注了的类 */
        for (APITag apiTag : scanAPIsAnnotations()) {
            Tag tag = new Tag();
            tag.setName(apiTag.value());
            tag.setDescription(apiTag.description());
            if (StringUtil.isNotBlank(apiTag.description()) || !tags.contains(tag)) {
                tags.add(tag);
            }
        }
		
		/* 扫描package-info上面的@APITags */
        for (Package pk : packages) {
            APITags apiTags = pk.getAnnotation(APITags.class);
            if (apiTags == null) {
                continue;
            }

            APITag[] ts = apiTags.tags();
            for (APITag t : ts) {
                Tag tag = new Tag();
                tag.setName(t.value());
                tag.setDescription(t.description());
                tags.add(tag);
            }
        }
        return new ArrayList(tags);
    }

    /**
     * 解析全部definition。
     *
     * @return 全部definition
     * @throws Exception
     */
    private Map<String, Object> parseDefinition() throws Exception {
        Map<String, Object> definitions = new HashMap<String, Object>();

        for (Package pk : packages) {
            APISchemas apiSchemas = pk
                    .getAnnotation(APISchemas.class);
            if (apiSchemas == null) {
                continue;
            }
            APISchema[] schemas = apiSchemas.schemas();
            for (APISchema schema : schemas) {
                Map<String, Object> definition = new HashMap<String, Object>();
                definition.put("type", schema.type());
                List<String> required = new ArrayList<String>();
                definition.put("required", required);
                APISchemaPropertie[] props = schema.properties();
                Map<String, Map<String, Object>> properties = new HashMap<String, Map<String, Object>>();
                for (APISchemaPropertie prop : props) {
                    Map<String, Object> propertie = new HashMap<String, Object>();
                    definition.put("properties", properties);

                    propertie.put("type", prop.type());
                    propertie.put("format", prop.format());
                    propertie.put("description", prop.description());

                    if (prop.required()) { // 为必须参数
                        required.add(prop.value());
                    }

                    if (prop.optionalValue().length > 0) { // 可选值
                        if (prop.type().equals("string")) { // string
                            propertie.put("enum", prop.optionalValue());
                        } else if (prop.type().equals("boolean")) { // boolean
                            List<Boolean> bs = new ArrayList<Boolean>();
                            for (String v : prop.optionalValue()) {
                                bs.add(Boolean.parseBoolean(v));
                            }
                            propertie.put("enum", bs);
                        } else if (prop.type().equals("integer")) { // integer
                            List<Integer> is = new ArrayList<Integer>();
                            for (String v : prop.optionalValue()) {
                                is.add(Integer.parseInt(v));
                            }
                            propertie.put("enum", is);
                        } else { // double
                            List<Double> ds = new ArrayList<Double>();
                            for (String v : prop.optionalValue()) {
                                ds.add(Double.parseDouble(v));
                            }
                            propertie.put("enum", ds);
                        }
                    }
                    properties.put(prop.value(), propertie);
                }
                definitions.put(schema.value(), definition); // 添加新的definition
            }
        }
        return definitions;
    }

    /**
     * 扫描所有用注解{@link API}修饰了的方法。
     *
     * @return 所有用注解{@link API}修饰了的方法
     * @throws Exception
     */
    private List<Method> scanAPIMethod(Class<?> clazz) throws Exception {
        List<Method> api = new ArrayList<Method>();
        APIs apis = clazz.getAnnotation(APIs.class);
        if (apis != null) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                API service = method.getAnnotation(API.class);
                if (service != null) {
                    api.add(method);
                }
            }
        }
        return api;
    }

    /*
     * @since 1.2.2
     */
    private List<APITag> scanAPIsAnnotations() throws Exception {
        List<APITag> apiTags = new ArrayList<APITag>();
        List<Class<?>> clazzs = ReflectUtils.scanClazzs(packageToScan, true);
        for (Class<?> clazz : clazzs) {
            APITag annotation = clazz.getAnnotation(APITag.class);
            if (annotation != null) {
                apiTags.add(annotation);
            }
        }
        return apiTags;
    }

    public void getResult() {
        //

        String packageSearchPath = "classpath*:com/dozenx/web/**/*.class";

    }

}
