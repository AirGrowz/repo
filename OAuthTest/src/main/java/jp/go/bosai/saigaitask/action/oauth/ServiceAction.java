package jp.go.bosai.saigaitask.action.oauth;

import javax.annotation.Resource;

import jp.go.bosai.saigaitask.dto.LoginDataDto;
import jp.go.bosai.saigaitask.form.oauth.ServiceForm;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * OAuth認証Webサービスに関するアクションクラスです.
 */
public class ServiceAction {

	@ActionForm
	@Resource protected ServiceForm serviceForm;

	@Resource protected LoginDataDto loginDataDto;

	/**
	 * 新規作成します.
	 * @return String
	 */
	@Execute(validator = false, redirect = true)
	public String create() {
		loginDataDto.serviceList.add(serviceForm);
		return "/oauth";
	}

}
