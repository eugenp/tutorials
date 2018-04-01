package mycompany.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;

import mycompany.model.StatusUpdate;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;
import org.springframework.util.xml.DomUtils;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LinkedInController extends CompanyController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String urlString = "http://api.linkedin.com/v1/people/~/network/updates";
		HttpClient hc = getHttpClient();
		OAuthConsumerToken token = getAccessTokenFromRequest(request);
		URL url = new URL(urlString);
		GetMethod get = getGetMethod(token, url);
		try {
			hc.executeMethod(get);
			InputStream input = get.getResponseBodyAsStream();
			DocumentBuilder db = Dom.getDocumentBuilder();
			Document doc = db.parse(input);
			List<StatusUpdate> result = new ArrayList<StatusUpdate>();
			NodeList updates = doc.getElementsByTagName("update");
			for (int i = 0, n = updates.getLength(); i < n; i++) {
				Element elem = (Element) updates.item(i);
				String type = DomUtils.getChildElementValueByTagName(elem,
						"update-type");
				if ("STAT".equals(type)) {
					result.add(parse(elem));
				}
			}
			ModelAndView mav = new ModelAndView("linkedin");
			mav.addObject("updates", result);
			return mav;
		} finally {
			get.releaseConnection();
		}
	}

	private StatusUpdate parse(Element element) {
		StatusUpdate statusUpdate = new StatusUpdate();

		Element personElem = Dom.getSingleElementByTagName(element, "person");
		String firstName = DomUtils.getChildElementValueByTagName(personElem,
				"first-name");
		String lastName = DomUtils.getChildElementValueByTagName(personElem,
				"last-name");
		String status = DomUtils.getChildElementValueByTagName(personElem,
				"current-status");
		statusUpdate.setUsername(firstName + "," + lastName);
		statusUpdate.setStatus(status);
		return statusUpdate;
	}

}
