package nju.yufan.yummy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SpringTest {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String from;

	@Test
	public void setMailSender() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("1205685624@qq.com");
		message.setTo("wan9yufan@163.com");
		message.setSubject("javaMail 测试");
		message.setText("<html><head></head><body><h2>哇哦，成功了</h2></body></html>");
		javaMailSender.send(message);
	}

	@Test
	public void sendHTMLMail() {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom(from);
			messageHelper.setTo("wan9yufan@163.com");
			messageHelper.setSubject("........");
			messageHelper.setText("<html><head></head><body><h2>哇哦，成功了</h2></body></html>",true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void boolCompareTest(){
		List<Boolean> list = new ArrayList<>();
		boolean a = true;
		boolean b = false;
		list.add(b);
		list.add(a);
		list.sort(Boolean::compare);
		for(boolean x:list){
			System.out.println(x);
		}
	}
}
