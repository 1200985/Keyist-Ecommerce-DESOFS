import { inject, TestBed } from '@angular/core/testing';

import { AccountService } from './account.service';

describe('AccountService', () => {
  let accountServiceMock: jasmine.SpyObj<AccountService> = jasmine.createSpyObj(
    'AccountServiceSpy',
    ['forgotPasswordConfirm']
  );
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: AccountService, useValue: accountServiceMock }],
    });
  });

  it('should be created', inject(
    [AccountService],
    (service: AccountService) => {
      expect(service).toBeTruthy();
    }
  ));
});
