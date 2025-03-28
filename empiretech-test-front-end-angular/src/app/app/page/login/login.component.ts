import {User} from '../../model/user';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TokenAuthenticationService} from '../../service/token-authentication.service';
import {Subject, takeUntil} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  userLogin: User = new User();
  errors!: string[];
  formLogin!: FormGroup;
  private readonly destroy$: Subject<void> = new Subject<void>();

  constructor(private userService: UserService,
              private tokenService: TokenAuthenticationService,
              private router: Router,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    if (this.tokenService.isLogged()) {
      this.router.navigateByUrl('/')
      return
    }

    this.formLogin = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    }, {updateOn: 'blur'})
  }

  autenticate() {
    this.userLogin.username = this.formLogin.get('username')?.value
    this.userLogin.password = this.formLogin.get('password')?.value

    if(!this.validFields())
      return;

    this.userService.authenticate(this.userLogin)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: token => {
            this.tokenService.setToken(token);
            this.router.navigate(['/products']).then(() => window.location.reload());
          }, error: (err) => {
            console.log(err);
          }
        });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  validFields(): boolean {
    if (this.userLogin.username === '') {
      document.getElementById('errorUsername')!.style.display = 'block';
      return false;
    }

    if (this.userLogin.password === '') {
      document.getElementById('errorPassword')!.style.display = 'block';
      return false;
    }

    return true;
  }
}
