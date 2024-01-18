import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { TokenAuthenticationService } from './token-authentication.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

private REQUEST_URL: string = "http://localhost:8080/api/login";

  constructor(private tokenService : TokenAuthenticationService,
    private httpClient: HttpClient,
    private router: Router) { }

  authenticate(user: User, tenantSelected: string): Observable<any> {
    const json = JSON.stringify(user);
    return this.httpClient.post<any>(this.REQUEST_URL, json,
      { headers: { 'X-TenantyID': tenantSelected, 'Content-Type': 'application/json' }, responseType: 'text' as 'json'});
  }
}
