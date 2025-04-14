import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsHomeComponent } from './app/components/home/products-home.component';
import { LoginComponent } from './app/components/login/login.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductsHomeComponent },
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: '*', redirectTo: 'products' },
]

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
