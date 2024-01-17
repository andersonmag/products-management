import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ProductsHomeComponent } from './app/page/products-home/products-home.component';

const routes: Routes = [
  { path: '/products', component: ProductsHomeComponent},
  {  path: '*', redirectTo: 'products'}
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
