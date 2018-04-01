package mycompany.controller;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.security.oauth.consumer.CoreOAuthConsumerSupport;
import org.springframework.security.oauth.consumer.OAuthConsumerProcessingFilter;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;
import org.springframework.web.servlet.mvc.AbstractController;

public abstract class CompanyController extends AbstractController {

	private static final String resourceId = "linkedIn";

	private CoreOAuthConsumerSupport support = null;

	public void setSupport(CoreOAuthConsumerSupport support) {
		this.support = support;
	}

	public OAuthConsumerToken getAccessTokenFromRequest(
			HttpServletRequest request) {
		OAuthConsumerToken token = null;

		List<OAuthConsumerToken> tokens = (List<OAuthConsumerToken>) request
				.getAttribute(OAuthConsumerProcessingFilter.ACCESS_TOKENS_DEFAULT_ATTRIBUTE);
		if (tokens != null) {
			for (OAuthConsumerToken consumerToken : tokens) {
				if (consumerToken.getResourceId().equals(resourceId)) {
					token = consumerToken;
					break;
				}
			}
		}
		return token;
	}

	public HttpClient getHttpClient() {
		HttpClient hc = new HttpClient();
		hc.getParams()
				.setParameter(
						HttpMethodParams.USER_AGENT,
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 ( .NET CLR 3.5.30729)");

		return hc;
	}

	public GetMethod getGetMethod(OAuthConsumerToken accessToken, URL url) {
		GetMethod method = new GetMethod(url.toString());
		method.setRequestHeader("Authorization",
				getHeader(accessToken, url, "GET"));
		return method;
	}

	public String getHeader(OAuthConsumerToken accessToken, URL url,
			String method) {
		ProtectedResourceDetails details = support
				.getProtectedResourceDetailsService()
				.loadProtectedResourceDetailsById(accessToken.getResourceId());
		return support.getAuthorizationHeader(details, accessToken, url,
				method, null);
	}
}
