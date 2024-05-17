import { inject, TestBed } from '@angular/core/testing';

import { OrderService } from './order.service';

describe('OrderService', () => {
  let orderServiceMock: jasmine.SpyObj<OrderService> = jasmine.createSpyObj(
    'OrderServiceSpy',
    ['forgotPasswordConfirm']
  );
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: OrderService, useValue: orderServiceMock }],
    });
  });

  it('should be created', inject([OrderService], (service: OrderService) => {
    expect(service).toBeTruthy();
  }));
});
