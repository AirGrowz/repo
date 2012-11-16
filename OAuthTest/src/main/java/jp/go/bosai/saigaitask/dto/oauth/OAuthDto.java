package jp.go.bosai.saigaitask.dto.oauth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.go.bosai.saigaitask.form.oauth.OAuthForm;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class OAuthDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登録済みサービスリスト
	 */
	public List<OAuthForm> serviceList = new ArrayList<OAuthForm>();

	/**
	 * OAuth認証中のデータ
	 */
	public OAuthForm oAuthForm;

	/**
	 * 認証済みリスト
	 */
	public List<OAuthForm> authorizedList = new ArrayList<OAuthForm>();
}
