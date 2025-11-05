const pkceAuthConfig = {
  authority: 'http://127.0.0.1:9000/',
  client_id: 'public-client',
  redirect_uri: 'http://127.0.0.1:3000/callback',
  response_type: 'code',
  scope: 'openid profile email',
  post_logout_redirect_uri: 'http://127.0.0.1:3000/',
  userinfo_endpoint: 'http://127.0.0.1:9000/userinfo',
  response_mode: 'query',
  code_challenge_method: 'S256',
};

export default pkceAuthConfig;
