package pfs2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;  
import javax.mail.*;
import javax.mail.search.FlagTerm;

import io.qameta.allure.internal.shadowed.jackson.databind.annotation.JsonAppend.Prop;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class mail {


	private static final class AuthenticatorExtension extends javax.mail.Authenticator {
		protected PasswordAuthentication getPasswordAuthentication() {
			String password = "Ken42$42";
			return new PasswordAuthentication("anandTest2022@gmail.com", "wxpjyqglxdjonabl");
		}
	}

	protected static final char[] PasswordAuthentication = null;

	public static void check(String host, String storeType, String userName, String password) {
		try {

			// create properties
			Properties properties = new Properties();
			
			properties.setProperty("mail.store.protocol", "pop3s");
			properties.setProperty("mail.pop3s.host", "pop.gmail.com");
			properties.setProperty("mail.pop3s.port", String.valueOf("995"));
			properties.setProperty("mail.pop3s.auth", "true");
			properties.setProperty("mail.pop3s.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory" ); 
			properties.setProperty("mail.pop3s.ssl.trust", "pop.gmail.com");
			System.setProperty("mail.pop3s.ssl.protocols", "TLSv1.3");
			
    //   Session emailSession = Session.getDefaultInstance(properties);
	Session emailSession = Session.getInstance(properties, new AuthenticatorExtension());
	  System.out.println(emailSession);
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("pop3s");
	  System.out.println("Store is "+store);
	//   System.out.println(user);
	//   System.out.println(password);
      store.connect("pop.gmail.com", 995, userName, "wxpjyqglxdjonabl");
    //   store.connect();
	  
	  

			// create the inbox object and open it
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				message.setFlag(Flag.SEEN, true);
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + message.getContent().toString());

			}

			inbox.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String host = "imap.gmail.com";
		String mailStoreType = "imap";
		String username = "anandTest2022@gmail.com";
		// String password = "wxpjyqglxdjonabl";
		String password = "Ken42$42";

		check(host, mailStoreType, username, password);

	}
}