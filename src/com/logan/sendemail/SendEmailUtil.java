package com.logan.sendemail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendEmailUtil {
	public static void main(String[] args) throws IOException {
		Email from = new Email("luogenkeji@gmail.com");
		String subject = "【罗根科技】-测试";
		Email to = new Email("m598806805@163.com"); 
		Content content = new Content("text/html",
				"Hi,我正在使用百度<a href=\"http://www.baidu.com\">http://www.baidu.com</a>,给大家分享,快来看看。<a href=\"http://www.baidu.com\">【罗根科技】</a>"
				);
		Mail mail = new Mail(from, subject, to, content); 
		Attachments attachments		= new Attachments(); attachments.setContentId("123 contentId");
		attachments.setContent(fileToBase64("src/com/logan/image/logan_head.jpg"));
		attachments.setDisposition("inline");
		attachments.setFilename("logan_head.jpg"); 
		attachments.setType("jpg");
		mail.addAttachments(attachments);

		// Personalization personalization = new Personalization();
		//personalization.addTo(new Email("15566909389@163.com"));
		//personalization.addTo(new Email("zhjzhang@cn.ibm.com"));
		//personalization.addTo(new Email("dumingy@cn.ibm.com")); 
		//personalization.addTo(new Email("marhe@cn.ibm.com"));

		// mail.addPersonalization(personalization);

		// SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		SendGrid sg	= new SendGrid("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");

		Request request = new Request(); 
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			System.out.println("mail.build() : " +mail.build());
			request.setBody(mail.build()); 
			Response response =	sg.api(request);

			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders()); 
		} catch (IOException e) {
			throw e;
		}
		//		String str = fileToBase64("src/com/logan/image/logan_head.jpg");
		//		System.out.println(str);
		//		base64ToFile("src/com/logan/image/logan_head2.jpg",str);
		//		if(file.exists()) {
		//			file.createNewFile();
		//		}
		//		FileWriter fw = new FileWriter(file);
		//		PrintWriter pw = new PrintWriter(fw);
		//		pw.print(fw);
		//		pw.close();
		//		fw.close();
	}

	public static String fileToBase64(String path) {
		String base64 = null;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			fis = new FileInputStream(path);
			byte[] buffer = new byte[1024];
			int temp = 0;
			while ((temp = fis.read(buffer)) != -1) {
				baos.write(buffer,0,temp);
			}
			//fis.close();
			base64 = new String(Base64.getEncoder().encodeToString(baos.toByteArray()));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

		return base64;
	}

	public static void base64ToFile(String fileName, String base64) {

		FileOutputStream fos = null;
		//		ByteArrayInputStream bais = new ByteArrayInputStream();

		try {
			fos = new FileOutputStream(fileName);
			byte[] bytes = Base64.getDecoder().decode(base64);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			byte[] buffer = new byte[1024];
			int temp = 0;
			while((temp = bais.read(buffer)) != -1) {
				//bais.read(buffer, 0, temp);
				fos.write(buffer, 0, temp);
			}
			fos.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
