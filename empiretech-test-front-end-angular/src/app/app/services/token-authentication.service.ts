import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenAuthenticationService {

  TOKEN_HEADER: string = 'accessToken';

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_HEADER, token);
  }

  removeToken() {
    localStorage.removeItem(this.TOKEN_HEADER)
  }

  getToken(): any {
    return localStorage.getItem(this.TOKEN_HEADER);
  }

  isLogged(): any {
    return localStorage.getItem(this.TOKEN_HEADER) !== null;
  }
}
