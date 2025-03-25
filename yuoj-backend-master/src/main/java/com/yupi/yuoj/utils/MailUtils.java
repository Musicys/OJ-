package com.yupi.yuoj.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;



/*
 * 邮件发送工具类
 *
 * */

public class MailUtils {
    //生成验证码
    public static int generateVerificationCode() {
        Random random = new Random();
        // 生成一个100000到999999之间的随机数
        int code = 100000 + random.nextInt(900000);
        return code;
    }


    //发送第二封验证码邮件

    public static void sendMail(String to, int vcode) throws Exception {

        //设置发送邮件的主机  smtp.qq.com

        String host = "smtp.qq.com";

        //1.创建连接对象，连接到邮箱服务器

        Properties props = System.getProperties();

        //Properties 用来设置服务器地址，主机名 。。 可以省略

        //设置邮件服务器

        props.setProperty("mail.smtp.host", host);

        props.put("mail.smtp.auth", "true");

        //SSL加密

        MailSSLSocketFactory sf = new MailSSLSocketFactory();

        sf.setTrustAllHosts(true);

        props.put("mail.smtp.ssl.enable", "true");

        props.put("mail.smtp.ssl.socketFactory", sf);

        //props：用来设置服务器地址，主机名；Authenticator：认证信息

        Session session = Session.getDefaultInstance(props, new Authenticator() {

            @Override

            //通过密码认证信息

            protected PasswordAuthentication getPasswordAuthentication() {

                //new PasswordAuthentication(用户名, password);

                //这个用户名密码就可以登录到邮箱服务器了,用它给别人发送邮件
                    //**QQ邮箱
                String MailUsername = "*******@qq.com";
                    //**QQ密钥
                String MailPassword="key";


                return new PasswordAuthentication(MailUsername, MailPassword);

            }

        });

        try {

            Message message = new MimeMessage(session);

            //2.1设置发件人：
            //发件人QQ
            message.setFrom(new InternetAddress("********@qq.com"));

            //2.2设置收件人 这个TO就是收件人

            message.setRecipient(RecipientType.TO, new InternetAddress(to));




            message.setSubject("来自OJ平台注册验证码");

            //2.4设置邮件的正文 第一个参数是邮件的正文内容 第二个参数是：是文本还是html的连接

            message.setContent("<h1>请接收你的验证码：</h1><h3>你的验证码是：<strong><b>" + vcode + "</b></strong>，请妥善保管好你的验证码！</h3>", "text/html;charset=UTF-8");

            //3.发送一封激活邮件

            Transport.send(message);


        } catch (MessagingException mex) {

            mex.printStackTrace();

        }

    }


}