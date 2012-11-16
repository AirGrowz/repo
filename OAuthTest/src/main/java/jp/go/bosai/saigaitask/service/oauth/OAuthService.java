package jp.go.bosai.saigaitask.service.oauth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

	@Resource protected HttpSession session;

	public String consumerKey;
	public String consumerSecret;
	public String callbackUrl;

	// OAuth
	public OAuthClient client = new OAuthClient(new URLConnectionClient());
	public OAuthServiceProvider provider;

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
		OAuthAccessor accessor = new OAuthAccessor(consumer);

		String redirectTo = null;
		try{
			client.getRequestToken(accessor);
			// TODO: ユーザがOAuthサービスプロバイダで認証後にリダイレクトするURLを指定する
			redirectTo = OAuth.addParameters(accessor.consumer.serviceProvider.userAuthorizationURL, OAuth.OAUTH_TOKEN, accessor.requestToken);
			System.out.println(OAuth.OAUTH_TOKEN+"(RequestToken): "+accessor.requestToken);
			System.out.println(OAuth.OAUTH_TOKEN_SECRET+": "+accessor.tokenSecret);
			session.setAttribute(OAuth.OAUTH_TOKEN, accessor.requestToken);
			session.setAttribute(OAuth.OAUTH_TOKEN_SECRET, accessor.tokenSecret);
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
	 */
	public void getAccessToken(String verifier) {
		String requestToken = (String) session.getAttribute(OAuth.OAUTH_TOKEN);
		String tokenSecret = (String) session.getAttribute(OAuth.OAUTH_TOKEN_SECRET);

		// OAuthコンシューマを作成
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl, consumerKey, consumerSecret, provider);

		// OAuthのアクセサーを作成
		OAuthAccessor accessor = new OAuthAccessor(consumer);
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
			session.setAttribute(OAuth.OAUTH_TOKEN, accessor.accessToken);
			session.setAttribute(OAuth.OAUTH_TOKEN_SECRET, accessor.tokenSecret);
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
	 * @return String レスポンス
	 */
	public String api(String url) {
		String tokenSecret = (String) session.getAttribute(OAuth.OAUTH_TOKEN_SECRET);
		String accessToken= (String) session.getAttribute(OAuth.OAUTH_TOKEN);

		// OAuthコンシューマを作成
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl, consumerKey, consumerSecret, provider);

		// OAuthのアクセサーを作成
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		accessor.tokenSecret = tokenSecret;
		accessor.accessToken = accessToken;

		String ret = null;
		try {
			OAuthMessage request = accessor.newRequestMessage(OAuthMessage.GET, url, null);
			OAuthResponseMessage responseMessage = client.access(request, ParameterStyle.AUTHORIZATION_HEADER);
			int statusCode = responseMessage.getHttpResponse().getStatusCode();
			System.out.println("API: "+url);
			if(statusCode == HttpResponseMessage.STATUS_OK) {
				String body = ret = responseMessage.readBodyAsString();
				System.out.println(body);
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

		return ret;
	}

}
