package jp.go.bosai.saigaitask.service.oauth;

import net.oauth.OAuthServiceProvider;

/**
 * OAuthのサービスプロバイダを生成するファクトリクラスです.
 * サービスプロバイダを増やす場合はクラスメソッドを追加することで利用できるようになります.
 */
public class OAuthServiceProviderFactory {

	/**
	 * eコミマップのOAuth認証サービスプロバイダを生成します.
	 * @param ecomServer
	 * @return
	 */
	public static OAuthServiceProvider getEcomMapProvider(String ecomServer) {
		String requestTokenUrl = ecomServer+"/oauth/request_token";
		String authorizeUrl = ecomServer+"/oauth/authenticate";
		String accessTokenUrl = ecomServer+"/oauth/access_token";
		OAuthServiceProvider provider = new OAuthServiceProvider(requestTokenUrl, authorizeUrl, accessTokenUrl);
		return provider;
	}

	/**
	 * TwitterのOAuth認証サービスプロバイダを生成します.
	 * @param ecomServer
	 * @return
	 */
	public static OAuthServiceProvider getTwitterProvider() {
		String requestTokenUrl = "https://api.twitter.com/oauth/request_token";
		String authorizeUrl = "https://api.twitter.com/oauth/authenticate";
		String accessTokenUrl = "https://api.twitter.com/oauth/access_token";
		OAuthServiceProvider provider = new OAuthServiceProvider(requestTokenUrl, authorizeUrl, accessTokenUrl);
		return provider;
	}


}
