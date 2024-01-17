import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenAuthenticationService {

private token!: string;

  constructor() { }

  getToken(): any {
    return localStorage.getItem('token');
  }

}
