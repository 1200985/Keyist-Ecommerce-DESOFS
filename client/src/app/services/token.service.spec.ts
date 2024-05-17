import { inject, TestBed } from '@angular/core/testing';

import { TokenService } from './token.service';

describe('AuthService', () => {
  let tokenServiceMock: jasmine.SpyObj<TokenService> = jasmine.createSpyObj(
    'TokenServiceSpy',
    ['forgotPasswordConfirm']
  );
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: TokenService, useValue: tokenServiceMock }],
    });
  });

  it('should be created', inject([TokenService], (service: TokenService) => {
    expect(service).toBeTruthy();
  }));
});
