"use client";

import { useUserContext } from "@/app/user-context";
import axios from "axios";
import { usePathname } from "next/navigation";
import { EventHandler, FormEvent, useEffect, useRef, useState } from "react";

enum LoginExperience {
  DEFAULT,
  IFRAME,
}

interface LoginOptionDto {
  label: string;
  loginUri: string;
  isSameAuthority: boolean;
}
async function getLoginOptions(): Promise<Array<LoginOptionDto>> {
  const response = await axios.get<Array<LoginOptionDto>>("/bff/login-options");
  return response.data;
}

interface LoginProperties {
  onLogin: EventHandler<any>;
}
export default function Login({ onLogin }: LoginProperties) {
  const user = useUserContext();
  const [loginUri, setLoginUri] = useState("");
  const [selectedLoginExperience, setSelectedLoginExperience] = useState(
    LoginExperience.DEFAULT
  );
  const [isLoginModalDisplayed, setIsLoginModalDisplayed] = useState(false);
  const [isIframeLoginPossible, setIframeLoginPossible] = useState(false);
  const currentPath = usePathname();
  const iframeRef = useRef<HTMLIFrameElement>(null);

  useEffect(() => {
    const iframe = iframeRef.current;
    iframe?.addEventListener("load", onIframeLoad);

    return () => {
      fetchLoginOptions();
      iframe?.removeEventListener("load", onIframeLoad);
    };
  });

  async function fetchLoginOptions() {
    const loginOpts = await getLoginOptions();
    if (loginOpts?.length < 1 || !loginOpts[0].loginUri) {
      setLoginUri("");
      setIframeLoginPossible(false);
    } else {
      setLoginUri(loginOpts[0].loginUri);
      setIframeLoginPossible(true);
    }
  }

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!loginUri) {
      return;
    }
    const url = new URL(loginUri);

    url.searchParams.append(
      "post_login_success_uri",
      `${process.env.NEXT_PUBLIC_BASE_URI}${currentPath}`
    );
    url.searchParams.append(
      "post_login_failure_uri",
      `${process.env.NEXT_PUBLIC_BASE_URI}/login-error`
    );
    const loginUrl = url.toString();
    if (
      +selectedLoginExperience === +LoginExperience.IFRAME &&
      iframeRef.current
    ) {
      const iframe = iframeRef.current;
      if (iframe) {
        iframe.src = loginUrl;
        setIsLoginModalDisplayed(true);
      }
    } else {
      window.location.href = loginUrl;
    }
  }

  function onIframeLoad() {
    if (isLoginModalDisplayed) {
      onLogin({});
    }
  }

  return (
    <span>
      <form onSubmit={onSubmit}>
        {isIframeLoginPossible && (
          <select
            value={selectedLoginExperience}
            onChange={(e) => {
              setSelectedLoginExperience(
                +e?.target?.value === +LoginExperience.IFRAME
                  ? LoginExperience.IFRAME
                  : LoginExperience.DEFAULT
              );
            }}
          >
            <option
              value={LoginExperience.IFRAME}
              hidden={!isIframeLoginPossible}
            >
              iframe
            </option>
            <option value={LoginExperience.DEFAULT}>default</option>
          </select>
        )}
        <button disabled={user.isAuthenticated} type="submit">
          Login
        </button>
      </form>
      <div
        className={
          !user.isAuthenticated && isLoginModalDisplayed
            ? "modal-overlay"
            : "hidden"
        }
        onClick={() => setIsLoginModalDisplayed(false)}
      >
        <div></div>
        <div className="modal">
          <div className="flex">
            <span className="ml-auto">
              <button onClick={() => setIsLoginModalDisplayed(false)}>X</button>
            </span>
          </div>
          <iframe ref={iframeRef}></iframe>
        </div>
      </div>
    </span>
  );
}
