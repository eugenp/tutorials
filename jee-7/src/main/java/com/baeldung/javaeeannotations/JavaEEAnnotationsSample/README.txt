About the application
---------------------
This application demonstrates the usage of JavaEE Web Annotations.


Contents of the application
---------------------------
1. AccountServlet.java - Demonstrates the @WebServlet and @ServletSecurity annotation.

NOTES: @WebServlet annotation designates the AccountServlet class as a Servlet component.
       The usage of its parameters 'urlPatterns' & 'initParams' can be observed.
       An initialization parameter 'type' is being set to denote the type of the bank account.
       
       @ServletSecurity annotation imposes security constraints on the AccountServlet based on
       the tomcat-users.xml.
       
       This code assumes that your tomcat-users.xml looks as follows:

       <role rolename="Admin"/>
       <role rolename="Member"/>
       <role rolename="Guest"/>
       <user username="Annie" password="admin" roles="Admin, Member, Guest" />
       <user username="Diane" password="coder" roles="Member, Guest" />
       <user username="Ted" password="newbie" roles="Guest" />
   
N.B : To see  @ServletSecurity annotation in action, please uncomment the annotation code 
      for @ServletSecurity.
      
      
2. BankAppServletContextListener.java - Demonstrates the @WebListener annotation for denoting a class as a ServletContextListener.
   
NOTES: Sets a Servlet context attribute ATTR_DEFAULT_LANGUAGE to 'english' on web application start up, 
       which can then be used throughout the application.


3. LogInFilter.java - Demonstrates the @WebFilter annotation.
                      
NOTES: @WebFilter annotation designates the LogInFilter class as a Filter component.
       It filters all requests to the bank account servlet and redirects them to
       the login page.
       
N.B : To see @WebFilter annotation in action, please uncomment the annotation code for @WebFilter.


4. UploadCustomerDocumentsServlet.java - Demonstrates the @MultipartConfig annotation.

NOTES: @MultipartConfig anotation designates the UploadCustomerDocumentsServlet Servlet component, 
       to handle multipart/form-data requests.
       To see it in action, deploy the web application an access the url: http://<your host>:<your port>/JavaEEAnnotationsSample/upload.jsp
       Once you upload a file from here, it will get uploaded to D:/custDocs (assuming such a folder exists).
       
 
5. index.jsp - This is the welcome page.

NOTES: You can enter a deposit amount here and click on the 'Deposit' button to see the AccountServlet in action.

6. login.jsp - All requests to the AccountServlet are redirected to this page, if the LogInFilter is imposed.

7. upload.jsp - Demonstrates the usage of handling multipart/form-data requests by the UploadCustomerDocumentsServlet.       
       
   
Building and Running the application
------------------------------------
To build the application:

1. Open the project in eclipse
2. Right click on it in eclispe and choose Run As > Maven build
3. Give 'clean install' under Goals
4. This should build the WAR file of the application

To run the application:

1. Right click on the project
2. Run as > Run on Server
3. This will start you Tomcat server and deploy the application (Provided that you have configured Tomcat in your eclipse)
4. You should now be able to access the url : http://<your host>:<your port>/JavaEEAnnotationsSample
