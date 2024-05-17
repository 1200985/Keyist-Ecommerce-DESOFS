import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailVerificationComponent } from './email-verification.component';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountService } from 'src/app/services/account.service';
import { provideMockStore } from '@ngrx/store/testing';

describe('EmailVerificationComponent', () => {
  let component: EmailVerificationComponent;
  let fixture: ComponentFixture<EmailVerificationComponent>;
  let accountServiceMock: jasmine.SpyObj<AccountService> = jasmine.createSpyObj(
    'AccountServiceSpy',
    ['verifyEmail']
  );

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EmailVerificationComponent],
      providers: [
        provideMockStore(),
        { provide: AccountService, useValue: accountServiceMock },
      ],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
