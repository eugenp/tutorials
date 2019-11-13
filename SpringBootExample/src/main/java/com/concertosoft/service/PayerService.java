package com.concertosoft.service;

import com.concertosoft.pojo.Payer;
import com.concertosoft.pojo.ReqPay;
import com.concertosoft.VO.PayerVO;;

public interface PayerService {

	String mapToXml(PayerVO pv);
	
	String save(ReqPay jaxbToDB);

	String getDate();
	String getRandomCode();
	
}
