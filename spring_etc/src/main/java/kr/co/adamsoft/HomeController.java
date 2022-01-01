package kr.co.adamsoft;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.adamsoft.service.EtcService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("echo")
	public String echo() {
		return "echo";
	}

	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String chat(Locale locale, Model model) {
		return "chat";
	}
	
	@Autowired
	private EtcService etcService;

	@RequestMapping("textmail")
	public String textmail(){
		return "textmail";
	}
	
	@RequestMapping("mailsending")
	public String mailSending(HttpServletRequest request){
		etcService.sendMail(request);
		return "redirect:./";
	}
	
	@RequestMapping("htmlmail")
	public String htmlmail(){
		etcService.htmlMail();
		return "redirect:/";
	}
	
	@RequestMapping("push")
	public void push(HttpServletRequest request, HttpServletResponse response){
		etcService.push(request, response);
	}
	
	@RequestMapping("proxy")
	public String proxy(HttpServletRequest request, HttpServletResponse response) {
		String result = etcService.download(request, response);
		request.setAttribute("result", result);
		return "proxy";
	}	
	
	
	@RequestMapping(value = "frontend", method = RequestMethod.GET)
	public String frontend() {
		return "frontend";
	}
	@RequestMapping(value = "database", method = RequestMethod.GET)
	public String database() {
		return "database";
	}
	@RequestMapping(value = "backend", method = RequestMethod.GET)
	public String backend() {
		return "backend";
	}

}
