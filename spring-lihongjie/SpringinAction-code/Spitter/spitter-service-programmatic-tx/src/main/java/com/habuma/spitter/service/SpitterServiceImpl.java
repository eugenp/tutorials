package com.habuma.spitter.service;

import static java.lang.Math.*;
import static java.util.Collections.*;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.persistence.SpitterDao;

@Transactional(propagation=Propagation.REQUIRED)
public class SpitterServiceImpl implements SpitterService {

  //<start id="java_addSpittle" /> 
  public void saveSpittle(final Spittle spittle) {
    txTemplate.execute(new TransactionCallback<Void>() {
      public Void doInTransaction(TransactionStatus txStatus) {
        try {
        spitterDao.saveSpittle(spittle);
        } catch (RuntimeException e) {
          txStatus.setRollbackOnly();
          throw e;
        }
        return null;
      }      
    });
  }
  //<end id="java_addSpittle" />

  @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
  public List<Spittle> getRecentSpittles(int count) {
    List<Spittle> recentSpittles = 
        spitterDao.getRecentSpittle();
    
    reverse(recentSpittles);
    
    return recentSpittles.subList(0, 
            min(49, recentSpittles.size()));
  }
  
  public void saveSpitter(final Spitter spitter) {
    txTemplate.execute(new TransactionCallback<Void>() {
      public Void doInTransaction(TransactionStatus txStatus) {
        if(spitter.getId() == null) {
          spitterDao.addSpitter(spitter);
        } else {
          spitterDao.saveSpitter(spitter);
        }

        return null;
      }
    });
  }
  
  public Spitter getSpitter(long id) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Spitter getSpitter(String username) {
    return spitterDao.getSpitterByUsername(username);
  }

  public void startFollowing(Spitter follower, Spitter followee) {
    // TODO Auto-generated method stub
    
  }
  
  public List<Spittle> getSpittlesForSpitter(
          Spitter spitter) {
    return spitterDao.getSpittlesForSpitter(spitter);
  }
  
  public void deleteSpittle(long id) {
    spitterDao.deleteSpittle(id);
  }
  
  public Spittle getSpittleById(long id) {
    return spitterDao.getSpittleById(id);
  }
  
  private SpitterDao spitterDao;
  public void setSpitterDao(SpitterDao spitterDao) {
    this.spitterDao = spitterDao;
  }
  
  private TransactionTemplate txTemplate;
  public void setTransactionTemplate(TransactionTemplate txTemplate) {
    this.txTemplate = txTemplate;
  }

  public List<Spittle> getSpittlesForSpitter(
          String username) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public List<Spitter> getAllSpitters() {
    return spitterDao.findAllSpitters();
  }
}
