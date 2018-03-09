//<start id="conventionalJndi"/> 
InitialContext ctx = null; 
try {
  ctx = new InitialContext();

  DataSource ds = 
      (DataSource) ctx.lookup("java:comp/env/jdbc/SpitterDatasource");
} catch (NamingException ne) {
  // handle naming exception ...
} finally { 
  if(ctx != null) {
      try {
        ctx.close();
      } catch (NamingException ne) {}
  }
}
//<end id="conventionalJndi"/> 

//<start id="conventionalJndi_oneLine"/> 
DataSource ds = 
    (DataSource) ctx.lookup("java:comp/env/jdbc/SpitterDatasource");
//<end id="conventionalJndi_oneLine"/> 

//<start id="wiringHibernateDataSource"/> 
<bean id="sessionFactory" 
  class="org.springframework.orm.hibernate3.annotation.[CA]
                                             AnnotationSessionFactoryBean">
  <property name="dataSource" ref="dataSource" />
  ...
</bean>
//<end id="wiringHibernateDataSource"/> 

//<start id="jndiLookup_cacheFalse"/> 
<jee:jndi-lookup id="dataSource"
    jndi-name="/jdbc/SpitterDS"
    resource-ref="true" 
    cache="false"
    proxy-interface="javax.sql.DataSource" />
//<end id="jndiLookup_cacheFalse"/> 

//<start id="jndiLookup_lookupOnStartup"/> 
<jee:jndi-lookup id="dataSource"
    jndi-name="/jdbc/SpitterDS"
    resource-ref="true" 
    lookup-on-startup="false"
    proxy-interface="javax.sql.DataSource" />
//<end id="jndiLookup_lookupOnStartup"/>


//<start id="jndiLookup_dmds"/> 
<bean id="devDataSource" 
   class="org.springframework.jdbc.datasource.DriverManagerDataSource"
   lazy-init="true"> 
  <property name="driverClassName"
            value="org.hsqldb.jdbcDriver" />
  <property name="url"
            value="jdbc:hsqldb:hsql://localhost/spitter/spitter" />
  <property name="username" value="sa" />
  <property name="password" value="" />
</bean>
//<end id="jndiLookup_dmds"/> 

//<start id="jndiLookup_defaultRef"/> 
<jee:jndi-lookup id="dataSource"
    jndi-name="/jdbc/SpitterDS"
    resource-ref="true" 
    default-ref="devDataSource" />
//<end id="jndiLookup_defaultRef"/> 


//<start id="localSlsb"/> 
<jee:local-slsb id="myEJB"
    jndi-name="my.ejb" 
    business-interface="com.habuma.ejb.MyEJB" />
//<end id="localSlsb"/> 

//<start id="remoteSlsb"/> 
<jee:remote-slsb id="myEJB"
    jndi-name="my.ejb" 
    business-interface="com.habuma.ejb.MyEJB" />
//<end id="remoteSlsb"/> 

