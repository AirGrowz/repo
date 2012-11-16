package jp.go.bosai.saigaitask.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.go.bosai.saigaitask.form.oauth.ServiceForm;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class LoginDataDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登録済みサービスリスト
	 */
	public List<ServiceForm> serviceList = new ArrayList<ServiceForm>();

	public List<OAuthDto> oauthDtoList = new ArrayList<OAuthDto>();

}
