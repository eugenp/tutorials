package com.concertosoft.Dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.concertosoft.MessageDetail;
import com.concertosoft.pojo.Payer;

public interface MessageRepository extends CrudRepository<MessageDetail,Integer> {

MessageDetail save(MessageDetail messageData);
List<MessageDetail> findAll();
//void update(MessageDetail messageData);
	
}
