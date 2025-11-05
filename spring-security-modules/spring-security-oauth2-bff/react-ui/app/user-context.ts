import { User } from "./lib/auth/user.service";
import { createContext, useContext } from "react";


export const UserContext = createContext(User.ANONYMOUS);
export function useUserContext() {
  return useContext(UserContext);
}