// Copyright (C) 2000 by Matt Towers <eBozo_AT_hotmail_DOT_com>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

/*
 The source code and object code, of the HttpsMessage class is copyright and
 owned by Matt Towers. 

 Feel free to use the HttpsMessage class in the development of any development
 project. For this use you are granted a non-exclusive, non-transferable 
 limited license at no cost.

 Redistribution of the HttpsMessage source code is permitted provided that the
 following conditions are met: 

   1.You maintain the original copyright notice in the source code. 
   2.This license file is redistributed with the source code. 
   3.You acknowledge that the parent HttpMessage class, with which this class
     is intended to be used, is owned and copyrighted by Jason Hunter
     <jhunter_AT_acm_DOT_org>. (For more information see
     http://www.servlets.com) 

 To clarify, you may use the HttpsMessage class to build new software and may
 distribute the object code as you see fit. You may NOT distribute the source
 code as part of a software development kit, other library, or development tool
 without consent of the copyright holder. Any modified form of the HttpsMessage
 class is bound by these same restrictions.

 Note that the HttpsMessage class is provided "as is" and the author will not
 be liable for any damages suffered as a result of your use. 
 Furthermore, you understand the source code comes without any technical
 support.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS
 OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 Matt Towers <eBozo_AT_hotmail_DOT_com> 
*/

package com.oreilly.servlet;

import java.io.*;
import java.net.*;
import java.util.*;
import java.security.Security;
import java.security.Provider;

/**
 * A class to simplify HTTP/HTTPS client-server communication.	It abstracts
 * the communication into messages, which can be either GET or POST.
 * <p>
 * It can be used like this:
 * <blockquote><pre>
 * &nbsp;
 * HttpsMessage msg = new HttpsMessage("https://[some server]");
 * &nbsp;
 * Properties props = new Properties();
 * props.put("name", "value");
 * &nbsp;
 * InputStream in = msg.sendGetMessage(props);
 * </pre></blockquote>
 * This class extends the HttpMessage class
 * written by Jason Hunter at servlets.com.
 * The HttpMessage class can be found in the com.oreilly.servlet
 * package found at www.servlets.com
 * <p>
 * For information see http://www.javaworld.com/javatips/jw-javatip96.html
 * Note this class works with JDK 1.2 or later only.
 * <p>
 * @author <b>Matt Towers</b>
 * @author Copyright &#169; 2000
 * @version 1.0, 2000/05/05
 */
public class HttpsMessage extends com.oreilly.servlet.HttpMessage
{

  //A flag to indicate weather or not the stream handler has been set
  static boolean m_bStreamHandlerSet = false;

  /**
   * Constructs a new HttpsMessage that can be used to communicate with the 
   * servlet at the specified URL using HTTPS.
   *
   * @param szURL the server resource (typically a servlet) with which 
   * to communicate
   */
  public HttpsMessage(String szURL) throws Exception
  {
	super(null);
	//If the stream handler has already been set
	//there is no need to do anything
	if( !m_bStreamHandlerSet )
	{
		String szVendor = System.getProperty("java.vendor");
		String szVersion = System.getProperty("java.version");
		//Assumes a system version string of the form [major].[minor].[release]  (eg. 1.2.2)
		Double dVersion = new Double(szVersion.substring(0, 3));

		//Otherwise, if we are running in a MS environment, use the MS stream handler.
		if( -1 < szVendor.indexOf("Microsoft") )
		{
    		try
    		{
    			Class clsFactory = Class.forName("com.ms.net.wininet.WininetStreamHandlerFactory" );
				if ( null != clsFactory)
					URL.setURLStreamHandlerFactory((URLStreamHandlerFactory)clsFactory.newInstance());
			}
			catch( ClassNotFoundException cfe )
			{
				throw new Exception("Unable to load the Microsoft SSL stream handler.  Check classpath."  + cfe.toString());
			}			
			//If the stream handler factory has already been successfuly set
			//make sure our flag is set and eat the error
			catch( Error err ){m_bStreamHandlerSet = true;}
		}
		//If we are in a normal Java environment, try to use the JSSE handler.
		else if( 1.2 <= dVersion.doubleValue() )
		{
			System.getProperties().put("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol"); 
			try
			{
				//if we have the JSSE provider available, and it has not already been
				//set, add it as a new provide to the Security class.
				Class clsFactory = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
				if( (null != clsFactory) && (null == Security.getProvider("SunJSSE")) )
						Security.addProvider((Provider)clsFactory.newInstance());
			}
			catch( ClassNotFoundException cfe )
			{
				throw new Exception("Unable to load the JSSE SSL stream handler.  Check classpath."  + cfe.toString());
			}
		}
										
		m_bStreamHandlerSet = true;
	}
	
	super.servlet = new URL(szURL);
  }
}
