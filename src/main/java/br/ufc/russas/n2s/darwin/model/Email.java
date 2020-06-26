package br.ufc.russas.n2s.darwin.model;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.EmailException;

import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import util.Constantes;


public class Email implements Runnable {
	
	private String from = "n2s.mensageiro@gmail.com";
	private String pass = "n2s@m@1ls3rv1c3";
	private Session session;
	private List<UsuarioBeans> to;
	private Set<UsuarioBeans> toSet;
	UsuarioBeans toUsuario;
	private String assunto;
	private String titulo;
	private String msg;
	private int TYPE_EMAIL;
	
	public Email () {
		config();
	}
	
	public Email(List<UsuarioBeans> to, String assunto, String titulo, String msg) {
		this();
		setTo(to);
		setTitulo(titulo);
		setAssunto(assunto);
		setMsg(msg);
		TYPE_EMAIL = 1;
	}
	
	public Email(Set<UsuarioBeans> to, String assunto, String titulo, String msg) {
		this();
		setToSet(to);
		setTitulo(titulo);
		setAssunto(assunto);
		setMsg(msg);
		TYPE_EMAIL = 2;
	}
	
	public Email(UsuarioBeans to, String assunto, String titulo, String msg) {
		this();
		setToUsuario(to);
		setTitulo(titulo);
		setAssunto(assunto);
		setMsg(msg);
		TYPE_EMAIL = 3;
	}
	
	
	public List<UsuarioBeans> getTo() {
		return to;
	}

	public void setTo(List<UsuarioBeans> to) {
		this.to = to;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Set<UsuarioBeans> getToSet() {
		return toSet;
	}

	public void setToSet(Set<UsuarioBeans> toSet) {
		this.toSet = toSet;
	}

	
	
	public UsuarioBeans getToUsuario() {
		return toUsuario;
	}

	public void setToUsuario(UsuarioBeans toUsuario) {
		this.toUsuario = toUsuario;
	}

	public void config() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
        session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() 
                         {
                               return new PasswordAuthentication(from, pass);
                         }
                    });

	}
	
	public void sendSimpleMail(UsuarioBeans u) throws EmailException {
		try {
			 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            Address[] toUser = InternetAddress 
                       .parse("wallisonrocha2008@gmail.com");  

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Enviando email com JavaMail");
            message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
            Transport.send(message);


       } catch (MessagingException e) {
            throw new RuntimeException(e);
       }
	}

	public void sendHtmlEmail() throws EmailException, MalformedURLException {
		try {
			if (!to.isEmpty()) { 
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from)); 
	            String emails = "";
	            for (int i = 0;i <  to.size();i++) {
	            	UsuarioBeans u = to.get(i);
	            	if (u.isRecebeEmail()) {
		            	emails += (u.getEmail());
		            	if (i + 1 != to.size()) {
		            		emails += (",");
		            	}
	            	}
	            }
	            Address[] toUser = InternetAddress
	                       .parse(emails);  
	            MimeMultipart multipart = new MimeMultipart("related");
	            BodyPart messageBodyPart = new MimeBodyPart();
	            String htmlText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
	    				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
	    				" \r\n" + 
	    				"<head>\r\n" + 
	    				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n" + 
	    				"  <title>Darwin - Sistema de Gerenciamento de Seleções</title>\r\n" + 
	    				"  <style type=\"text/css\">\r\n" + 
	    				"  body {margin: 0; padding: 0; min-width: 100%!important;}\r\n" + 
	    				"  img {height: auto;}\r\n" + 
	    				"  .content {width: 100%; max-width: 600px;}\r\n" + 
	    				"  .header {padding: 40px 30px 20px 30px;}\r\n" + 
	    				"  .innerpadding {padding: 30px 30px 30px 30px;}\r\n" + 
	    				"  .borderbottom {border-bottom: 1px solid #f2eeed;}\r\n" + 
	    				"  .subhead {font-size: 15px; color: #ffffff; font-family: sans-serif; letter-spacing: 10px;}\r\n" + 
	    				"  .h1, .h2, .bodycopy {color: #153643; font-family: sans-serif;}\r\n" + 
	    				"  .h1 {font-size: 33px; line-height: 38px; font-weight: bold;}\r\n" + 
	    				"  .h2 {padding: 0 0 15px 0; font-size: 24px; line-height: 28px; font-weight: bold;}\r\n" + 
	    				"  .bodycopy {font-size: 16px; line-height: 22px;}\r\n" + 
	    				"  .button {text-align: center; font-size: 18px; font-family: sans-serif; font-weight: bold; padding: 0 30px 0 30px;}\r\n" + 
	    				"  .button a {color: #ffffff; text-decoration: none;}\r\n" + 
	    				"  .footer {padding: 20px 30px 15px 30px;}\r\n" + 
	    				"  .footercopy {font-family: sans-serif; font-size: 14px; color: #ffffff;}\r\n" + 
	    				"  .footercopy a {color: #ffffff; text-decoration: underline;}\r\n" + 
	    				"\r\n" + 
	    				"  @media only screen and (max-width: 550px), screen and (max-device-width: 550px) {\r\n" + 
	    				"  body[yahoo] .hide {display: none!important;}\r\n" + 
	    				"  body[yahoo] .buttonwrapper {background-color: transparent!important;}\r\n" + 
	    				"  body[yahoo] .button {padding: 0px!important;}\r\n" + 
	    				"  body[yahoo] .button a {background-color: #e05443; padding: 15px 15px 13px!important;}\r\n" + 
	    				"  body[yahoo] .unsubscribe {display: block; margin-top: 20px; padding: 10px 50px; background: #2f3942; border-radius: 5px; text-decoration: none!important; font-weight: bold;}\r\n" + 
	    				"  }\r\n" + 
	    				"\r\n" + 
	    				"  /*@media only screen and (min-device-width: 601px) {\r\n" + 
	    				"    .content {width: 600px !important;}\r\n" + 
	    				"    .col425 {width: 425px!important;}\r\n" + 
	    				"    .col380 {width: 380px!important;}\r\n" + 
	    				"    }*/\r\n" + 
	    				"\r\n" + 
	    				"  </style>\r\n" + 
	    				"</head>\r\n" + 
	    				"\r\n" + 
	    				"<body yahoo bgcolor=\"#f6f8f1\">\r\n" + 
	    				"<table width=\"100%\" bgcolor=\"#f6f8f1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
	    				"<tr>\r\n" + 
	    				"  <td>\r\n" + 
	    				"    <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"        <tr>\r\n" + 
	    				"          <td>\r\n" + 
	    				"    <![endif]-->     \r\n" + 
	    				"    <table bgcolor=\"#ffffff\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td bgcolor=\"#c7d8a7\" class=\"header\">\r\n" + 
	    				"          <table width=\"70\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td height=\"70\" style=\"padding: 0 20px 20px 0;\">\r\n" + 
	    				"                <img class=\"fix\" src=\"cid:logoDarwin\" width=\"70\" height=\"70\" border=\"0\" alt=\"\" />\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"            <table width=\"425\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"              <tr>\r\n" + 
	    				"                <td>\r\n" + 
	    				"          <![endif]-->\r\n" + 
	    				"          <table class=\"col425\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 425px;\">  \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td height=\"70\">\r\n" + 
	    				"                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td class=\"subhead\" style=\"padding: 0 0 0 3px;\">\r\n" + 
	    				"                      Darwin\r\n" + 
	    				"                    </td>\r\n" + 
	    				"                  </tr>\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td class=\"h1\" style=\"padding: 5px 0 0 0;\">\r\n" + 
	    				"                      "+assunto+"\r\n" + 
	    				"                    </td>\r\n" + 
	    				"                  </tr>\r\n" + 
	    				"                </table>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"                </td>\r\n" + 
	    				"              </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <![endif]-->\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td class=\"innerpadding borderbottom\">\r\n" + 
	    				"          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td class=\"h2\">\r\n" + 
	    				"                "+titulo+"\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td class=\"bodycopy\">\r\n" + 
	    				"                "+msg+"\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td class=\"footer\" bgcolor=\"#44525f\">\r\n" + 
	    				"          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"            \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td align=\"center\" style=\"padding: 20px 0 0 0;\">\r\n" + 
	    				"                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\r\n" + 
	    				"                      <a href=\"http://n2s.russas.ufc.br\" style=\"color:#fff\">\r\n" + 
	    				"                        <img src=\"cid:logoNucleo\" width=\"150px\" height=\"150px\" alt=\"Núcleo de Soluções em Software\" border=\"0\" />\r\n" + 
	    				"                      </a>\r\n" + 
	    				"                    </td>\r\n" +
	    				"                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\r\n" + 
	    				"                      <a href=\"http://russas.ufc.br\" style=\"color:#fff\">\r\n" + 
	    				"                        <img src=\"cid:logoUFC\" width=\"150px\" height=\"150px\" alt=\"Universidade Federal do Ceará - Campus Russas-CE\" border=\"0\" />\r\n" + 
	    				"                      </a>\r\n" + 
	    				"                    </td>\r\n" +
	    				"                  </tr>\r\n" + 
	    				"                </table>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"			<tr>\r\n" + 
	    				"              <td align=\"center\" class=\"footercopy\">\r\n" + 
	    				"                &copy; N2S "+LocalDate.now().getYear()+"<br/>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"    </table>\r\n" + 
	    				"    <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"          </td>\r\n" + 
	    				"        </tr>\r\n" + 
	    				"    </table>\r\n" + 
	    				"    <![endif]-->\r\n" + 
	    				"    </td>\r\n" + 
	    				"  </tr>\r\n" + 
	    				"</table>\r\n" + 
	    				"</body>\r\n" + 
	    				"</html>";
	            messageBodyPart.setContent(htmlText, "text/html");

	            multipart.addBodyPart(messageBodyPart);
	
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoDarwin = new FileDataSource(
	               Constantes.getLOGO_DARWIN());
	
	            messageBodyPart.setDataHandler(new DataHandler(logoDarwin));
	            messageBodyPart.addHeader("Content-ID", "<logoDarwin>");
	            
	            multipart.addBodyPart(messageBodyPart);
	            
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoN2S = new FileDataSource(
	                    Constantes.getLOGO_N2S());
	
	                 messageBodyPart.setDataHandler(new DataHandler(logoN2S));
	                 messageBodyPart.addHeader("Content-ID", "<logoNucleo>");
	                 multipart.addBodyPart(messageBodyPart);
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoUFC = new FileDataSource(
		                 Constantes.getLOGO_UFC());
		              messageBodyPart.setDataHandler(new DataHandler(logoUFC));
		              messageBodyPart.addHeader("Content-ID", "<logoUFC>");
	
	            multipart.addBodyPart(messageBodyPart);
	
	            message.setContent(multipart);
	
	            Address[] toSend = InternetAddress
	                       .parse(from);  
	            message.setRecipients(Message.RecipientType.TO, toSend);
	            message.setRecipients(Message.RecipientType.BCC, toUser);
	            message.setSubject(assunto);
	            message.setContent(multipart);
	            Transport.send(message);
			}

       } catch (MessagingException e) {
    	   	e.printStackTrace();
            throw new RuntimeException(e);
       }
		
	}
	
	public void sendHtmlEmailSet() throws EmailException, MalformedURLException {
		try {
			if (!toSet.isEmpty()) { 
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            String emails = "";
	            int i = 0;
	            for(Iterator<UsuarioBeans> iter = to.iterator(); iter.hasNext();) {
	            	UsuarioBeans u = iter.next();
                  	if (u.isRecebeEmail()) {
                	  emails += (u.getEmail());
	            		if (i + 1 != toSet.size()) {
	            			emails += (",");
	            		}
                  	}
                  	i++;
	            }
	            Address[] toUser = InternetAddress
	                       .parse(emails);  
	            MimeMultipart multipart = new MimeMultipart("related");
	
	            BodyPart messageBodyPart = new MimeBodyPart();
	            String htmlText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
	    				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
	    				" \r\n" + 
	    				"<head>\r\n" + 
	    				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n" + 
	    				"  <title>Darwin - Sistema de Gerenciamento de Seleções</title>\r\n" + 
	    				"  <style type=\"text/css\">\r\n" + 
	    				"  body {margin: 0; padding: 0; min-width: 100%!important;}\r\n" + 
	    				"  img {height: auto;}\r\n" + 
	    				"  .content {width: 100%; max-width: 600px;}\r\n" + 
	    				"  .header {padding: 40px 30px 20px 30px;}\r\n" + 
	    				"  .innerpadding {padding: 30px 30px 30px 30px;}\r\n" + 
	    				"  .borderbottom {border-bottom: 1px solid #f2eeed;}\r\n" + 
	    				"  .subhead {font-size: 15px; color: #ffffff; font-family: sans-serif; letter-spacing: 10px;}\r\n" + 
	    				"  .h1, .h2, .bodycopy {color: #153643; font-family: sans-serif;}\r\n" + 
	    				"  .h1 {font-size: 33px; line-height: 38px; font-weight: bold;}\r\n" + 
	    				"  .h2 {padding: 0 0 15px 0; font-size: 24px; line-height: 28px; font-weight: bold;}\r\n" + 
	    				"  .bodycopy {font-size: 16px; line-height: 22px;}\r\n" + 
	    				"  .button {text-align: center; font-size: 18px; font-family: sans-serif; font-weight: bold; padding: 0 30px 0 30px;}\r\n" + 
	    				"  .button a {color: #ffffff; text-decoration: none;}\r\n" + 
	    				"  .footer {padding: 20px 30px 15px 30px;}\r\n" + 
	    				"  .footercopy {font-family: sans-serif; font-size: 14px; color: #ffffff;}\r\n" + 
	    				"  .footercopy a {color: #ffffff; text-decoration: underline;}\r\n" + 
	    				"\r\n" + 
	    				"  @media only screen and (max-width: 550px), screen and (max-device-width: 550px) {\r\n" + 
	    				"  body[yahoo] .hide {display: none!important;}\r\n" + 
	    				"  body[yahoo] .buttonwrapper {background-color: transparent!important;}\r\n" + 
	    				"  body[yahoo] .button {padding: 0px!important;}\r\n" + 
	    				"  body[yahoo] .button a {background-color: #e05443; padding: 15px 15px 13px!important;}\r\n" + 
	    				"  body[yahoo] .unsubscribe {display: block; margin-top: 20px; padding: 10px 50px; background: #2f3942; border-radius: 5px; text-decoration: none!important; font-weight: bold;}\r\n" + 
	    				"  }\r\n" + 
	    				"\r\n" + 
	    				"  /*@media only screen and (min-device-width: 601px) {\r\n" + 
	    				"    .content {width: 600px !important;}\r\n" + 
	    				"    .col425 {width: 425px!important;}\r\n" + 
	    				"    .col380 {width: 380px!important;}\r\n" + 
	    				"    }*/\r\n" + 
	    				"\r\n" + 
	    				"  </style>\r\n" + 
	    				"</head>\r\n" + 
	    				"\r\n" + 
	    				"<body yahoo bgcolor=\"#f6f8f1\">\r\n" + 
	    				"<table width=\"100%\" bgcolor=\"#f6f8f1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
	    				"<tr>\r\n" + 
	    				"  <td>\r\n" + 
	    				"    <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"        <tr>\r\n" + 
	    				"          <td>\r\n" + 
	    				"    <![endif]-->     \r\n" + 
	    				"    <table bgcolor=\"#ffffff\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td bgcolor=\"#c7d8a7\" class=\"header\">\r\n" + 
	    				"          <table width=\"70\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td height=\"70\" style=\"padding: 0 20px 20px 0;\">\r\n" + 
	    				"                <img class=\"fix\" src=\"cid:logoDarwin\" width=\"70\" height=\"70\" border=\"0\" alt=\"\" />\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"            <table width=\"425\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"              <tr>\r\n" + 
	    				"                <td>\r\n" + 
	    				"          <![endif]-->\r\n" + 
	    				"          <table class=\"col425\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 425px;\">  \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td height=\"70\">\r\n" + 
	    				"                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td class=\"subhead\" style=\"padding: 0 0 0 3px;\">\r\n" + 
	    				"                      Darwin\r\n" + 
	    				"                    </td>\r\n" + 
	    				"                  </tr>\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td class=\"h1\" style=\"padding: 5px 0 0 0;\">\r\n" + 
	    				"                      "+assunto+"\r\n" + 
	    				"                    </td>\r\n" + 
	    				"                  </tr>\r\n" + 
	    				"                </table>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"                </td>\r\n" + 
	    				"              </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <![endif]-->\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td class=\"innerpadding borderbottom\">\r\n" + 
	    				"          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td class=\"h2\">\r\n" + 
	    				"                "+titulo+"\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td class=\"bodycopy\">\r\n" + 
	    				"                "+msg+"\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td class=\"footer\" bgcolor=\"#44525f\">\r\n" + 
	    				"          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"            \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td align=\"center\" style=\"padding: 20px 0 0 0;\">\r\n" + 
	    				"                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\r\n" + 
	    				"                      <a href=\"http://n2s.russas.ufc.br\" style=\"color:#fff\">\r\n" + 
	    				"                        <img src=\"cid:logoNucleo\" width=\"150px\" height=\"150px\" alt=\"Núcleo de Soluções em Software\" border=\"0\" />\r\n" + 
	    				"                      </a>\r\n" + 
	    				"                    </td>\r\n" +
	    				"                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\r\n" + 
	    				"                      <a href=\"http://russas.ufc.br\" style=\"color:#fff\">\r\n" + 
	    				"                        <img src=\"cid:logoUFC\" width=\"150px\" height=\"150px\" alt=\"Universidade Federal do Ceará - Campus Russas-CE\" border=\"0\" />\r\n" + 
	    				"                      </a>\r\n" + 
	    				"                    </td>\r\n" +
	    				"                  </tr>\r\n" + 
	    				"                </table>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"			<tr>\r\n" + 
	    				"              <td align=\"center\" class=\"footercopy\">\r\n" + 
	    				"                &copy; N2S "+LocalDate.now().getYear()+"<br/>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"    </table>\r\n" + 
	    				"    <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"          </td>\r\n" + 
	    				"        </tr>\r\n" + 
	    				"    </table>\r\n" + 
	    				"    <![endif]-->\r\n" + 
	    				"    </td>\r\n" + 
	    				"  </tr>\r\n" + 
	    				"</table>\r\n" + 
	    				"</body>\r\n" + 
	    				"</html>";
	            messageBodyPart.setContent(htmlText, "text/html");
	            // add it
	            multipart.addBodyPart(messageBodyPart);
	
	            // second part (the image)
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoDarwin = new FileDataSource(
	               Constantes.getLOGO_DARWIN());
	
	            messageBodyPart.setDataHandler(new DataHandler(logoDarwin));
	            messageBodyPart.addHeader("Content-ID", "<logoDarwin>");
	            
	            multipart.addBodyPart(messageBodyPart);
	            
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoN2S = new FileDataSource(
	                    Constantes.getLOGO_N2S());
	
	                 messageBodyPart.setDataHandler(new DataHandler(logoN2S));
	                 messageBodyPart.addHeader("Content-ID", "<logoNucleo>");
	                 multipart.addBodyPart(messageBodyPart);
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoUFC = new FileDataSource(
		                 Constantes.getLOGO_UFC());
		              messageBodyPart.setDataHandler(new DataHandler(logoUFC));
		              messageBodyPart.addHeader("Content-ID", "<logoUFC>");
	
	            multipart.addBodyPart(messageBodyPart);
	
	            message.setContent(multipart);
	
	            Address[] toSend = InternetAddress
	                       .parse(from);  
	            message.setRecipients(Message.RecipientType.TO, toSend);
	            message.setRecipients(Message.RecipientType.BCC, toUser);
	            message.setSubject(assunto);
	            message.setContent(multipart);
	            Transport.send(message);
			}

       } catch (MessagingException e) {
    	   	e.printStackTrace();
            throw new RuntimeException(e);
       }
		
	}
	
	public void sendHtmlEmailUsuario() throws EmailException, MalformedURLException {
		try {
			if (toUsuario != null) { 
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            Address[] toUser = InternetAddress
	                       .parse(toUsuario.getEmail());  
	            MimeMultipart multipart = new MimeMultipart("related");

	            BodyPart messageBodyPart = new MimeBodyPart();
	            String htmlText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
	    				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
	    				" \r\n" + 
	    				"<head>\r\n" + 
	    				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n" + 
	    				"  <title>Darwin - Sistema de Gerenciamento de Seleções</title>\r\n" + 
	    				"  <style type=\"text/css\">\r\n" + 
	    				"  body {margin: 0; padding: 0; min-width: 100%!important;}\r\n" + 
	    				"  img {height: auto;}\r\n" + 
	    				"  .content {width: 100%; max-width: 600px;}\r\n" + 
	    				"  .header {padding: 40px 30px 20px 30px;}\r\n" + 
	    				"  .innerpadding {padding: 30px 30px 30px 30px;}\r\n" + 
	    				"  .borderbottom {border-bottom: 1px solid #f2eeed;}\r\n" + 
	    				"  .subhead {font-size: 15px; color: #ffffff; font-family: sans-serif; letter-spacing: 10px;}\r\n" + 
	    				"  .h1, .h2, .bodycopy {color: #153643; font-family: sans-serif;}\r\n" + 
	    				"  .h1 {font-size: 33px; line-height: 38px; font-weight: bold;}\r\n" + 
	    				"  .h2 {padding: 0 0 15px 0; font-size: 24px; line-height: 28px; font-weight: bold;}\r\n" + 
	    				"  .bodycopy {font-size: 16px; line-height: 22px;}\r\n" + 
	    				"  .button {text-align: center; font-size: 18px; font-family: sans-serif; font-weight: bold; padding: 0 30px 0 30px;}\r\n" + 
	    				"  .button a {color: #ffffff; text-decoration: none;}\r\n" + 
	    				"  .footer {padding: 20px 30px 15px 30px;}\r\n" + 
	    				"  .footercopy {font-family: sans-serif; font-size: 14px; color: #ffffff;}\r\n" + 
	    				"  .footercopy a {color: #ffffff; text-decoration: underline;}\r\n" + 
	    				"\r\n" + 
	    				"  @media only screen and (max-width: 550px), screen and (max-device-width: 550px) {\r\n" + 
	    				"  body[yahoo] .hide {display: none!important;}\r\n" + 
	    				"  body[yahoo] .buttonwrapper {background-color: transparent!important;}\r\n" + 
	    				"  body[yahoo] .button {padding: 0px!important;}\r\n" + 
	    				"  body[yahoo] .button a {background-color: #e05443; padding: 15px 15px 13px!important;}\r\n" + 
	    				"  body[yahoo] .unsubscribe {display: block; margin-top: 20px; padding: 10px 50px; background: #2f3942; border-radius: 5px; text-decoration: none!important; font-weight: bold;}\r\n" + 
	    				"  }\r\n" + 
	    				"\r\n" + 
	    				"  /*@media only screen and (min-device-width: 601px) {\r\n" + 
	    				"    .content {width: 600px !important;}\r\n" + 
	    				"    .col425 {width: 425px!important;}\r\n" + 
	    				"    .col380 {width: 380px!important;}\r\n" + 
	    				"    }*/\r\n" + 
	    				"\r\n" + 
	    				"  </style>\r\n" + 
	    				"</head>\r\n" + 
	    				"\r\n" + 
	    				"<body yahoo bgcolor=\"#f6f8f1\">\r\n" + 
	    				"<table width=\"100%\" bgcolor=\"#f6f8f1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
	    				"<tr>\r\n" + 
	    				"  <td>\r\n" + 
	    				"    <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"        <tr>\r\n" + 
	    				"          <td>\r\n" + 
	    				"    <![endif]-->     \r\n" + 
	    				"    <table bgcolor=\"#ffffff\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td bgcolor=\"#c7d8a7\" class=\"header\">\r\n" + 
	    				"          <table width=\"70\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td height=\"70\" style=\"padding: 0 20px 20px 0;\">\r\n" + 
	    				"                <img class=\"fix\" src=\"cid:logoDarwin\" width=\"70\" height=\"70\" border=\"0\" alt=\"\" />\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"            <table width=\"425\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
	    				"              <tr>\r\n" + 
	    				"                <td>\r\n" + 
	    				"          <![endif]-->\r\n" + 
	    				"          <table class=\"col425\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 425px;\">  \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td height=\"70\">\r\n" + 
	    				"                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td class=\"subhead\" style=\"padding: 0 0 0 3px;\">\r\n" + 
	    				"                      Darwin\r\n" + 
	    				"                    </td>\r\n" + 
	    				"                  </tr>\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td class=\"h1\" style=\"padding: 5px 0 0 0;\">\r\n" + 
	    				"                      "+assunto+"\r\n" + 
	    				"                    </td>\r\n" + 
	    				"                  </tr>\r\n" + 
	    				"                </table>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"                </td>\r\n" + 
	    				"              </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"          <![endif]-->\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td class=\"innerpadding borderbottom\">\r\n" + 
	    				"          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td class=\"h2\">\r\n" + 
	    				"                "+titulo+"\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td class=\"bodycopy\">\r\n" + 
	    				"                "+msg+"\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"      <tr>\r\n" + 
	    				"        <td class=\"footer\" bgcolor=\"#44525f\">\r\n" + 
	    				"          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"            \r\n" + 
	    				"            <tr>\r\n" + 
	    				"              <td align=\"center\" style=\"padding: 20px 0 0 0;\">\r\n" + 
	    				"                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    				"                  <tr>\r\n" + 
	    				"                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\r\n" + 
	    				"                      <a href=\"http://n2s.russas.ufc.br\" style=\"color:#fff\">\r\n" + 
	    				"                        <img src=\"cid:logoNucleo\" width=\"150px\" height=\"150px\" alt=\"Núcleo de Soluções em Software\" border=\"0\" />\r\n" + 
	    				"                      </a>\r\n" + 
	    				"                    </td>\r\n" +
	    				"                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\r\n" + 
	    				"                      <a href=\"http://russas.ufc.br\" style=\"color:#fff\">\r\n" + 
	    				"                        <img src=\"cid:logoUFC\" width=\"150px\" height=\"150px\" alt=\"Universidade Federal do Ceará - Campus Russas-CE\" border=\"0\" />\r\n" + 
	    				"                      </a>\r\n" + 
	    				"                    </td>\r\n" +
	    				"                  </tr>\r\n" + 
	    				"                </table>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"			<tr>\r\n" + 
	    				"              <td align=\"center\" class=\"footercopy\">\r\n" + 
	    				"                &copy; N2S "+LocalDate.now().getYear()+"<br/>\r\n" + 
	    				"              </td>\r\n" + 
	    				"            </tr>\r\n" + 
	    				"          </table>\r\n" + 
	    				"        </td>\r\n" + 
	    				"      </tr>\r\n" + 
	    				"    </table>\r\n" + 
	    				"    <!--[if (gte mso 9)|(IE)]>\r\n" + 
	    				"          </td>\r\n" + 
	    				"        </tr>\r\n" + 
	    				"    </table>\r\n" + 
	    				"    <![endif]-->\r\n" + 
	    				"    </td>\r\n" + 
	    				"  </tr>\r\n" + 
	    				"</table>\r\n" + 
	    				"</body>\r\n" + 
	    				"</html>";
	            messageBodyPart.setContent(htmlText, "text/html");

	            multipart.addBodyPart(messageBodyPart);

	            messageBodyPart = new MimeBodyPart();
	            DataSource logoDarwin = new FileDataSource(
	               Constantes.getLOGO_DARWIN());
	
	            messageBodyPart.setDataHandler(new DataHandler(logoDarwin));
	            messageBodyPart.addHeader("Content-ID", "<logoDarwin>");
	            
	            multipart.addBodyPart(messageBodyPart);
	            
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoN2S = new FileDataSource(
	                    Constantes.getLOGO_N2S());
	
	                 messageBodyPart.setDataHandler(new DataHandler(logoN2S));
	                 messageBodyPart.addHeader("Content-ID", "<logoNucleo>");
	                 multipart.addBodyPart(messageBodyPart);
	            messageBodyPart = new MimeBodyPart();
	            DataSource logoUFC = new FileDataSource(
		                 Constantes.getLOGO_UFC());
		              messageBodyPart.setDataHandler(new DataHandler(logoUFC));
		              messageBodyPart.addHeader("Content-ID", "<logoUFC>");
	
	            multipart.addBodyPart(messageBodyPart);
	
	            message.setContent(multipart);
	
	            
	            message.setRecipients(Message.RecipientType.TO, toUser);
	            message.setSubject(assunto);
	            message.setContent(multipart);
	            Transport.send(message);
			}

       } catch (MessagingException e) {
    	   	e.printStackTrace();
            throw new RuntimeException(e);
       }
		
	}

	@Override
	public void run() {
		if (TYPE_EMAIL == 1) {
			try {
				this.sendHtmlEmail();
			} catch (MalformedURLException | EmailException e) {
				e.printStackTrace();
			}
		} else if (TYPE_EMAIL == 2) {
			try {
				this.sendHtmlEmailSet();
			} catch (MalformedURLException | EmailException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.sendHtmlEmailUsuario();
			} catch (MalformedURLException | EmailException e) {
				e.printStackTrace();
			}
		}
		
	}
}
