import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Product } from '../../model/product';
import { ProductsService } from '../../service/products.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { TokenAuthenticationService } from '../../service/token-authentication.service';

@Component({
  selector: 'app-products-home',
  templateUrl: './products-home.component.html',
  styleUrls: ['./products-home.component.css']
})
export class ProductsHomeComponent implements OnInit, OnDestroy{

  products!: Product[];
  private readonly destroy$ : Subject<void> = new Subject<void>();

  constructor(private productService : ProductsService,
    private tokenService: TokenAuthenticationService,
    private router: Router)  { }

  ngOnInit(): void {
    if(this.tokenService.isLogged()) {
      this.router.navigateByUrl('/')
      return
    }

    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts()
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: data => {
        data && (this.products = data);
      }, error: (err) => {
        console.log(err);
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
