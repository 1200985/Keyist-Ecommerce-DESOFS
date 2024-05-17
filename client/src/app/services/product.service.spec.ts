import { inject, TestBed } from '@angular/core/testing';

import { ProductService } from './product.service';

describe('ProductService', () => {
  let productServiceMock: jasmine.SpyObj<ProductService> = jasmine.createSpyObj(
    'AccountServiceSpy',
    ['getUser']
  );
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: ProductService, useValue: productServiceMock }],
    });
  });

  it('should be created', inject(
    [ProductService],
    (service: ProductService) => {
      expect(service).toBeTruthy();
    }
  ));
});
