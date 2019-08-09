package com.dozenx.common.mail;


import com.dozenx.common.mail.MailSenderInfo;
import com.dozenx.common.mail.MyAuthenticator;
import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 简单邮件（不带附件的邮件）发送器
 * http://www.bt285.cn BT下载
 */
public class SimpleMailSender {
    private final static Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

    /**
     * 以文本格式发送邮件
     *
     * @param mailInfo 待发送的邮件的信息
     */
    public boolean sendTextMail(com.dozenx.common.mail.MailSenderInfo mailInfo) {
        // 判断是否需要身份认证
        com.dozenx.common.mail.MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new com.dozenx.common.mail.MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from;
            if (StringUtil.isNotBlank(mailInfo.getUserName())) {
                from = new InternetAddress(mailInfo.getFromAddress(), MimeUtility.encodeText(mailInfo.getUserName()));
            } else {
                from = new InternetAddress(mailInfo.getFromAddress());

            }
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());

            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendSimpleTextMail(com.dozenx.common.mail.MailSenderInfo mailInfo) {
        // 判断是否需要身份认证
        com.dozenx.common.mail.MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from;
            if (StringUtil.isNotBlank(mailInfo.getUserName())) {
                from = new InternetAddress(mailInfo.getFromAddress(), MimeUtility.encodeText(mailInfo.getUserName()));
            } else {
                from = new InternetAddress(mailInfo.getFromAddress());

            }


            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            mailMessage.setText(mailInfo.getContent());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            Transport transport = sendMailSession.getTransport();
            transport.connect(mailInfo.getMailServerHost(), Integer.valueOf(mailInfo.getMailServerPort()), mailInfo.getUserName(), mailInfo.getPassword());//这届连接
            transport.sendMessage(mailMessage,
                    new Address[]{new InternetAddress(mailInfo.getToAddress())});
            transport.close();
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param mailInfo 待发送的邮件信息
     */
    public static boolean sendHtmlMail(MailSenderInfo mailInfo) throws MessagingException, UnsupportedEncodingException {
        System.setProperty("mail.mime.splitlongparameters","false");
        // 判断是否需要身份认证
        com.dozenx.common.mail.MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();

        //pro.put("mail.debug", "true");//便于调试
        //如果需要身份认证，则创建一个密码验证器
        //===================================
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        //另外一种写法如下 只不过我继承了MyAuthenticator 这个类而已 其实是一样的
//      Session session = Session.getDefaultInstance(properties, new Authenticator() {
//        public PasswordAuthentication getPasswordAuthentication() { // qq邮箱服务器账户、第三方登录授权码
//          return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
//        }
//      });
        //=================================
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            // 设置邮件消息的发送者
            Address from;
            if (StringUtil.isNotBlank(mailInfo.getUserName())) {
                from = new InternetAddress(mailInfo.getFromAddress(), MimeUtility.encodeText(mailInfo.getUserName()));
            } else {
                from = new InternetAddress(mailInfo.getFromAddress());

            }
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);


            //需要增加批量发送的人

            // 设置收件人
            if (mailInfo.getToAddressList() != null && mailInfo.getToAddressList().size() > 1) {
                String toListStr = getMailList(mailInfo.getToAddressList());
                mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toListStr));
            } else if(StringUtil.isNotBlank(mailInfo.getToAddress())) {
                // Message.RecipientType.TO属性表示接收者的类型为TO
                // 创建邮件的接收者地址，并设置到邮件消息中
                Address to = new InternetAddress(mailInfo.getToAddress());
                mailMessage.setRecipient(Message.RecipientType.TO, to);

            }else{
                logger.error("没有设置收件人");
            }





            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            try {
                mailMessage.setSubject(MimeUtility.encodeText(mailInfo.getSubject(), MimeUtility.mimeCharset("utf-8"), null));
            } catch (Exception e) {

                logger.error("设置邮件标题出错  set mail title error", e);
            }

            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象

            /**MimeMessage
             *          ->MimeMultipart
             *                  ->MimeBodyPart   textMimeBodyPart
             *                                  ->text 文本消息
             *                  ->MimeBodyPart   fileMimeBodyPart
             *                                  ->FileName ==>文件名
             *                                  ->DataHandler ==> file
             */

            Multipart multipart = new MimeMultipart();


            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart textMimeBodyPart = new MimeBodyPart();
            // 设置HTML内容
            textMimeBodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");

            try {
                textMimeBodyPart.setText(mailInfo.getContent());//不需要转码  不然会出现 utf-8=等多一层转码//MimeUtility.encodeText(mailInfo.getContent(), MimeUtility.mimeCharset("utf-8"), "B")
            } catch (Exception e) {//UnsupportedEncoding
                logger.error("设置邮件内容出错  set mail content error", e);
            }

            multipart.addBodyPart(textMimeBodyPart);
            // 将MiniMultipart对象设置为邮件内容


            if (mailInfo.getAttachFileNames() != null) {

                for (String fileName : mailInfo.getAttachFileNames()) {
                    //判断文件是否存在
                    File file = new File(fileName);
//                    if (file.exists()) {
//                        BodyPart fileMimeBodyPart = new MimeBodyPart();
//                        DataSource source = new FileDataSource(fileName);
//                        fileMimeBodyPart.setDataHandler(new DataHandler(source));
//                        try {
//                            String fileNameTxt = file.getName();
//                            fileMimeBodyPart.setFileName(MimeUtility.encodeWord(fileNameTxt));
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        multipart.addBodyPart(fileMimeBodyPart);
//                    }
                    if (file.exists()) {
                        BodyPart fileMimeBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(fileName);
                        fileMimeBodyPart.setDataHandler(new DataHandler(source));
                        try {
                            String fileNameTxt = file.getName();
                            fileMimeBodyPart.setFileName(MimeUtility.encodeText(file.getName(), "UTF-8", "B"));//需要制定utf-8不然会出现未命名附件  或者 文件名过长问题
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        multipart.addBodyPart(fileMimeBodyPart);
                    }
                }
            }

            mailMessage.setContent(multipart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw e;
        }

    }


    public static String getMailList(List<String> mailArray) {
        StringBuffer toList = new StringBuffer();
        int length = mailArray.size();
        if (mailArray != null && length < 2) {
            toList.append(mailArray.get(0));
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray.get(i));
                if (i != (length - 1)) {
                    toList.append(",");
                }

            }
        }
        return toList.toString();
    }
}   
