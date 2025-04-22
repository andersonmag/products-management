import {TokenAuthenticationService} from './token-authentication.service';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";
import {Product} from "../models/product";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private httpClient: HttpClient, private tokenService: TokenAuthenticationService) {
  }

  getProducts(): Observable<any> {
    const token = this.tokenService.getToken();
    return this.httpClient.get<Product[]>(`${environment.apiUrl}/products`, {headers: {'Authorization': token}});
  }
}
