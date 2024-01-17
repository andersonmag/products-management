import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ProductsHomeComponent } from './app/page/products-home/products-home.component';
import { LoginComponent } from './app/page/login/login.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'products', component: ProductsHomeComponent},
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
