INSERT INTO `users` (`user_id`, `password`, `roles`, `scopes`) VALUES ('appuser', 'appusersecret', 'USER', 'resource.read resource.write');

INSERT INTO `clients` (`client_id`, `client_secret`, `redirect_uri`,`scope`,`authorized_grant_types`) VALUES ('webappclient', 'webappclientsecret', 'http://localhost:9180/callback', 'resource.read resource.write', 'authorization_code refresh_token');