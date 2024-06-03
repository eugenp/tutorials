import React, { useEffect } from 'react';
import authConfig from '../pkceAuthConfig';

const CallbackHandler = ({
  authenticated,
  setAuth,
  userManager,
  userInfo,
  setUserInfo,
}) => {
  useEffect(() => {
    if (authenticated === null) {
      userManager
        .signinRedirectCallback()
        .then((user) => {
          if (user) {
            setAuth(true);
            const access_token = user.access_token;
            fetch(authConfig.userinfo_endpoint, {
              headers: {
                Authorization: `Bearer ${access_token}`,
              },
            })
              .then((response) => response.json())
              .then((userInfo) => {
                console.log(userInfo);
                setUserInfo(userInfo);
              });
          } else {
            setAuth(false);
          }
        })
        .catch((error) => {
          setAuth(false);
        });
    }
  }, [authenticated, userManager, setAuth, setUserInfo]);

  if (authenticated === true && userInfo) {
    return (
      <div>
        <h2>Welcome to Baeldung, {userInfo.sub}</h2>
      </div>
    );
  } else {
    return <div>Loading</div>;
  }
};

export default CallbackHandler;
