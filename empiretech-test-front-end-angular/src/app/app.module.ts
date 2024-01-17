import { MatTableModule } from '@angular/material/table';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule} from '@angular/material/button';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductsHomeComponent } from './app/page/products-home/products-home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './app/page/header/header.component';
import { FooterComponent } from './app/page/footer/footer.component';
import { LoginComponent } from './app/page/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageErrorFormComponent } from './app/page/util/message-error-form/message-error-form.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductsHomeComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    MessageErrorFormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatTableModule,
    MatListModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
