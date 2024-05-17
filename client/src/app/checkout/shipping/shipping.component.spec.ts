import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShippingComponent } from './shipping.component';
import { AccountService } from 'src/app/services/account.service';
import { RouterTestingModule } from '@angular/router/testing';
import { provideMockStore } from '@ngrx/store/testing';

describe('ShippingComponent', () => {
  let component: ShippingComponent;
  let fixture: ComponentFixture<ShippingComponent>;
  let accountServiceMock: jasmine.SpyObj<AccountService> = jasmine.createSpyObj(
    'AccountServiceSpy',
    ['getUser']
  );

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ShippingComponent],
      providers: [
        { provide: AccountService, useValue: accountServiceMock },
        provideMockStore(),
      ],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShippingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
