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
import java.io.InputStream;
import java.util.Collection;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;

import jp.go.bosai.saigaitask.dto.oauth.OAuthDto;
import jp.go.bosai.saigaitask.form.oauth.OAuthForm;
import jp.go.bosai.saigaitask.service.oauth.EcommapAPIService;
import jp.go.bosai.saigaitask.service.oauth.OAuthService;
import net.oauth.OAuthServiceProvider;

import org.apache.commons.io.IOUtils;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * OAuth認証のアクションクラスです.
 */
public class IndexAction {

	@ActionForm
	@Resource public OAuthForm oAuthForm;

	@Resource protected ServletResponse response;

	@Resource public OAuthDto oAuthDto;
	@Resource protected OAuthService oAuthService;
	@Resource protected EcommapAPIService ecommapAPIService;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}

	/**
	 * OAuth認証のリクエストトークンをサーバから取得します.
	 * @return String
	 */
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

	/**
	 * OAuth認証のアクセストークンをサーバから取得します.
	 * @return String
	 */
	@Execute(validator = false, redirect = true)
	public String requestAccessToken() {

		OAuthForm oAuthForm = oAuthDto.oAuthForm;

		if(oAuthForm!=null) {

			// verifier
			String verifier = this.oAuthForm.verifier;

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

	/**
	 * 認証済みリストから指定IDのデータを削除します.
	 * @return String
	 */
	@Execute(validator = false, redirect = true, urlPattern="authorized/delete/{oauthId}")
	public String deleteAuthorized() {
		Integer oauthId = oAuthForm.oauthId;
		oAuthDto.authorizedList.remove(oauthId.intValue());
		return "/oauth";
	}

	/**
	 * OAuth認証でAPIを利用します.
	 * @return String
	 */
	@Execute(validator = false)
	public String api() {

		Integer oauthId = oAuthForm.oauthId;
		String url = oAuthForm.apiURL;
		String method = oAuthForm.apiMethod;
		Collection<? extends Entry<String, String>> parameters = oAuthForm.getApiQueryParameters();

		if(StringUtil.isNotEmpty(url)) {
			OAuthForm oAuthForm = oAuthDto.authorizedList.get(oauthId);

			// consumer key
			String consumerKey = oAuthForm.consumerKey;
			oAuthService.consumerKey = consumerKey;

			// consumer secret
			String consumerSecret = oAuthForm.consumerSecret;
			oAuthService.consumerSecret = consumerSecret;

			// provider
			oAuthService.provider = new OAuthServiceProvider(oAuthForm.requestTokenURL, oAuthForm.authorizeURL, oAuthForm.accessTokenURL);

			boolean success = oAuthService.api(method, url, parameters, oAuthForm.accessToken, oAuthForm.tokenSecret);
			if(success) {
				response.setContentType("application/octet-stream");
				try {
					InputStream is = oAuthService.apiResponse.getBodyAsStream();
					IOUtils.copy(is, response.getOutputStream());
					response.flushBuffer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return "/oauth";
	}

}
