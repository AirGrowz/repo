<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OAuth認証テストページ</title>
		<style type="text/css">
		th, td{
			padding: 3px 5px;
		}
		</style>
	</head>
	<body>

		<div>サービスを登録する</div>
		<form action="${f:url('/oauth/service/create')}" method="POST">
			<table border="1">
				<tr>
					<th>Key</th>
					<th>Value</th>
				</tr>
				<tr>
					<td>サービス名称</td>
					<td><input type="text" name="serviceName"/></td>
				</tr>
				<tr>
					<td>Consumer Key</td>
					<td><input type="text" name="consumerKey"/></td>
				</tr>
				<tr>
					<td>Consumer Secret</td>
					<td><input type="text" name="consumerSecret"/></td>
				</tr>
				<tr>
					<td>Request Token URL</td>
					<td><input type="text" name="requestTokenURL"/></td>
				</tr>
				<tr>
					<td>Authorize URL</td>
					<td><input type="text" name="authorizeURL"/></td>
				</tr>
				<tr>
					<td>Access Token URL</td>
					<td><input type="text" name="accessTokenURL"/></td>
				</tr>
				<tr style="text-align:center;">
					<td colspan="2"><input type="submit" value="サービスを登録する"/></td>
				</tr>
			</table>
		</form>

		<div>登録済みサービス一覧</div>
		<form>
			<table border="1">
				<tr>
					<th>サービス名称</th>
					<th>Consumer Key</th>
					<th>Consumer Secret</th>
					<th>Request Token URL</th>
					<th>Authorize URL</th>
					<th>Access Token URL</th>
				</tr>
			</table>
		</form>

		<div>アクセスコードを取得する</div>
		<form action="${f:url('/oauth/requestToken')}"target="_blank">
			<table border="1">
				<tr>
					<th>Key</th>
					<th>Value</th>
				</tr>
				<tr>
					<td>サービスを選択する</td>
					<td>
						<select name="serviceId">
							<option value="1">eコミマップ</option>
							<option value="2">Twitter</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Callback URL</td>
					<td><input type="text" name="callbackURL"/></td>
				</tr>
				<tr style="text-align:center;">
					<td colspan="2"><input type="submit" value="アクセスコードを取得する"/></td>
				</tr>
			</table>
		</form>

		<div>認証する</div>
		<form>
			<table border="1">
				<tr>
					<th>Key</th>
					<th>Value</th>
				</tr>
				<tr>
					<td>アクセスコード</td>
					<td><input type="text" name="oauth_verifier"/></td>
				</tr>
				<tr style="text-align:center;">
					<td colspan="2"><input type="submit" name="callback" value="認証する"/></td>
				</tr>
			</table>
		</form>

		<div>認証情報一覧</div>
		<form>
			<table border="1">
				<tr>
					<th>サービス名称</th>
					<th>ログインユーザ</th>
				</tr>
			</table>
		</form>

	</body>
</html>