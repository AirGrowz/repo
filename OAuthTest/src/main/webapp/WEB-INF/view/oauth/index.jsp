<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OAuth認証テストページ</title>
		<style type="text/css">
		th, td {
			padding: 3px 5px;
		}
		form {
			margin: 0px;
		}
		select, input[type="text"] {
			width: 200px;
		}
		.panel {
			border: 1px solid gray;
			width: 100%;
			overflow: auto;
			margin-bottom: 10px;
		}
		.panel-inner {
			padding: 5px;
		}
		</style>
	</head>
	<body>

		<div>サービス情報</div>
		<div class="panel">
			<div class="panel-inner">
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

				<br/>

				<div>登録済みサービス一覧</div>
				<form>
					<table border="1">
						<tr>
							<th>No.</th>
							<th>サービス名称</th>
							<th>Consumer Key</th>
							<th>Consumer Secret</th>
							<th>Request Token URL</th>
							<th>Authorize URL</th>
							<th>Access Token URL</th>
							<th>Delete</th>
						</tr>
						<c:forEach var="service" items="${oAuthDto.serviceList}" varStatus="status">
							<tr>
								<td>${status.index}</td>
								<td>${service.serviceName}</td>
								<td>${service.consumerKey}</td>
								<td>${service.consumerSecret}</td>
								<td>${service.requestTokenURL}</td>
								<td>${service.authorizeURL}</td>
								<td>${service.accessTokenURL}</td>
								<td><input type="button" value="削除" onclick="location.href='service/delete/${status.index}';"/></td>
							</tr>
						</c:forEach>
					</table>
				</form>
			</div>
		</div>

		<div>OAuth認証</div>
		<div class="panel">
			<div class="panel-inner">
				<div>アクセスコードを取得する</div>
				<form action="${f:url('/oauth/requestRequestToken')}" target="_blank">
					<table border="1">
						<tr>
							<th>Key</th>
							<th>Value</th>
						</tr>
						<tr>
							<td>サービスを選択する</td>
							<td>
								<select name="serviceId">
									<c:forEach var="service" items="${oAuthDto.serviceList}" varStatus="status">
										<option value="${status.index}">${service.serviceName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Callback URL(optional)</td>
							<td><input type="text" name="callbackURL"/></td>
						</tr>
						<tr style="text-align:center;">
							<td colspan="2"><input type="submit" value="アクセスコードを取得する"/></td>
						</tr>
					</table>
				</form>

				<br/>

				<div>認証する</div>
				<form action="${f:url('/oauth/requestAccessToken')}">
					<table border="1">
						<tr>
							<th>Key</th>
							<th>Value</th>
						</tr>
						<c:if test="${oAuthDto.oAuthForm!=null}">
						<tr>
							<td>サービス名称</td>
							<td>${oAuthDto.oAuthForm.serviceName}</td>
						</tr>
						</c:if>
						<tr>
							<td>アクセスコード</td>
							<td><input type="text" name="oauth_verifier" ${oAuthDto.oAuthForm==null?"disabled":""}/></td>
						</tr>
						<tr style="text-align:center;">
							<td colspan="2"><input type="submit" name="callback" value="認証する" ${oAuthDto.oAuthForm==null?"disabled":""}/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div>認証情報</div>
		<div class="panel">
			<div class="panel-inner">
				<div>認証情報一覧</div>
				<form>
					<table border="1">
						<tr>
							<th>No.</th>
							<th>サービス名称</th>
							<th>Consumer Key</th>
							<th>Consumer Secret</th>
							<th>Access Token</th>
							<th>Token Secret</th>
							<th>Request Token URL</th>
							<th>Authorize URL</th>
							<th>Access Token URL</th>
							<th>Delete</th>
						</tr>
						<c:forEach var="service" items="${oAuthDto.authorizedList}" varStatus="status">
							<tr>
								<td>${status.index}</td>
								<td>${service.serviceName}</td>
								<td>${service.consumerKey}</td>
								<td>${service.consumerSecret}</td>
								<td>${service.accessToken}</td>
								<td>${service.tokenSecret}</td>
								<td>${service.requestTokenURL}</td>
								<td>${service.authorizeURL}</td>
								<td>${service.accessTokenURL}</td>
								<td><input type="button" value="削除" onclick="location.href='authorized/delete/${status.index}';"/></td>
							</tr>
						</c:forEach>
					</table>
				</form>
			</div>
		</div>

		<div>API利用</div>
		<div class="panel">
			<div class="panel-inner">
				<div>APIをリクエストする</div>
				<form action="${f:url('/oauth/api')}" method="POST" target="_blank">
					<table border="1">
						<tr>
							<th>Key</th>
							<th>Value</th>
						</tr>
						<tr>
							<td>認証情報を選択する</td>
							<td>
								<select name="oauthId">
									<c:forEach var="service" items="${oAuthDto.authorizedList}" varStatus="status">
										<option value="${status.index}">${service.serviceName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>HTTP method</td>
							<td><input type="text" name="apiMethod" value="GET"/></td>
						</tr>
						<tr>
							<td>API URL</td>
							<td><input type="text" name="apiURL"/></td>
						</tr>
						<tr>
							<td>Query</td>
							<td><input type="text" name="apiQuery"/></td>
						</tr>
						<tr style="text-align:center;">
							<td colspan="2"><input type="submit" value="APIをリクエストする" ${fn:length(oAuthDto.authorizedList)==0?"disabled":""}/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>