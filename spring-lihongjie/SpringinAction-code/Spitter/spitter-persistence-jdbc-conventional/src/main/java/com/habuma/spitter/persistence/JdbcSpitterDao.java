package com.habuma.spitter.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public class JdbcSpitterDao implements
        SpitterDao {
  //<start id="ugly_addSpitter_vars" /> 

  private static final String SQL_INSERT_SPITTER = 
    "insert into spitter (username, password, fullname) values (?, ?, ?)";
  private DataSource dataSource;
  //<end id="ugly_addSpitter_vars" />

  //<start id="ugly_saveSpitter_vars" /> 
  private static final String SQL_UPDATE_SPITTER = 
      "update spitter set username = ?, password = ?, fullname = ?"
      + "where id = ?";
  //<end id="ugly_saveSpitter_vars" />

  //<start id="ugly_getSpitter_vars" /> 
  private static final String SQL_SELECT_SPITTER = 
      "select id, username, fullname from spitter where id = ?";

  //<end id="ugly_getSpitter_vars" />

  //<start id="ugly_addSpitter" /> 
  public void addSpitter(Spitter spitter) {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = dataSource.getConnection(); //<co id="co_openConnection"/>

      stmt = conn.prepareStatement(SQL_INSERT_SPITTER); //<co id="co_createStatement"/>

      stmt.setString(1, spitter.getUsername());//<co id="co_bindParameters"/>
      stmt.setString(2, spitter.getPassword());
      stmt.setString(3, spitter.getFullName());

      stmt.execute();//<co id="co_executeStatement"/>
      
    } catch (SQLException e) {
      // do something...not sure what, though
      //<co id="co_handleExceptions"/>
    } finally {
      try {
        if (stmt != null) {//<co id="co_cleanUp"/>
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        // I'm even less sure about what to do here
      }
    }
  }

  //<end id="ugly_addSpitter" />

  //<start id="ugly_getSpitter" /> 
  public Spitter getSpitterById(long id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      conn = dataSource.getConnection();//<co id="co_openConnection"/>
      
      stmt = conn.prepareStatement(SQL_SELECT_SPITTER);//<co id="co_createStatement"/>
      
      stmt.setLong(1, id);//<co id="co_bindParameter"/>
      
      rs = stmt.executeQuery();//<co id="co_executeQuery"/>
      
      Spitter spitter = null;
      if (rs.next()) {//<co id="co_processResults"/>
        spitter = new Spitter();
        spitter.setId(rs.getLong("id"));
        spitter.setUsername(rs.getString("username"));
        spitter.setPassword(rs.getString("password"));
        spitter.setFullName(rs.getString("fullname"));
      }
      return spitter;
    } catch (SQLException e) {
      //<co id="co_handleExceptions"/>
    } finally {
      if(rs != null) {
        try {//<co id="co_cleanUp"/>
          rs.close();
        } catch(SQLException e) {}
      }
      
      if(stmt != null) {
        try {
        stmt.close();
        } catch(SQLException e) {}
      }
      
      if(conn != null) {
        try {
          conn.close();
        } catch(SQLException e) {}
      }
    }

    return null;
  }
  //<end id="ugly_getSpitter" />

  //<start id="ugly_saveSpitter" /> 
  public void saveSpitter(Spitter spitter) {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = dataSource.getConnection();//<co id="co_openConnection"/>
      
      stmt = conn.prepareStatement(SQL_UPDATE_SPITTER);//<co id="co_createConnection"/>
      
      stmt.setString(1, spitter.getUsername());//<co id="co_bindParameters"/>
      stmt.setString(2, spitter.getPassword());
      stmt.setString(3, spitter.getFullName());
      stmt.setLong(4, spitter.getId());

      stmt.execute();//<co id="co_executeStatement"/>
    } catch (SQLException e) {
      // Still not sure what I'm supposed to do here   <co id="co_handleExceptions"/>
    } finally {
      try {
        if (stmt != null) {//<co id="co_cleanUp"/>
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        // or here
      }
    }
  }
  //<end id="ugly_saveSpitter" />

  
  public List<Spittle> getRecentSpittle() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void saveSpittle(Spittle spittle) {
    // TODO Auto-generated method stub
    
  }

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
