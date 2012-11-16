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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import jp.go.bosai.saigaitask.dto.oauth.OAuthDto;
import jp.go.bosai.saigaitask.form.oauth.OAuthForm;
import jp.go.bosai.saigaitask.service.oauth.EcommapAPIService;
import jp.go.bosai.saigaitask.service.oauth.OAuthService;
import net.oauth.OAuth;
import net.oauth.OAuthServiceProvider;

import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.Execute;

/**
 * OAuth認証のアクションクラスです.
 */
public class IndexAction {

	@Resource protected ServletRequest request;
	@Resource protected ServletResponse response;
	@Resource protected HttpSession session;

	@Resource public OAuthDto oAuthDto;
	@Resource protected OAuthService oAuthService;
	@Resource protected EcommapAPIService ecommapAPIService;

	public String apiResult;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}

	@Execute(validator = false, redirect = true)
	public String requestToken() {

		int serviceId = Integer.parseInt((String) request.getParameter("serviceId"));

		OAuthForm oAuthForm = oAuthDto.serviceList.get(serviceId);

		// consumer key
		String consumerKey = oAuthForm.consumerKey;
		oAuthService.consumerKey = consumerKey;

		// consumer secret
		String consumerSecret = oAuthForm.consumerSecret;
		oAuthService.consumerSecret = consumerSecret;

		// callback url
		String callbackUrl = null;
		oAuthService.callbackUrl = callbackUrl;

		// provider
		oAuthService.provider = new OAuthServiceProvider(oAuthForm.requestTokenURL, oAuthForm.authorizeURL, oAuthForm.accessTokenURL);

		String redirectTo = oAuthService.getRequestToken();
		oAuthForm.requestToken = oAuthService.accessor.requestToken;
		oAuthForm.tokenSecret = oAuthService.accessor.tokenSecret;

		oAuthDto.oAuthForm = oAuthForm;

		return redirectTo;
	}

	@Execute(validator = false, redirect = true)
	public String accessToken() {

		// verifier
		String verifier = request.getParameter(OAuth.OAUTH_VERIFIER);

		OAuthForm oAuthForm = oAuthDto.oAuthForm;

		// consumer key
		String consumerKey = oAuthForm.consumerKey;
		oAuthService.consumerKey = consumerKey;

		// consumer secret
		String consumerSecret = oAuthForm.consumerSecret;
		oAuthService.consumerSecret = consumerSecret;

		// callback url
		String callbackUrl = null;
		oAuthService.callbackUrl = callbackUrl;

		// provider
		oAuthService.provider = new OAuthServiceProvider(oAuthForm.requestTokenURL, oAuthForm.authorizeURL, oAuthForm.accessTokenURL);

		oAuthService.getAccessToken(verifier, oAuthForm.requestToken, oAuthForm.tokenSecret);
		oAuthForm.accessToken = oAuthService.accessor.accessToken;
		oAuthForm.tokenSecret = oAuthService.accessor.tokenSecret;

		// 認証中のデータを削除する
		oAuthDto.oAuthForm = null;
		// 認証済みリストに追加する
		oAuthDto.authorizedList.add(oAuthForm);

		return "/oauth";
	}

	@Execute(validator = false)
	public String api() {

		int oauthId = Integer.parseInt((String) request.getParameter("oauthId"));
		String url = request.getParameter("apiURL");
		String method = request.getParameter("method");
		String query = request.getParameter("query");
		System.out.println("query: "+query);
		Map<String, String> parameters = new HashMap<String, String>();
		if(StringUtil.isNotEmpty(query)) {
			String[] paramArray = query.split("&");
			for(String param : paramArray) {
				String key = null, value = null;
				if(StringUtil.isNotEmpty(param)) {
					int pos = param.indexOf("=");
					if(pos!=-1) {
						String[] kv = param.split("=");
						if(pos==0) {
							value = kv[0];
						}
						else {
							key = kv[0];
							if(kv.length==2) {
								value = kv[1];
							}
						}
					}
					else {
						key = param;
					}
					parameters.put(key, value);
				}
			}
		}

		OAuthForm oAuthForm = oAuthDto.authorizedList.get(oauthId);

		// consumer key
		String consumerKey = oAuthForm.consumerKey;
		oAuthService.consumerKey = consumerKey;

		// consumer secret
		String consumerSecret = oAuthForm.consumerSecret;
		oAuthService.consumerSecret = consumerSecret;

		// callback url
		String callbackUrl = null;
		oAuthService.callbackUrl = callbackUrl;

		// provider
		oAuthService.provider = new OAuthServiceProvider(oAuthForm.requestTokenURL, oAuthForm.authorizeURL, oAuthForm.accessTokenURL);

		apiResult = oAuthService.api(method, url, parameters.entrySet(), oAuthForm.accessToken, oAuthForm.tokenSecret);

		return "/oauth";
	}

}
