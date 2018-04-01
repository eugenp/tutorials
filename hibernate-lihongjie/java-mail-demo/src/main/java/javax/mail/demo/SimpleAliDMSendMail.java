package javax.mail.demo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by lihongjie on 5/30/17.
 * 测试 aliyun 免费邮件推送
 *
 * HOST： smtpdm.aliyun.com
 * PORT: 25
 */
public class SimpleAliDMSendMail {

    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final int ALIDM_SMTP_PORT = 25;

    public static void main(String[] args) throws MessagingException {
        // 配置发送邮件的环境属性
        final Properties properties = new Properties();
        // 表示SMTP发送邮件， 需要进行身份验证
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", ALIDM_SMTP_HOST);
        properties.put("mail.smtp.port", ALIDM_SMTP_PORT);
        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
        // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // props.put("mail.smtp.socketFactory.port", "465");
        // props.put("mail.smtp.port", "465");

        //阿里云配置的发送人帐号
        properties.put("mail.user", "1379574823@mail.lihongjie.win");
        //访问 SMTP服务时需要提供的密码
        properties.put("mail.password", "QWERlhj123456");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名 密码
                String userName = properties.getProperty("mail.user");
                String password = properties.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 使用环境属性和授权信息， 创建邮件会话
        Session mailSession = Session.getInstance(properties, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);

        // 设置发件人
        InternetAddress form = new InternetAddress(properties.getProperty("mail.user"));

        message.setFrom(form);
        // 设置收件人
        InternetAddress to = new InternetAddress("726676865@qq.com");
        message.setRecipients(MimeMessage.RecipientType.TO, String.valueOf(to));

        // 设置邮件标题
        message.setSubject("测试邮件");
        // 设置邮件的内容体
        message.setContent("123456/HTML邮件", "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);

    }
}
