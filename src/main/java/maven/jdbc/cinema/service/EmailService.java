package maven.jdbc.cinema.service;

import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService
{
    private static final String emailAddress = "**********";
    private static final String emailPassword = "******";

    private void prepareEmailMessage(MimeMessage message, String to, String title, String html)
    {
        try
        {
            message.setContent(html, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(emailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(title);

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.SERVICE_EMAIL, "PREPARE EMAIL MESSAGE: " + e.getMessage());
        }
    }

    private Session createSession()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        return Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });
    }


    public void sendMessageAsHtml(String to, String title, String html) {
        try
        {
            System.out.println("SENDING EMAIL TO: " + to);
            Session session = createSession();
            MimeMessage mimeMessage = new MimeMessage(session);
            prepareEmailMessage(mimeMessage, to, title, html);
            Transport.send(mimeMessage);
            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.SERVICE_EMAIL, "SEND MESSAGE AS HTML EXCEPTION" + e.getMessage());
        }
    }





}
