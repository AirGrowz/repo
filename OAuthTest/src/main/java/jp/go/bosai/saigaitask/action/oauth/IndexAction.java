/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.go.bosai.saigaitask.action.oauth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import jp.go.bosai.saigaitask.dto.LoginDataDto;
import jp.go.bosai.saigaitask.service.oauth.EcommapAPIService;
import jp.go.bosai.saigaitask.service.oauth.OAuthService;
import jp.go.bosai.saigaitask.service.oauth.OAuthServiceProviderFactory;

import net.oauth.OAuth;

import org.seasar.struts.annotation.Execute;

/**
 * OAuth認証のアクションクラスです.
 */
public class IndexAction {

	@Resource protected ServletRequest request;
	@Resource protected ServletResponse response;
	@Resource protected HttpSession session;

	@Resource protected LoginDataDto loginDataDto;
	@Resource protected OAuthService oAuthService;
	@Resource protected EcommapAPIService ecommapAPIService;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}

	@Execute(validator = false, redirect = true)
	public String requestToken() {

		// consumer key
		String consumerKey = null;
		session.setAttribute(OAuth.OAUTH_CONSUMER_KEY, consumerKey);
		oAuthService.consumerKey = consumerKey;

		// consumer secret
		String consumerSecret = null;
		oAuthService.consumerSecret = consumerSecret;

		// callback url
		String callbackUrl = null;
		oAuthService.callbackUrl = callbackUrl;

		// ecom server
		String ecomServer = null;
		oAuthService.provider = OAuthServiceProviderFactory.getEcomMapProvider(ecomServer);

		return oAuthService.getRequestToken();
	}

	@Execute(validator = false, redirect = true)
	public String accessToken() {

		// consumer key
		String consumerKey = (String) session.getAttribute(OAuth.OAUTH_CONSUMER_KEY);
		oAuthService.consumerKey = consumerKey;

		// verifier
		String verifier = request.getParameter(OAuth.OAUTH_VERIFIER);

		oAuthService.getAccessToken(verifier);
		return "test";
	}

	@Execute(validator = false)
	public String test() {
		// APIが使えるかチェックする
		//ecommapAPIService.ecomServer = ecomServer;

		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = new PrintWriter(response.getOutputStream());
			out.write("<div>"+oAuthService.api(ecommapAPIService.getVersion())+"</div>");
			out.write("<div>"+oAuthService.api(ecommapAPIService.getWhoAmI())+"</div>");
			out.write("<div>"+oAuthService.api(ecommapAPIService.getUser(1, 1))+"</div>");
			out.write("<div>"+oAuthService.api(ecommapAPIService.getGroup(1, 1))+"</div>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
