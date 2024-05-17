import { inject, TestBed } from '@angular/core/testing';

import { AccountService } from './account.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AccountService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountService],
      imports: [HttpClientTestingModule],
    });
  });

  it('should be created', inject(
    [AccountService],
    (service: AccountService) => {
      expect(service).toBeTruthy();
    }
  ));
});
