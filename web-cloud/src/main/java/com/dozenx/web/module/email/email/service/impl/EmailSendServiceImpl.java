/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.email.email.service.impl;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.mail.MailSenderInfo;
import com.dozenx.common.mail.SimpleMailSender;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.email.email.bean.Email;
import com.dozenx.web.module.email.email.dao.EmailMapper;
import com.dozenx.web.module.email.email.service.EmailSendService;
import com.dozenx.web.module.email.email.service.EmailService;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.ResultUtil;
import com.sun.mail.imap.protocol.MailboxInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("emailSendService")
public class EmailSendServiceImpl extends BaseService implements EmailSendService {
    private static final Logger logger = LoggerFactory
            .getLogger(EmailService.class);
    @Resource
    private EmailMapper emailMapper;

    @Override
    public void sendEmail(String to, String title, String content) {
        MailSenderInfo mailSenderInfo =getMailInfo();
        SimpleMailSender simpleMailSender =new SimpleMailSender() ;
        mailSenderInfo.setSubject(title);
        mailSenderInfo.setToAddress(to);
        mailSenderInfo.setContent(content);
        try {
            simpleMailSender.sendHtmlMail(mailSenderInfo);
        } catch (MessagingException e) {
            logger.error("",e);
            throw new BizException(30105197,"发送邮件报错");
        } catch (UnsupportedEncodingException e) {
          logger.error("",e);
            throw new BizException(30105197,"发送邮件报错");
        }

    }

    @Override
    public void sendEmail(String to, String title, String content,String file) {
        MailSenderInfo mailSenderInfo =getMailInfo();
        mailSenderInfo.setToAddress(to);
        SimpleMailSender simpleMailSender =new SimpleMailSender() ;
        mailSenderInfo.setSubject(title);
        if(StringUtil.isNotBlank(file)) {
            mailSenderInfo.setAttachFileNames(new String[]{file});
        }
        mailSenderInfo.setContent(content);
        try {
            simpleMailSender.sendHtmlMail(mailSenderInfo);
        } catch (MessagingException e) {
            logger.error("",e);
            throw new BizException(30105197,"发送邮件报错");
        } catch (UnsupportedEncodingException e) {
            logger.error("",e);
            throw new BizException(30105197,"发送邮件报错");
        }

    }

    @Override
    public void sendEmail(List<String> toList, String title, String content,String file) {
        MailSenderInfo mailSenderInfo = getMailInfo();
        mailSenderInfo.setToAddressList(toList);
        SimpleMailSender simpleMailSender =new SimpleMailSender() ;
        mailSenderInfo.setSubject(title);
        if(StringUtil.isNotBlank(file)) {
            mailSenderInfo.setAttachFileNames(new String[]{file});
        }
        mailSenderInfo.setContent(content);
        try {
            simpleMailSender.sendHtmlMail(mailSenderInfo);
        } catch (MessagingException e) {
            logger.error("",e);
            throw new BizException(30105197,"发送邮件报错");
        } catch (UnsupportedEncodingException e) {
            logger.error("",e);
            throw new BizException(30105197,"发送邮件报错");
        }

    }

    public MailSenderInfo getMailInfo(){
        return      new MailSenderInfo(ConfigUtil.getConfig("email.host"),
                Integer.valueOf(ConfigUtil.getConfig("email.port")),
                ConfigUtil.getConfig("email.username"),
                ConfigUtil.getConfig("email.pwd"),
                ConfigUtil.getConfig("email.use"));
    }
}
