import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordForgotVerificationComponent } from './password-forgot-verification.component';
import { provideMockStore } from '@ngrx/store/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountService } from 'src/app/services/account.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('PasswordForgotVerificationComponent', () => {
  let component: PasswordForgotVerificationComponent;
  let fixture: ComponentFixture<PasswordForgotVerificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PasswordForgotVerificationComponent],
      providers: [provideMockStore(), AccountService],
      imports: [RouterTestingModule, HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordForgotVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
