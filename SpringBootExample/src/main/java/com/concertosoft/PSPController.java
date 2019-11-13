package com.concertosoft;

import java.io.File;



import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.concertosoft.Dao.MessageRepository;
import com.concertosoft.VO.PayerVO;
import com.concertosoft.pojo.Payer;
import com.concertosoft.service.PayerService;

@Controller
public class PSPController {
	

	@Autowired 
	MessageRepository messageRepo ;
	
	@Autowired
	PayerService service;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getForm(Model model) {
		
		 PayerVO p=new PayerVO(); 
		 model.addAttribute("payerForm", p);
	 
	return "index";
	
	}
	

	@PostMapping(value="/sendRequest")
	 //@Consumes(MediaType.APPLICATION_XML)									// appears in JAX-RS
	public String sendReqToNpci(@ModelAttribute("payer") PayerVO payervo, BindingResult result,Model model) {

		String ts,msgId, txnId = null;
		ts = service.getDate();
		msgId = service.getRandomCode();
		 String URI = "http://localhost:8080/NPCISimulator/ReqPay";
		 String xmlData = service.mapToXml(payervo);
	
//		 xmlData=xmlData.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
		MessageDetail message = new MessageDetail();
		message.setRequest(xmlData);
		messageRepo.save(message);
		  
		  RestTemplate restTemplate = new RestTemplate(); //
		  HttpHeaders headers = new HttpHeaders(); 
		  HttpEntity<String> request = new  HttpEntity<String>(xmlData,headers); 
		
		  ResponseEntity response1 = restTemplate.exchange(URI, HttpMethod.POST,request, String.class);
		  System.out.println(response1.getStatusCode());
		
		  model.addAttribute("message", response1.getBody());
		  
		  
		return "Pay";
	}
	

	
	@GetMapping("/RespPay")
	public String getResponseFromNpci(Model resp) {
		
		System.out.println("from respPay");
		return "PayResponse";
		 
	}
	

}
