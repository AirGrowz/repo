package jp.go.bosai.saigaitask.form.oauth;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.framework.util.StringUtil;

/**
 * OAuth認証のアクションフォームクラスです.
 */
public class OAuthForm implements Serializable {
	private static final long serialVersionUID = 1L;

	//===============================================
	// OAuth サービスプロバイダ情報
	//===============================================
	/** WebサービスID */
	public Integer serviceId;

	/** Webサービス名称 */
	public String serviceName;

	/** コンシューマキー */
	public String consumerKey;

	/** コンシューマシークレット */
	public String consumerSecret;

	/** リクエストトークン取得URL */
	public String requestTokenURL;

	/** 認証URL */
	public String authorizeURL;

	/** アクセストークンURL */
	public String accessTokenURL;

	//===============================================
	// OAuth 認証情報
	//===============================================
	/** OAuth認証ID */
	public Integer oauthId;

	/** リクエストトークン */
	public String requestToken;

	/** トークンシークレット */
	public String tokenSecret;

	/** アクセストークン */
	public String accessToken;

	/** 認証コード */
	public String verifier;

	//===============================================
	// OAuth API情報
	//===============================================
	/** API URL */
	public String apiURL;

	/** API HTTPメソッド */
	public String apiMethod;

	/** API HTTPクエリ*/
	public String apiQuery;

	/**
	 * API HTTPクエリをMapのコレクションとして取得します.
	 * @return Collection<? extends Entry<String, String>>
	 */
	public Collection<? extends Entry<String, String>> getApiQueryParameters() {
		Collection<? extends Entry<String, String>> parameters = null;
		if(StringUtil.isNotEmpty(apiQuery)) {
			Map<String, String> params = new HashMap<String, String>();
			String[] paramArray = apiQuery.split("&");
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
					params.put(key, value);
				}
			}
			if(0<params.size()) parameters = params.entrySet();
		}
		return parameters;
	}
}
