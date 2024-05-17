import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddressComponent } from './address.component';
import { AccountService } from 'src/app/services/account.service';

describe('AddressComponent', () => {
  let component: AddressComponent;
  let fixture: ComponentFixture<AddressComponent>;
  let accountServiceMock: jasmine.SpyObj<AccountService> = jasmine.createSpyObj(
    'AccountServiceSpy',
    ['getUser']
  );

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [AddressComponent],
      providers: [{ provide: AccountService, useValue: accountServiceMock }],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
