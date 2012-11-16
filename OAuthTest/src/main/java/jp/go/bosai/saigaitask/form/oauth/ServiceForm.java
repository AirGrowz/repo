package jp.go.bosai.saigaitask.form.oauth;

import java.io.Serializable;

/**
 * OAuth認証のWebサービスのアクションフォームクラスです.
 */
public class ServiceForm implements Serializable {
	private static final long serialVersionUID = 1L;
	public String accessTokenURL;
	public String authorizeURL;
	public String consumerKey;
	public String consumerSecret;
	public String requestTokenURL;
	public String serviceName;
}
