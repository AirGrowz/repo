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

import java.util.Collection;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import jp.go.bosai.saigaitask.dto.oauth.OAuthDto;
import jp.go.bosai.saigaitask.form.oauth.OAuthForm;
import jp.go.bosai.saigaitask.service.oauth.EcommapAPIService;
import jp.go.bosai.saigaitask.service.oauth.OAuthService;
import net.oauth.OAuth;
import net.oauth.OAuthServiceProvider;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * OAuth認証のアクションクラスです.
 */
public class IndexAction {

	@ActionForm
	@Resource public OAuthForm oAuthForm;

	@Resource protected ServletRequest request;

	@Resource public OAuthDto oAuthDto;
	@Resource protected OAuthService oAuthService;
	@Resource protected EcommapAPIService ecommapAPIService;

	public String apiResult;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}

	@Execute(validator = false, redirect = true)
	public String requestRequestToken() {

		Integer serviceId = oAuthForm.serviceId;
		if(serviceId!=null) {
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

		return "/oauth";
	}

	@Execute(validator = false, redirect = true)
	public String requestAccessToken() {

		OAuthForm oAuthForm = oAuthDto.oAuthForm;

		if(oAuthForm!=null) {
			// verifier
			String verifier = request.getParameter(OAuth.OAUTH_VERIFIER);

			// consumer key
			String consumerKey = oAuthForm.consumerKey;
			oAuthService.consumerKey = consumerKey;

			// consumer secret
			String consumerSecret = oAuthForm.consumerSecret;
			oAuthService.consumerSecret = consumerSecret;

			// provider
			oAuthService.provider = new OAuthServiceProvider(oAuthForm.requestTokenURL, oAuthForm.authorizeURL, oAuthForm.accessTokenURL);

			oAuthService.getAccessToken(verifier, oAuthForm.requestToken, oAuthForm.tokenSecret);
			oAuthForm.accessToken = oAuthService.accessor.accessToken;
			oAuthForm.tokenSecret = oAuthService.accessor.tokenSecret;

			// 認証中のデータを削除する
			oAuthDto.oAuthForm = null;
			// 認証済みリストに追加する
			oAuthDto.authorizedList.add(oAuthForm);
		}

		return "/oauth";
	}

	@Execute(validator = false, redirect = true, urlPattern="authorized/delete/{oauthId}")
	public String deleteAuthorized() {
		int oauthId = Integer.parseInt((String) request.getParameter("oauthId"));
		oAuthDto.authorizedList.remove(oauthId);
		return "/oauth";
	}

	@Execute(validator = false)
	public String api() {

		Integer oauthId = oAuthForm.oauthId;
		String url = oAuthForm.apiURL;
		String method = oAuthForm.apiMethod;
		Collection<? extends Entry<String, String>> parameters = oAuthForm.getApiQueryParameters();

		OAuthForm oAuthForm = oAuthDto.authorizedList.get(oauthId);

		// consumer key
		String consumerKey = oAuthForm.consumerKey;
		oAuthService.consumerKey = consumerKey;

		// consumer secret
		String consumerSecret = oAuthForm.consumerSecret;
		oAuthService.consumerSecret = consumerSecret;

		// provider
		oAuthService.provider = new OAuthServiceProvider(oAuthForm.requestTokenURL, oAuthForm.authorizeURL, oAuthForm.accessTokenURL);

		apiResult = oAuthService.api(method, url, parameters, oAuthForm.accessToken, oAuthForm.tokenSecret);

		return "/oauth";
	}

}
