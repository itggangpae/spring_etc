package kr.co.adamsoft.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EtcServiceImpl implements EtcService {

	@Autowired
	private MailSender mailSender;


	@Override
	public void sendMail(HttpServletRequest request) {
		/*
		String setfrom = "ggangpae1@naver.com";         
		String tomail  = request.getParameter("tomail");     // 받는 사람 이메일
		String title   = request.getParameter("title");      // 보내는 사람 이메일
		String content = request.getParameter("content");    // 보내는 사람 이메일


		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(setfrom);  // 보내는사람 생략하거나 하면 정상작동을 안함
			message.setTo(tomail);     // 받는사람 이메일
			message.setSubject(title); // 메일제목은 생략이 가능하다
			message.setText(content);  // 메일 내용
			mailSender.send(message);
		} catch(Exception e){
			System.out.println(e);
		}
		 */

		try {
			MimeMessage message = ((JavaMailSenderImpl)mailSender).createMimeMessage();
			MimeMessageHelper messageHelper 
			= new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom("ggangpae3@naver.com");  // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo("ggangpae1@gmail.com");     // 받는사람 이메일
			messageHelper.setSubject("첨부파일"); // 메일제목은 생략이 가능하다
			messageHelper.setText("첨부파일 보내기");  // 메일 내용
			DataSource ds = new FileDataSource("/Users/adam/Documents/업무/인사이트시리즈.rtf");
			messageHelper.addAttachment(MimeUtility.encodeText("/Users/adam/Documents/업무/인사이트시리즈.rtf", "utf-8", "B"), ds);
			((JavaMailSenderImpl)mailSender).send(message);
		} catch(Exception e){
			System.out.println(e);
		}

	}

	@Override
	public void htmlMail() {
		MimeMessage message = ((JavaMailSenderImpl)mailSender).createMimeMessage();
		try {
			message.setFrom(new InternetAddress("ggangpae3@naver.com")); // 보내는사람 생략하거나 하면 정상작동을 안함
			message.setRecipient(RecipientType.TO,new InternetAddress("ggangpae1@gmail.com")); // 받는사람 이메일
			message.setSubject("html 메일"); // 메일제목은 생략이 가능하다
			message.setText("<a href='http:www.naver.com'>네이버</a>", "utf-8", "html"); // 메일 내용
			((JavaMailSenderImpl)mailSender).send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void push(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			response.setContentType("text/event-stream");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			Random r = new Random();
			writer.write("data: " + (r.nextInt(46)+1) + "\n\n");
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writer.close();
	}

	public String download(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(request.getParameter("addr"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(20000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStreamReader isr = new InputStreamReader(conn.getInputStream());
					BufferedReader br = new BufferedReader(isr);
					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						sb.append(line + "\n");
					}
					br.close();
					conn.disconnect();
				}
			}
		} catch (Exception e) {
			System.out.println("가져오기 실패:" + e.getMessage());
		}
		return sb.toString();
	}

}
