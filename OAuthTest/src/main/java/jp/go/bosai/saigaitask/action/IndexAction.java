package jp.go.bosai.saigaitask.action;

import org.seasar.struts.annotation.Execute;

public class IndexAction {

	@Execute(validator = false, redirect = true)
	public String index() {
		return "/oauth";
	}
}
