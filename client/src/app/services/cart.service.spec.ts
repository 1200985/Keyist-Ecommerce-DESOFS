import { inject, TestBed } from '@angular/core/testing';

import { CartService } from './cart.service';

describe('CartService', () => {
  let cartServiceMock: jasmine.SpyObj<CartService> = jasmine.createSpyObj(
    'CartServiceSpy',
    ['forgotPasswordConfirm']
  );

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: CartService, useValue: cartServiceMock }],
    });
  });

  it('should be created', inject([CartService], (service: CartService) => {
    expect(service).toBeTruthy();
  }));
});
