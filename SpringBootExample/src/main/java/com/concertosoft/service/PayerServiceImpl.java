package com.concertosoft.service;

import java.io.FileOutputStream;
import java.io.StringWriter;
import com.concertosoft.pojo.*;
import java.io.Writer;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.concertosoft.VO.PayerVO;
import com.concertosoft.service.PayerService;

@Service
public class PayerServiceImpl implements PayerService {
	
	String pathToXMLUnsigned = "D:\\UPI-Project\\RequestMessages\\";
	
	public String mapToXml(PayerVO pv) {
		ReqPay newRequest = new ReqPay();
		Payer newPayer = newRequest.getPayer();
		newPayer.setAddress(pv.getAddress());
		newPayer.setAmount(pv.getAmount());
		newPayer.setCurrency(pv.getCurrency());
		newPayer.setName(pv.getName());

		return save(newRequest);
	}

	public String save(ReqPay newRequest) {																		//need to use a Parent class as Request from which all the request types can inherit.
		String filePath = pathToXMLUnsigned+"Reqpay.xml";
		Writer w = new StringWriter();
		try {
			        JAXBContext context1 = JAXBContext.newInstance(ReqPay.class);
			        Marshaller marshalObj = context1.createMarshaller();
			        marshalObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			        marshalObj.marshal(newRequest, new FileOutputStream(filePath));
			        marshalObj.marshal(newRequest, w);
			        System.out.println(w.toString());
	}catch(Exception e)
		{e.printStackTrace();}
//		System.out.println(w.toString());
return w.toString();
		
	}
	

	public String getRandomCode() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String salt = "";
		Random rnd = new Random();
		while (salt.length() < 32) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt += SALTCHARS.charAt(index);
		}

		String result = "CON";
		return result + salt;
	}


	public String getDate() {
		DateTime dt = new DateTime();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		String str = fmt.print(dt);
		return str;
	}
	

}
