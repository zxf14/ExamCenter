package com.nju.coursework.saas.util;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by guhan on 17/11/8.
 */
@Service
public class MailMaster {
    public static String myMailAccount = "13851497696@163.com";

    public static String myMailPassword = "gh100982,,";

    public static String myEmailSMTPHost = "smtp.163.com";

    private Properties props;

    private Session session;

    @PostConstruct
    public void init() {
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        session = Session.getInstance(props);
        session.setDebug(true);
    }

    public void sendForValidation(String userMail, String verifyCode) {
        try {
            List<String> receiverList = new ArrayList<>();
            receiverList.add(userMail);
            MimeMessage message = createMessage(myMailAccount, receiverList,
                    "在线考试平台邮箱验证", "验证码为" + verifyCode);
            Transport transport = session.getTransport();
            transport.connect(myMailAccount, myMailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendForExam(String userMail, String key, String examTitle) {
        try {
            List<String> receiverList = new ArrayList<>();
            receiverList.add(userMail);
            MimeMessage message = createMessage(myMailAccount, receiverList,
                    "考试密钥", examTitle + "的考试密钥为: " + key);
            Transport transport = session.getTransport();
            transport.connect(myMailAccount, myMailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendForResult(String userMail, String examTitle, int score) {
        try {
            MimeMessage message = createMessage(myMailAccount, Arrays.asList(userMail), "考试结果", "您参加的考试: " +
                    examTitle + "分数为： " + score);
            Transport transport = session.getTransport();
            transport.connect(myMailAccount, myMailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private MimeMessage createMessage(String sender, List<String> receiver, String subject, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);
        List<InternetAddress> receiverList = new ArrayList<>();
        Address[] recvArray = new Address[receiver.size()];
        int i = 0;
        for (String ritem : receiver) {
            receiverList.add(new InternetAddress(ritem, "学生", "UTF-8"));
            recvArray[i] = new InternetAddress(ritem, "学生", "UTF-8");
            i++;
        }


        message.setFrom(new InternetAddress(sender, "软件学院考试中心", "UTF-8"));
        message.setRecipients(MimeMessage.RecipientType.TO, recvArray);
        message.setSubject(subject, "UTF-8");
        message.setContent(content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
