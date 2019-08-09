/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.email.email.service;

import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.email.email.bean.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public interface EmailSendService {
    static final Logger logger = LoggerFactory
            .getLogger(EmailSendService.class);

    /**
     * 发送普通邮件
     * @param to
     * @param title
     * @param content
     */
    public void sendEmail(String to, String title, String content);

    /**
     * 发送带文件邮件
     * @param to
     * @param title
     * @param content
     * @param file
     */
    public void sendEmail(String to, String title, String content, String file);


    /**
     * 发送带文件邮件
     * @param to
     * @param title
     * @param content
     * @param file
     */
    public void sendEmail(List<String> to, String title, String content, String file);
}
