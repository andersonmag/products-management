import { Component } from '@angular/core';
import { TokenAuthenticationService } from '../../service/token-authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private tokenService: TokenAuthenticationService, private router: Router) {}

  logout() {
    console.log("1ada")
    this.tokenService.removeToken();
    this.router.navigate(['/login']).then(() => window.location.reload());
  }

}
