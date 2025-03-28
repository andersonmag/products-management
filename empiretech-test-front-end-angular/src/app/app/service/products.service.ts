import { TokenAuthenticationService } from './token-authentication.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private REQUEST_URL: string  = "http://localhost:8080/api/products";

  constructor(private httpClient: HttpClient, private tokenService : TokenAuthenticationService) { }

  getProducts(): Observable<any> {
    const token = this.tokenService.getToken();
    return this.httpClient.get<any>(this.REQUEST_URL, { headers: {'Authorization': token}   });
  }
}
