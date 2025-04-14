import { MatTableModule } from '@angular/material/table';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule} from '@angular/material/button';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductsHomeComponent } from './app/components/home/products-home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './app/components/shared/header/header.component';
import { FooterComponent } from './app/components/shared/footer/footer.component';
import { LoginComponent } from './app/components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageErrorFormComponent } from './app/components/shared/message-error-form/message-error-form.component';

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
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
