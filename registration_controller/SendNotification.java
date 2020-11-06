package registration_controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendNotification {

	public static void sendMail(String courseIndex) {

		final String username = "mystarsntu@gmail.com"; // to be added
		final String password = "mystars123!"; // to be added

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mystarsntu@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("quynhnga001@e.ntu.edu.sg")); // to be added an email addr
			message.setSubject("Add Course");
			message.setText("Dear Student,"
				+ "\n\nYour registration for the Course Index " + courseIndex + " was successfully!" 
				+ "\n\nBest regards,\nMyStars");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}