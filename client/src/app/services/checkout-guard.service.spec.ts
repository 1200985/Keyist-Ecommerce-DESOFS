import { inject, TestBed } from '@angular/core/testing';

import { CheckoutGuardService } from './checkout-guard.service';
import { provideMockStore } from '@ngrx/store/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('CheckoutGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CheckoutGuardService, provideMockStore()],
      imports: [RouterTestingModule],
    });
  });

  it('should be created', inject(
    [CheckoutGuardService],
    (service: CheckoutGuardService) => {
      expect(service).toBeTruthy();
    }
  ));
});
