import { Subject, takeUntil } from 'rxjs';
import { Product } from '../../model/product';
import { ProductsService } from '../../service/products.service';
import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-products-home',
  templateUrl: './products-home.component.html',
  styleUrls: ['./products-home.component.css']
})
export class ProductsHomeComponent implements OnInit, OnDestroy{

  products!: Product[];
  private readonly destroy$ : Subject<void> = new Subject<void>();

  constructor(private productService : ProductsService) { }

  ngOnInit(): void {
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
