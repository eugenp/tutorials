"use client";

import axios from "axios";
import { Dispatch, SetStateAction } from "react";
import { Subscription, interval } from "rxjs";

interface UserinfoDto {
  username: string;
  email: string;
  roles: string[];
  exp: number;
}

export class User {
  static readonly ANONYMOUS = new User("", "", []);

  constructor(
    readonly name: string,
    readonly email: string,
    readonly roles: string[]
  ) {}

  get isAuthenticated(): boolean {
    return !!this.name;
  }

  hasAnyRole(...roles: string[]): boolean {
    for (const r of roles) {
      if (this.roles.includes(r)) {
        return true;
      }
    }
    return false;
  }
}

export class UserService {
  private refreshSub?: Subscription;

  constructor(user: User, setUser: Dispatch<SetStateAction<User>>) {
    this.refresh(user, setUser);
  }

  async refresh(
    user: User,
    setUser: Dispatch<SetStateAction<User>>
  ): Promise<void> {
    this.refreshSub?.unsubscribe();
    const response = await axios.get<UserinfoDto>("/bff/api/me");
    if (
      response.data.username !== user.name ||
      response.data.email !== user.email ||
      (response.data.roles || []).toString() !== user.roles.toString()
    ) {
      setUser(
        response.data.username
          ? new User(
              response.data.username || "",
              response.data.email || "",
              response.data.roles || []
            )
          : User.ANONYMOUS
      );
    }
    if (!!response.data.exp) {
      const now = Date.now();
      const delay = (1000 * response.data.exp - now) * 0.8;
      if (delay > 2000) {
        this.refreshSub = interval(delay).subscribe(() =>
          this.refresh(user, setUser)
        );
      }
    }
  }
}
