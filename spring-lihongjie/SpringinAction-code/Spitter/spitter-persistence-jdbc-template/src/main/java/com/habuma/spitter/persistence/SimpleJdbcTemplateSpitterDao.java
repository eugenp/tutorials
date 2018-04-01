package com.habuma.spitter.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public class SimpleJdbcTemplateSpitterDao implements
        SpitterDao {


  private static final String SQL_INSERT_SPITTER = "insert into spitter (username, password, fullname, email, update_by_email) values (?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE_SPITTER = "update spitter set username = ?, password = ?, fullname = ?, set email=?"
          + "where id = ?";

  private static final String SQL_SELECT_SPITTER = ""
          + "select id, username, password, fullname from spitter";

  private static final String SQL_SELECT_SPITTER_BY_ID = SQL_SELECT_SPITTER
          + " where id=?";

  //<start id="java_addSpitter_vars" /> 
  private SimpleJdbcTemplate jdbcTemplate;
  public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  //<end id="java_addSpitter_vars" />

  //<start id="java_getSpitterById" /> 
  public Spitter getSpitterById(long id) {
    return jdbcTemplate.queryForObject(//<co id="co_query"/>
            SQL_SELECT_SPITTER_BY_ID,
        new ParameterizedRowMapper<Spitter>() {
          public Spitter mapRow(ResultSet rs, int rowNum) 
              throws SQLException {
            Spitter spitter = new Spitter();//<co id="co_map"/>
            spitter.setId(rs.getLong(1));
            spitter.setUsername(rs.getString(2));
            spitter.setPassword(rs.getString(3));
            spitter.setFullName(rs.getString(4));
            return spitter;
          }
        }, 
        id //<co id="co_bind"/>
        );
  }
  //<end id="java_getSpitterById" />

  //<start id="java_addSpitter" /> 
  public void addSpitter(Spitter spitter) {
    jdbcTemplate.update(SQL_INSERT_SPITTER,//<co id="co_updateSpitter"/>
            spitter.getUsername(), 
            spitter.getPassword(),
            spitter.getFullName(),
            spitter.getEmail(),
            spitter.isUpdateByEmail());
    spitter.setId(queryForIdentity());
  }
  //<end id="java_addSpitter" />

  public void saveSpitter(Spitter spitter) {
    jdbcTemplate.update(SQL_UPDATE_SPITTER,
            spitter.getUsername(), 
            spitter.getPassword(),
            spitter.getFullName(), 
            spitter.getEmail(),
            spitter.getId());
  }
  
  public List<Spittle> getRecentSpittle() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void saveSpittle(Spittle spittle) {
    // TODO Auto-generated method stub
    
  }
  

  //<start id="java_queryForIdentity" /> 
  private long queryForIdentity() {
    return jdbcTemplate.queryForLong("call identity()");
  }
  //<end id="java_queryForIdentity" />

  public List<Spittle> getSpittlesForSpitter(
          Spitter spitter) {
    // TODO Auto-generated method stub
    return null;
  }
  

  public Spitter getSpitterByUsername(String username) {
    // TODO Auto-generated method stub
    return null;
  }

  public void deleteSpittle(long id) {
    throw new UnsupportedOperationException();
  }

  public Spittle getSpittleById(long id) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public List<Spitter> findAllSpitters() {
    // TODO Auto-generated method stub
    return null;
  }
}
