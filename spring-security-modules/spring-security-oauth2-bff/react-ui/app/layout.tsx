"use client";

import { Inter } from "next/font/google";
import { useState } from "react";
import "./globals.css";
import Authentication from "./lib/auth/authentication.component";
import { User, UserService } from "./lib/auth/user.service";
import { UserContext } from "./user-context";

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const [user, setUser] = useState(User.ANONYMOUS);
  const userService = new UserService(user, setUser);

  return (
    <html lang="en">
      <body className={inter.className}>
        <UserContext.Provider value={user}>
          <div className="flex">
            <div className="m-auto"></div>
            <h1 className="mt-2">React UI</h1>
            <div className="m-auto"></div>
            <div className="mt-2">
              <Authentication
                onLogin={() => userService.refresh(user, setUser)}
              ></Authentication>
            </div>
            <div className="mr-3"></div>
          </div>
          {children}
        </UserContext.Provider>
      </body>
    </html>
  );
}
