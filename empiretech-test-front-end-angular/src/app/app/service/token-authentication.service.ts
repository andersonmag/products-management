import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenAuthenticationService {

  TOKEN_NAME: string = 'token';

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_NAME, token);
  }

  removeToken() {
    localStorage.removeItem(this.TOKEN_NAME)
  }

  getToken(): any {
    return localStorage.getItem(this.TOKEN_NAME);
  }

  isLogged(): any {
    return localStorage.getItem(this.TOKEN_NAME) !== null;
  }
}
