/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.jdbc.bugs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.test.DefaultProperties;


public class Bug51582
{

  /**
   * @param args
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException
  {
    org.apache.tomcat.jdbc.pool.DataSource datasource = null;
    PoolConfiguration p = new DefaultProperties();

    p.setJmxEnabled(true);
    p.setTestOnBorrow(false);
    p.setTestOnReturn(false);
    p.setValidationInterval(1000);
    p.setTimeBetweenEvictionRunsMillis(2000);

    p.setMaxWait(2000);
    p.setMinEvictableIdleTimeMillis(1000);

    datasource = new org.apache.tomcat.jdbc.pool.DataSource();
    datasource.setPoolProperties(p);
    datasource.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReportJmx(threshold=200)");
    ConnectionPool pool = datasource.createPool();


    Connection con = pool.getConnection();
    Statement st = con.createStatement();
    try {
        st.execute("DROP ALIAS SLEEP");
    } catch (Exception ignore) {
        // Ignore
    }
    st.execute("CREATE ALIAS SLEEP AS $$\nboolean sleep() {\n        try {\n            Thread.sleep(10000);\n            return true;        } catch (Exception x) {\n            return false;\n        }\n}\n$$;");
    st.close();
    con.close();
    int iter = 0;
    while ((iter++) < 10)
    {
      final Connection connection = pool.getConnection();
      final CallableStatement s = connection.prepareCall("{CALL SLEEP()}");

      List<Thread> threadList = new ArrayList<Thread>();

      for (int l = 0; l < 3; l++)
      {
        final int i = l;

        Thread thread = new Thread()
        {
          @Override
          public void run()
          {
            try
            {
              if (i == 0)
              {
                Thread.sleep(1000);
                s.cancel();
              }
              else if (i == 1)
              {
                //or use some other statement which will block for a longer time
                long start = System.currentTimeMillis();
                System.out.println("["+getName()+"] Calling SP SLEEP");
                s.execute();
                System.out.println("["+getName()+"] Executed SP SLEEP ["+(System.currentTimeMillis()-start)+"]");
              }
              else
              {
                Thread.sleep(1000);
                connection.close();
              }
            }
            catch (InterruptedException e)
            {

            }
            catch (SQLException e)
            {
              e.printStackTrace();
            }

          }

        };
        threadList.add(thread);
        thread.start();
      }
      for (Thread t : threadList)
      {
        try
        {
          t.join();
        }
        catch (InterruptedException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

    }


  }
}
