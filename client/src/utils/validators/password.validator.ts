import { FormControl, FormGroup } from '@angular/forms';

export function passwordMatchCheckValidator(control: FormGroup): { [s: string]: boolean } {
  if (control.value.newPassword !== control.value.newPasswordConfirm) {
    return { noMatch: true };
  }
  return null;
}

export function passwordStrengthCheckValidator(control: FormControl): { [s: string]: boolean } | null {
  if (!control.value || isStrong(control.value)) {
    return null;
  }
  return { notStrong: true };
}

function isStrong(password: string) {
  return password.length >= 8 && hasMixedCase(password) && hasDigits(password) && hasSpecialCharacters(password)
}

function hasMixedCase(password: string) {
  return /^(?=.*[a-z])(?=.*[A-Z]).+$/.test(password);
}

function hasDigits(password: string) {
  return /^(?=.*\d).+$/.test(password);
}

function hasSpecialCharacters(password: string) {
  return /^(?=.*[@#$%^&+=!?]).+$/.test(password);
}
