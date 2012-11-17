package jp.go.bosai.saigaitask.service.oauth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.ParameterStyle;
import net.oauth.client.OAuthClient;
import net.oauth.client.OAuthResponseMessage;
import net.oauth.client.URLConnectionClient;
import net.oauth.http.HttpResponseMessage;

/**
 * OAuth認証を行うサービスクラスです.
 */
public class OAuthService {

	public String consumerKey;
	public String consumerSecret;
	public String callbackUrl;

	// OAuth
	public OAuthClient client = new OAuthClient(new URLConnectionClient());
	public OAuthServiceProvider provider;
	public OAuthAccessor accessor;
	public OAuthResponseMessage apiResponse;

	/**
	 * OAuth認証を開始します.
	 * ユーザに認証されていないリクエストトークンをサーバから取得し、
	 * ユーザがこのアプリケーションを許可するためのURLを返します.
	 * @return String OAuthサービスプロバイダの認証URL
	 */
	public String getRequestToken() {

		// OAuthコンシューマを作成
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl, consumerKey, consumerSecret, provider);

		// OAuthのアクセサーを作成
		accessor = new OAuthAccessor(consumer);

		String redirectTo = null;
		try{
			client.getRequestToken(accessor);
			// TODO: ユーザがOAuthサービスプロバイダで認証後にリダイレクトするURLを指定する
			redirectTo = OAuth.addParameters(accessor.consumer.serviceProvider.userAuthorizationURL, OAuth.OAUTH_TOKEN, accessor.requestToken);
			System.out.println(OAuth.OAUTH_TOKEN+"(RequestToken): "+accessor.requestToken);
			System.out.println(OAuth.OAUTH_TOKEN_SECRET+": "+accessor.tokenSecret);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (OAuthException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return redirectTo;
	}

	/**
	 * アクセストークンを取得します.
	 * OAuthサービスプロバイダに認証コードを送信して、
	 * 認証済みリクエストトークンを交換してアクセストークンを取得します.
	 * @param verifier 認証コード
	 * @param requestToken 認証済みリクエストトークン
	 * @param tokenSecret トークンシークレット
	 */
	public void getAccessToken(String verifier, String requestToken, String tokenSecret) {
		// OAuthコンシューマを作成
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl, consumerKey, consumerSecret, provider);

		// OAuthのアクセサーを作成
		accessor = new OAuthAccessor(consumer);
		accessor.requestToken = requestToken;
		accessor.tokenSecret = tokenSecret;

		try {
			// アクセスコードをパラメータで渡す
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(OAuth.OAUTH_VERIFIER, verifier);

			// アクセストークンを取得する
			OAuthMessage response = client.getAccessToken(accessor, null, parameters.entrySet());
			response.requireParameters(OAuth.OAUTH_TOKEN, OAuth.OAUTH_TOKEN_SECRET);
			System.out.println(OAuth.OAUTH_TOKEN+"(AccessToken): "+accessor.accessToken);
			System.out.println(OAuth.OAUTH_TOKEN_SECRET+": "+accessor.tokenSecret);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OAuthException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * APIを実行します.
	 * @param url APIのURL
	 * @param accessToken アクセストークン
	 * @param tokenSecret トークンシークレット
	 * @return boolean success
	 */
	public boolean api(String method, String url, Collection<? extends Entry<String, String>> parameters, String accessToken, String tokenSecret) {

		// OAuthコンシューマを作成
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl, consumerKey, consumerSecret, provider);

		// OAuthのアクセサーを作成
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		accessor.accessToken = accessToken;
		accessor.tokenSecret = tokenSecret;

		apiResponse = null;

		try {
			OAuthMessage request = accessor.newRequestMessage(method, url, parameters);
			OAuthResponseMessage responseMessage = client.access(request, ParameterStyle.AUTHORIZATION_HEADER);
			int statusCode = responseMessage.getHttpResponse().getStatusCode();
			System.out.println("API: "+url);
			System.out.println("body type: "+responseMessage.getBodyType());
			System.out.println("body encoding: "+responseMessage.getBodyEncoding());
			if(statusCode == HttpResponseMessage.STATUS_OK) {
				apiResponse = responseMessage;
				return true;
			}
			else {
				System.out.println("API Error: "+statusCode);
			}
		} catch (OAuthException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return false;
	}

}
