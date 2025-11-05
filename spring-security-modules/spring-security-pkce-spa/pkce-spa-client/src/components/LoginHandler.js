import { Navigate } from 'react-router-dom';

const LoginHandler = ({ authentication, handleLoginRequest }) => {
  return (
    <div>
      {authentication === null && <div>Loading...</div>}
      {authentication === false && (
        <div>
          <h1>Welcome!</h1>
          <button
            onClick={() => {
              handleLoginRequest();
            }}
          >
            Sign In
          </button>
        </div>
      )}
      {authentication && <Navigate to="/callback" />}
    </div>
  );
};

export default LoginHandler;
