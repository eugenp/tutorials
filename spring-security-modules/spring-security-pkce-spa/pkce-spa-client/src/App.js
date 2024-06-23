import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './components/LoginHandler';
import CallbackHandler from './components/CallbackHandler';
import pkceAuthConfig from './pkceAuthConfig';
import { UserManager, WebStorageStateStore } from 'oidc-client-ts';

function App() {
    const [authenticated, setAuthenticated] = useState(null);
    const [userInfo, setUserInfo] = useState(null);

    const userManager = new UserManager({
        userStore: new WebStorageStateStore({ store: window.localStorage }),
        ...pkceAuthConfig,
    });

    function doAuthorize() {
        userManager.signinRedirect({state: '6c2a55953db34a86b876e9e40ac2a202',});
    }

    useEffect(() => {
        userManager.getUser().then((user) => {
            if (user) {
                setAuthenticated(true);
            } 
            else {
                setAuthenticated(false);
            }
      });
    }, [userManager]);

    return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Login authentication={authenticated} handleLoginRequest={doAuthorize}/>}/>
              <Route path="/callback"
                  element={<CallbackHandler
                      authenticated={authenticated}
                      setAuth={setAuthenticated}
                      userManager={userManager}
                      userInfo={userInfo}
                      setUserInfo={setUserInfo}/>}/>
          </Routes>
      </BrowserRouter>
    );
}

export default App;
