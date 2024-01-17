import { Component } from '@angular/core';
import { TokenAuthenticationService } from './app/service/token-authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'empiretech-test-front-end-angular';

  constructor(private tokenService: TokenAuthenticationService) { }

  isLogged() {
    return this.tokenService.isLogged();
  }

}
