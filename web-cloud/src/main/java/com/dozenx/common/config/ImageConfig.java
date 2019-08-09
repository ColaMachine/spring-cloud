
package com.dozenx.common.config;

/**
 * @author dozen.zhang
 *
 */
public class ImageConfig {
    
    /** 
     *是否允许图片集中存储在图片服务器上
     */
    private boolean serverEnable;        //
    /** 
     *图片服务器ip
     */
    private  String serverIp;            //
    /** 
     *图片服务器主目录 
     */
    private  String serverDir;            //               
    /** 
     *图片服务器ssh username
     */
    private  String serverUser;        //
    /** 
     *密码
     */
    private  String serverPwd;            //
    
    /** 
     *图片主要目录 用在共享文件形式的目录
     */
    private String mainPath;
    /** 
     *海报目录
     */
    private String posterDir;            //
    /** 
     *二维码目录
     */
    private String qrcodeDir;            //
    /**
     * 二维码目录
     */
    private String vcodeDir;            //
    public String getVcodeDir() {
        return vcodeDir;
    }

    public void setVcodeDir(String vcodeDir) {
        this.vcodeDir = vcodeDir;
    }

    /** 
     *图片类型
     */
    private String  type;            //
    
    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /** 
     *图片最大容量
     */
    private int maxSize;            //
    
    /** 
     *海报模板目录
     */
    private String posterTemplate;        //
    /** 
     *海报打包目录
     */
    private String posterZipDir;        //
    
    /** 
     *图片服务器url
     */
    private String serverUrl;            //

    public boolean isServerEnable() {
        return serverEnable;
    }

    public void setServerEnable(boolean serverEnable) {
        this.serverEnable = serverEnable;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerDir() {
        return serverDir;
    }

    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }

    public String getServerUser() {
        return serverUser;
    }

    public void setServerUser(String serverUser) {
        this.serverUser = serverUser;
    }

    public String getServerPwd() {
        return serverPwd;
    }

    public void setServerPwd(String serverPwd) {
        this.serverPwd = serverPwd;
    }

    public String getPosterDir() {
        return posterDir;
    }

    public void setPosterDir(String posterDir) {
        this.posterDir = posterDir;
    }

    public String getQrcodeDir() {
        return qrcodeDir;
    }

    public void setQrcodeDir(String qrcodeDir) {
        this.qrcodeDir = qrcodeDir;
    }

    public String getPosterTemplate() {
        return posterTemplate;
    }

    public void setPosterTemplate(String posterTemplate) {
        this.posterTemplate = posterTemplate;
    }

    public String getPosterZipDir() {
        return posterZipDir;
    }

    public void setPosterZipDir(String posterZipDir) {
        this.posterZipDir = posterZipDir;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
    
}
