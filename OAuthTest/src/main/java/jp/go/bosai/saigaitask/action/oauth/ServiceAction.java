package jp.go.bosai.saigaitask.action.oauth;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import jp.go.bosai.saigaitask.dto.oauth.OAuthDto;
import jp.go.bosai.saigaitask.form.oauth.OAuthForm;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * OAuth認証Webサービスに関するアクションクラスです.
 */
public class ServiceAction {

	@ActionForm
	@Resource protected OAuthForm oAuthForm;

	@Resource protected ServletRequest request;
	@Resource protected OAuthDto oAuthDto;

	/**
	 * 新規作成します.
	 * @return String
	 */
	@Execute(validator = false, redirect = true)
	public String create() {
		oAuthDto.serviceList.add(oAuthForm);
		return "/oauth";
	}

	@Execute(validator = false, redirect = true, urlPattern="delete/{serviceId}")
	public String delete() {
		oAuthDto.serviceList.remove(oAuthForm.serviceId.intValue());
		return "/oauth";
	}
}
