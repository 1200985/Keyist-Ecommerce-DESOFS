import { AuthState } from './../../store/auth/auth.reducer';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import * as fromApp from '../../store/app.reducers';
import * as AuthActions from '../../store/auth/auth.actions';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {

  signInForm: FormGroup;
  emailPattern = '^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$';
  showPassword = false;

  authState: Observable<AuthState>;


  constructor(private store: Store<fromApp.AppState>) {
  }

  ngOnInit() {
    this.signInForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.pattern(this.emailPattern)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(12), Validators.maxLength(128)]),
    });



    this.authState = this.store.select('auth');
  }

  togglePasswordVisibility(show: boolean): void {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = show ? 'text' : 'password';
    this.showPassword = show;
  }

  onSubmitted() {
    this.store.dispatch(new AuthActions.SignIn({
      email: this.signInForm.value.email,
      password: this.signInForm.value.password
    }));
  }

}
