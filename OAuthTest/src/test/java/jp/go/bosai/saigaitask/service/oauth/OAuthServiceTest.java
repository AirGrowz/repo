package jp.go.bosai.saigaitask.service.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.go.bosai.saigaitask.service.oauth.OAuthService;
import jp.go.bosai.saigaitask.service.oauth.OAuthServiceProviderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.StringUtil;

public class OAuthServiceTest extends S2TestCase {

	private OAuthService oAuthService;
	private EcommapAPIService ecommapAPIService;

	/**
	 * すべてのテストメソッドの前に呼び出されます.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include("app.dicon");
	}

	/**
	 * すべてのテストメソッドの後に呼び出されます.
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * eコミにOAuth認証できるかテストします.
	 */
	public void testEcomOAuth() throws IOException, JSONException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		DataSet dataset = this.readXls("OAuthServiceTest.xls");
		DataTable oauthTable = dataset.getTable("oauth");
		DataRow row = oauthTable.getRow(0);

		String key, value;
		// consumer key
		key = "consumer_key";
		value = (String) row.getValue(key);
		if(StringUtil.isEmpty(value)) {
			System.out.print("Input "+key+": ");
			value = reader.readLine();
		}
		System.out.println(key+": "+value);
		oAuthService.consumerKey = value;

		// consumer secret
		key = "consumer_secret";
		value = (String) row.getValue(key);
		if(StringUtil.isEmpty(value)) {
			System.out.print("Input "+key+": ");
			value = reader.readLine();
		}
		System.out.println(key+": "+value);
		oAuthService.consumerSecret = value;

		// callback url
		key = "callback_url";
		value = (String) row.getValue(key);
		if(StringUtil.isEmpty(value)) {
			System.out.print("Input "+key+": ");
			value = reader.readLine();
		}
		System.out.println(key+": "+value);
		oAuthService.callbackUrl = value;

		// ecom server
		key = "ecom_server";
		value = (String) row.getValue(key);
		if(StringUtil.isEmpty(value)) {
			System.out.print("Input "+key+": ");
			value = reader.readLine();
		}
		System.out.println(key+": "+value);
		String ecomServer = value;
		oAuthService.provider = OAuthServiceProviderFactory.getEcomMapProvider(ecomServer);

		// check ecommap api version;
		ecommapAPIService.ecomServer = ecomServer;
		boolean success = oAuthService.api("GET", ecommapAPIService.getVersion(), null, oAuthService.accessor.accessToken, oAuthService.accessor.tokenSecret);
		if(success) {
			String jsonStr = oAuthService.apiResponse.readBodyAsString();
			// print json
			JSONObject json = new JSONObject(jsonStr);
			String[] names = JSONObject.getNames(json);
			for(String name : names) {
				System.out.println(name+": "+json.get(name));
			}

			// get unauthorized request token
			String callbackUrl = oAuthService.getRequestToken();

			// get verifier
			System.out.println("Access this URL and get OAuth verifier: "+callbackUrl);
			System.out.print("Input OAuth verifier: ");
			String verifier = reader.readLine();

			// get access token
			oAuthService.getAccessToken(verifier, oAuthService.accessor.requestToken, oAuthService.accessor.tokenSecret);

			// api test
			oAuthService.api("GET", ecommapAPIService.getVersion(), null, oAuthService.accessor.accessToken, oAuthService.accessor.tokenSecret);
			oAuthService.api("GET", ecommapAPIService.getWhoAmI(), null, oAuthService.accessor.accessToken, oAuthService.accessor.tokenSecret);
			oAuthService.api("GET", ecommapAPIService.getUser(1, 1), null, oAuthService.accessor.accessToken, oAuthService.accessor.tokenSecret);
			oAuthService.api("GET", ecommapAPIService.getGroup(1, 1), null, oAuthService.accessor.accessToken, oAuthService.accessor.tokenSecret);
		}
	}

	/**
	 * twitterにOAuth認証できるかテストします.
	 */
	public void testTwitterOAuth() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// config
		config();
		oAuthService.provider = OAuthServiceProviderFactory.getTwitterProvider();

		// get unauthorized request token
		String callbackUrl = oAuthService.getRequestToken();

		// get verifier
		System.out.println("Access this URL and get OAuth verifier: "+callbackUrl);
		System.out.print("Input OAuth verifier: ");
		String verifier = reader.readLine();

		// get access token
		oAuthService.getAccessToken(verifier, oAuthService.accessor.requestToken, oAuthService.accessor.tokenSecret);

		// api test
		oAuthService.api("GET", "https://api.twitter.com/1.1/statuses/mentions_timeline.json", null, oAuthService.accessor.accessToken, oAuthService.accessor.tokenSecret);
	}

	protected void config() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// consumer key
		System.out.print("Input consumer key: ");
		oAuthService.consumerKey = reader.readLine();

		// consumer secret
		System.out.print("Input consumer secret: ");
		oAuthService.consumerSecret = reader.readLine();

		// callback url
		System.out.print("Input callback url(optional): ");
		oAuthService.callbackUrl = reader.readLine();
	}
}
