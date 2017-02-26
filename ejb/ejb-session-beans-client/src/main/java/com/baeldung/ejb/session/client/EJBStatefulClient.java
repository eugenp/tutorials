package com.baeldung.ejb.session.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.baeldung.ejb.stateful.beans.ItemStatefulRemote;

public class EJBStatefulClient {

	public EJBStatefulClient() {
	}

	private Context context = null;

	public Boolean getEJBRemoteMessage() {
		EJBStatefulClient ejb = new EJBStatefulClient();
		Boolean result = true;
		try {
			// 1. Obtaining Context
			ejb.createInitialContext();
			// 2. Generate JNDI Lookup name and caste
			ItemStatefulRemote itemStatefulOne = ejb.lookup();
			ItemStatefulRemote itemStatefulTwo = ejb.lookup();

			itemStatefulOne.addItem("Book");
			itemStatefulOne.addItem("Pen");
			itemStatefulOne.addItem("Copy");
			itemStatefulOne.addItem("Pencil");

			result = itemStatefulOne.getItemList().equals(itemStatefulTwo.getItemList());

			return result;
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ejb.closeContext();
			} catch (NamingException e) {
				e.printStackTrace();

			}
		}
	}

	public ItemStatefulRemote lookup() throws NamingException {

		// The app name is the EAR name of the deployed EJB without .ear suffix.
		// Since we haven't deployed the application as a .ear, the app name for
		// us will be an empty string
		final String appName = "";
		final String moduleName = "session-beans";
		final String distinctName = "";
		final String beanName = "ItemStatefulRemote";
		final String viewClassName = ItemStatefulRemote.class.getName() + "?stateful";
		final String toLookup = String.format("ejb:%s/%s/%s/%s!%s", appName, moduleName, distinctName, beanName,
				viewClassName);
		return (ItemStatefulRemote) context.lookup(toLookup);
	}

	public void createInitialContext() throws NamingException {
		Properties prop = new Properties();
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		prop.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		prop.put(Context.SECURITY_PRINCIPAL, "testUser");
		prop.put(Context.SECURITY_CREDENTIALS, "admin1234!");
		prop.put("jboss.naming.client.ejb.context", false);

		context = new InitialContext(prop);
	}

	public void closeContext() throws NamingException {
		if (context != null) {
			context.close();
		}
	}

}
