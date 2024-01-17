import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenAuthenticationService {

  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): any {
    return localStorage.getItem('token');
  }

  isLogged(): boolean {
    return localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined;
  }
}
