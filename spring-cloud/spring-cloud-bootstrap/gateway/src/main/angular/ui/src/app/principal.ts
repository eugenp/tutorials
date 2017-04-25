/**
 * Created by tschi on 4/16/2017.
 */
export class Principal {
  public authenticated: boolean;
  public authorities: Authority[] = [];
  public credentials: any;

  constructor(authenticated: boolean, authorities: any[], credentials: any) {
    this.authenticated = authenticated;
    authorities.map(auth => this.authorities.push(new Authority(auth.authority)))
    this.credentials = credentials;
  }

  isAdmin() {
    return this.authorities.some((auth: Authority) => auth.authority.indexOf('ADMIN') > -1)
  }
}

export class Authority {
  public authority: String;

  constructor(authority: String) {
    this.authority = authority;
  }
}
