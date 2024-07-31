import { EventHandler } from "react";
import Login from "./login";
import Logout from "./logout";
import { useUserContext } from "@/app/user-context";

interface AuthenticationProperties {
  onLogin: EventHandler<any>;
}

export default function Authentication({ onLogin }: AuthenticationProperties) {
  const user = useUserContext();

  return (
    <span>
      {!user.isAuthenticated ? (
        <Login onLogin={onLogin}></Login>
      ) : (
        <Logout></Logout>
      )}
    </span>
  );
}
