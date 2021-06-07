/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.utils;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class SendEmail implements Serializable {
    public static void sendMail(String receiver, int code, String fullName) throws MessagingException {
        Properties pro = new Properties();
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");
        String myEmail = "traanvanlongo@gmail.com";
        String myPassword = "VanLongpro14789";
        
        Session session = Session.getInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        Message message = prepareMessage(session, myEmail, receiver, code, fullName);
        if(message != null) {
            Transport.send(message);
        }
    }

    private static Message prepareMessage(Session session, String myEmail, String receiver, int code, String fullName) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("Verification Code Resource Sharing");
            String generateURL = "http://localhost:8080/ResourceSharing/DispatchServlet?action=VerifyEmailAccount&email=" + receiver + "&code=" + code + "&verifyType=link";
            String firstLine = "Dear " + fullName;
            String secondLine = "Thanks for using our Resource Sharing system, your activation code is: " + code;
            String thirdLine = "You can also click the link to verify: " + generateURL;
            String lastLine = "Visit our website: http://localhost:8080/ResourceSharing";
            message.setText(firstLine + "\n" + secondLine + "\n" + thirdLine + "\n\n" + lastLine);
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
