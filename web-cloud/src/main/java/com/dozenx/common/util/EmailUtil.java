package com.dozenx.common.util;


import com.dozenx.common.mail.MailSenderInfo;
import com.dozenx.common.mail.SimpleMailSender;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

/**
 * Created by dozen.zhang on 2016/5/13.
 */
public class EmailUtil {
    public static void send(String email, String title, String content) throws Exception {
        if (StringUtil.isNotBlank(email)) {
            // 发送激活邮件
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost(PropertiesUtil.get("mail.smtp.host"));
            mailInfo.setMailServerPort(PropertiesUtil.get("mail.smtp.port"));
            mailInfo.setValidate(true);
            mailInfo.setUserName(PropertiesUtil.get("mail.username"));
            mailInfo.setPassword(PropertiesUtil.get("mail.pwd"));// 您的邮箱密码
            mailInfo.setFromAddress(PropertiesUtil.get("mail.from"));
            mailInfo.setToAddress(email);
            mailInfo.setSubject(title);
            //mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active.htm?activeid="
            //	+ active.getActiveid() + "</a>");
            mailInfo.setContent(content);
            // 这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            // sms.sendTextMail(mailInfo);//发送文体格式
            try {
                sms.sendHtmlMail(mailInfo);// 发送html格式
            } catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }
        }

    }

    public static void sendMail2(String receive, String subject, String msg, String filename) throws Exception {
        if (StringUtil.isNotBlank(receive)) {

            // 发送激活邮件
            MailSenderInfo mailInfo = new MailSenderInfo();

            mailInfo.setMailServerHost(PropertiesUtil.get("mail.smtp.host"));
            mailInfo.setMailServerPort(PropertiesUtil.get("mail.smtp.port"));
            mailInfo.setValidate(true);
            mailInfo.setUserName(PropertiesUtil.get("mail.username"));
            mailInfo.setPassword(PropertiesUtil.get("mail.pwd"));// 您的邮箱密码
            mailInfo.setFromAddress(PropertiesUtil.get("mail.from"));
            mailInfo.setToAddress(receive);
            mailInfo.setSubject(subject);
            mailInfo.setAttachFileNames(new String[]{filename});
            //mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active.htm?activeid="
            //	+ active.getActiveid() + "</a>");
            mailInfo.setContent(msg);
            // 这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            // sms.sendTextMail(mailInfo);//发送文体格式
            try {
                sms.sendHtmlMail(mailInfo);// 发送html格式
            } catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }
        }

    }

    /**
     * 发送带附件的邮件
     *
     * @param receive  收件人
     * @param subject  邮件主题
     * @param msg      邮件内容
     * @param fileAbsolutePathAndName 附件地址
     * @return
     * @throws GeneralSecurityException
     */
    public static boolean sendMail(String receive, String subject, String msg, String fileAbsolutePathAndName)
            throws Exception {

        if (StringUtil.isBlank(receive)) {
            return false;
        }
        final String from = PropertiesUtil.get("mail.from");//"likegadfly@163.com";
        // 发件人电子邮箱密码
        final String pass = PropertiesUtil.get("mail.pwd");//"wangyi216568";
        // 获取系统属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", PropertiesUtil.get("mail.transport.protocol"));// 设置传输协议
        properties.put("mail.smtp.host", PropertiesUtil.get("mail.smtp.host"));// 设置发信邮箱的smtp地址
        properties.setProperty("mail.smtp.auth", PropertiesUtil.get("mail.smtp.auth")); // 验证
        properties.put("mail.debug", PropertiesUtil.get("mail.debug"));//便于调试
        // 设置邮件服务器

        properties.put("mail.smtp.port", Integer.valueOf(PropertiesUtil.get("mail.smtp.port")));
        properties.put("mail.smtp.auth", PropertiesUtil.get("mail.smtp.auth"));


        properties.put("mail.smtp.ssl.enable", "false");

//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        properties.put("mail.smtp.ssl.socketFactory", sf);


//        sf.setTrustAllHosts(true);
        System.setProperty("mail.mime.splitlongparameters", "false");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() { // qq邮箱服务器账户、第三方登录授权码
                return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receive));

            // Set Subject: 主题文字
            message.setSubject(subject);

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();

            // 消息
            messageBodyPart.setText(msg);

            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 附件部分
            messageBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(fileAbsolutePathAndName);
            messageBodyPart.setDataHandler(new DataHandler(source));

            // messageBodyPart.setFileName(filename);
            // 处理附件名称中文（附带文件路径）乱码问题
            String fileName = fileAbsolutePathAndName.substring(fileAbsolutePathAndName.lastIndexOf(File.separator));
            messageBodyPart.setFileName(MimeUtility.encodeText(fileName));
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart);

            // 发送消息
            Transport.send(message);
            // System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean sendMail(List<String> receives, String subject, String msg, String filePath, String filename)
            throws Exception {
        if (receives == null || receives.size() == 0) {
            return false;
        }
        // 发件人电子邮箱
        final String from = PropertiesUtil.get("mail.from");//"likegadfly@163.com";
        // 发件人电子邮箱密码
        final String pass = PropertiesUtil.get("mail.pwd");//"wangyi216568";
        // 指定发送邮件的主机为 smtp.qq.com
        // String host = "smtp.163.com"; // 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", PropertiesUtil.get("mail.transport.protocol"));// 设置传输协议
        properties.put("mail.smtp.host", PropertiesUtil.get("mail.smtp.host"));// 设置发信邮箱的smtp地址
        properties.setProperty("mail.smtp.auth", PropertiesUtil.get("mail.smtp.auth")); // 验证
        properties.put("mail.debug", PropertiesUtil.get("mail.debug"));//便于调试
        // 设置邮件服务器

        properties.put("mail.smtp.port", Integer.valueOf(PropertiesUtil.get("mail.smtp.port")));
        properties.put("mail.smtp.auth", PropertiesUtil.get("mail.smtp.auth"));
//        properties.put("mail.smtp.ssl.enable", "true");
//
//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        properties.put("mail.smtp.ssl.socketFactory", sf);


//        sf.setTrustAllHosts(true);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() { // qq邮箱服务器账户、第三方登录授权码
                return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段


            // 设置收件人
            if (receives != null && receives.size() > 1) {
                String toListStr = getMailList(receives);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toListStr));
            } else {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receives.get(0)));
            }


            // Set Subject: 主题文字
            message.setSubject(MimeUtility.encodeText(subject, MimeUtility.mimeCharset("utf-8"), null));

            // 创建消息部分
            BodyPart textMimeBodyPart = new MimeBodyPart();

            // 消息
            textMimeBodyPart.setText(MimeUtility.encodeText(msg, MimeUtility.mimeCharset("utf-8"), null));


            /**MimeMessage
             *          ->MimeMultipart
             *                  ->MimeBodyPart   textMimeBodyPart
             *                                  ->text 文本消息
             *                  ->MimeBodyPart   fileMimeBodyPart
             *                                  ->FileName ==>文件名
             *                                  ->DataHandler ==> file
             */
            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(textMimeBodyPart);

            // 附件部分
            BodyPart fileMimeBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(filePath);
            fileMimeBodyPart.setDataHandler(new DataHandler(source));

            // messageBodyPart.setFileName(filename);
            // 处理附件名称中文（附带文件路径）乱码问题
            fileMimeBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(fileMimeBodyPart);

            // 发送完整消息
            message.setContent(multipart);

            // 发送消息
            Transport.send(message);
            // System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
       * 初始化方法
       */
//    public JavaMailWithAttachment(boolean debug) {
//        InputStream in = JavaMailWithAttachment.class.getResourceAsStream("MailServer.properties");
//        try {
//            properties.load(in);
//            this.mailHost = properties.getProperty("mail.smtp.host");
//            this.sender_username = properties.getProperty("mail.sender.username");
//            this.sender_password = properties.getProperty("mail.sender.password");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        session = Session.getInstance(properties);
//        session.setDebug(debug);// 开启后有调试信息
//        message = new MimeMessage(session);
//    }

//    /**
//     * 发送邮件
//     *
//     * @param subject
//     *            邮件主题
//     * @param sendHtml
//     *            邮件内容
//     * @param receiveUser
//     *            收件人地址
//     * @param attachment
//     *            附件
//     */
//    public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, File attachment) {
//        try {
//            // 发件人
//            InternetAddress from = new InternetAddress(sender_username);
//            message.setFrom(from);
//
//            // 收件人
//            InternetAddress to = new InternetAddress(receiveUser);
//            message.setRecipient(Message.RecipientType.TO, to);
//
//            // 邮件主题
//            message.setSubject(subject);
//
//            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
//            Multipart multipart = new MimeMultipart();
//
//            // 添加邮件正文
//            BodyPart contentPart = new MimeBodyPart();
//            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
//            multipart.addBodyPart(contentPart);
//
//            // 添加附件的内容
//            if (attachment != null) {
//                BodyPart attachmentBodyPart = new MimeBodyPart();
//                DataSource source = new FileDataSource(attachment);
//                attachmentBodyPart.setDataHandler(new DataHandler(source));
//
//                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
//                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
//                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
//
//                //MimeUtility.encodeWord可以避免文件名乱码
//                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
//                multipart.addBodyPart(attachmentBodyPart);
//            }
//
//            // 将multipart对象放到message中
//            message.setContent(multipart);
//            // 保存邮件
//            message.saveChanges();
//
//            transport = session.getTransport("smtp");
//            // smtp验证，就是你用来发邮件的邮箱用户名密码
//            transport.connect(mailHost, sender_username, sender_password);
//            // 发送
//            transport.sendMessage(message, message.getAllRecipients());
//
//            System.out.println("send success!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (transport != null) {
//                try {
//                    transport.close();
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public static void main(String args[]) {
//        File file = new File("G:\\5566.xls");
//        System.out.println(file.getName());
        /*try {
            //  EmailUtil.send("371452875@qq.com","test");
            //EmailUtil.sendMail2("13958173965@189.cn", "今天的会议晚点开", "今天的会议要晚点开了,因为今天我请假了", "G:\\5566.zip");
            List<String > receives =new ArrayList<>();
            receives.add("13958173965@189.cn");
            receives.add("likegadfly@163.com");
            receives.add("371452875@qq.com");
            receives.add("zhangxiao94@126.com");

            EmailUtil.sendMail(receives, "2018年10月23日20:14:20","2018年10月23日20:14:38","G:\\5566.xls","5566.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
