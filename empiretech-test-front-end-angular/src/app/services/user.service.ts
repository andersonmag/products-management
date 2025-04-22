import {HttpClient} from '@angular/common/http';
import {User} from '../models/user';
import {TokenAuthenticationService} from './token-authentication.service';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private tokenService: TokenAuthenticationService,
                private httpClient: HttpClient,
                private router: Router) {
    }

    authenticate(user: User): Observable<any> {
        const json = JSON.stringify(user);
        return this.httpClient.post<any>(`${environment.apiUrl}/login`, json,
            {headers: {'Content-Type': 'application/json'}, responseType: 'text' as 'json'});
    }
}
