<%@ page import="org.eclipse.microprofile.config.Config" %>
<%@ page import="org.eclipse.microprofile.config.ConfigProvider" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>OAuth2 Client</title>
    <style>
        body {
            margin: 0px;
        }

        input[type=text], input[type=password] {
            width: 75%;
            padding: 4px 0px;
            display: inline-block;
            border: 1px solid #502bcc;
            box-sizing: border-box;
        }

        .container-error {
            padding: 16px;
            border: 1px solid #cc102a;
            background: #f50307;
            width: 75%;
            margin-left: 25px;
            margin-bottom: 25px;
        }

        .container {
            padding: 16px;
            border: 1px solid #130ecc;
            background: #eaecf5;
            width: 75%;
            margin-left: 25px;
            margin-bottom: 25px;
        }
    </style>
</head>

<body>

<%
    Config config1 = ConfigProvider.getConfig();
%>

<div class="container-error" style="visibility: <%=request.getAttribute("error")!=null?"visible":"hidden"%>">
    <span>Error : ${error}</span>
</div>

<div class="container">
    <span><h4>OAuth2 Authorization Server</h4></span>
    <hr>
    <p>Client Registration :</p>
    <ul>
        <li>client_id: <%=config1.getValue("client.clientId", String.class)%>
        </li>
        <li>client_secret: <%=config1.getValue("client.clientSecret", String.class)%>
        </li>
        <li>redirect_uri: <%=config1.getValue("client.redirectUri", String.class)%>
        </li>
        <li>scope: <%=config1.getValue("client.scope", String.class)%>
        </li>
    </ul>
    <p>Authorization Server :</p>
    <ul>
        <li>authorization_uri: <%=config1.getValue("provider.authorizationUri", String.class)%>
        </li>
        <li>token_uri: <%=config1.getValue("provider.tokenUri", String.class)%>
        </li>
    </ul>
</div>
<div class="container">
    <span><h4>OAuth2 Client</h4></span>
    <hr>
    <p>Token Request :</p>
    <ul>
        <li><a class="btn btn-primary" href="/authorize">Get Access Token</a></li>
    </ul>

    <p>Token Response:</p>
    <ul>
        <li>access_token: ${tokenResponse.getString("access_token")}</li>
        <li>scope: ${tokenResponse.getString("scope")}</li>
        <li>Expires in (s): ${tokenResponse.getInt("expires_in")}</li>
        <li>refresh_token: ${tokenResponse.getString("refresh_token")}</li>
    </ul>
</div>

<div class="container">
    <span><h4>Refresh Token</h4></span>
    <hr>
    <ul>
        <li><a href="refreshtoken">Refresh token (original scope)</a></li>
        <li><a href="refreshtoken?scope=resource.read">Refresh token (scope: resource.read)</a></li>
        <li><a href="refreshtoken?scope=resource.write">Refresh token (scope: resource.write)</a></li>
    </ul>
</div>

<div class="container">
    <span><h4>OAuth2 Resource Server Call</h4></span>
    <hr>
    <ul>
        <li><a href="downstream?action=read">Read Protected Resource</a></li>
        <li><a href="downstream?action=write">Write Protected Resource</a></li>
    </ul>
</div>

</body>
</html>