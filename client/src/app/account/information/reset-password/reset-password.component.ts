import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../services/account.service';
import * as PasswordValidators from '../../../../utils/validators/password.validator';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as BlankValidators from '../../../../utils/validators/blank.validator';
import { throwError } from 'rxjs';
import { catchError, take } from 'rxjs/operators';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm: FormGroup;
  innerLoading = false;
  showPassword = false;
  showPasswordConfirm = false;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.resetPasswordForm = new FormGroup({
      oldPassword: new FormControl(null, [
        Validators.required,
        BlankValidators.checkIfBlankValidator,
        Validators.minLength(12),
        Validators.maxLength(128),
      ]),
      newPasswordGroup: new FormGroup(
        {
          newPassword: new FormControl(null, [
            Validators.required,
            BlankValidators.checkIfBlankValidator,
            Validators.minLength(12),
            Validators.maxLength(128),
            PasswordValidators.passwordStrengthCheckValidator
          ]),
          newPasswordConfirm: new FormControl(null, [
            Validators.required,
            BlankValidators.checkIfBlankValidator,
            Validators.minLength(12),
            Validators.maxLength(128),
          ]),
        },
        PasswordValidators.passwordMatchCheckValidator
      ),
    });
  }

  togglePasswordVisibility(show: boolean): void {
    const passwordInput = document.getElementById('newPassword') as HTMLInputElement;
    passwordInput.type = show ? 'text' : 'password';
    this.showPassword = show;
  }

  togglePasswordConfirmVisibility(show: boolean): void {
    const passwordInput = document.getElementById('newPasswordConfirm') as HTMLInputElement;
    passwordInput.type = show ? 'text' : 'password';
    this.showPasswordConfirm = show;
  }

  onSubmitResetPassword() {
    this.innerLoading = true;
    this.accountService
      .resetPassword(
        this.resetPasswordForm.value.oldPassword,
        this.resetPasswordForm.value.newPasswordGroup.newPassword,
        this.resetPasswordForm.value.newPasswordGroup.newPasswordConfirm
      )
      .pipe(
        take(1),
        catchError((error) => {
          this.innerLoading = false;
          alert('Error resetting password. Please try again.');
          return throwError(error);
        })
      )
      .subscribe((res) => {
        this.innerLoading = false;
        alert('Success! Your password has been changed.');
        this.resetPasswordForm.reset();
      });
  }
}
