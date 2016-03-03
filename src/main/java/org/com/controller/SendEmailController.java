package org.com.controller;

import java.net.InetAddress;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.com.dao.BaseDao;
import org.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SendEmailController {
 
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	BaseDao baseDao;
    
    @RequestMapping(value="/SendMail",method=RequestMethod.POST)
	public @ResponseBody
	String doSendEmail(@RequestBody int id){
    	try{
    	System.out.println(id);
    	User user=(User) baseDao.getEntityById(User.class, id);
    	InetAddress IP=InetAddress.getLocalHost();
    	System.out.println("IP of my system is := "+IP.getHostAddress());
        // takes input from e-mail form
        String subject = "subject";
        String message = "http://"+IP.getHostAddress()+":8090/Chatmachine/confirm.html?id="+user.getId();
        // prints debug info
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + "http://"+IP.getHostAddress()+":8090/Chatmachine/confirm.html?id="+user.getId());
         
        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
         
        // sends the e-mail
        mailSender.send(email);
         
        // forwards to the view named "Result"
        }catch(Exception e){
        	System.out.println(e);
        }
    	return "Result";
    	
	}
    
    @RequestMapping(value="/emailConfirm",method=RequestMethod.POST)
   	public @ResponseBody
   	String emailConfirm(@RequestBody String[] arr){
    	baseDao.saveOrupdateByQuery("update User set emailstatus='1' where id="+arr[1]);
    	return arr[0];
    }
}