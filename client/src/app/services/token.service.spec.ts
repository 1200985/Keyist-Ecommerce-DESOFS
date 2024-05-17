import { inject, TestBed } from '@angular/core/testing';

import { TokenService } from './token.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AuthService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TokenService],
      imports: [HttpClientTestingModule],
    });
  });

  it('should be created', inject([TokenService], (service: TokenService) => {
    expect(service).toBeTruthy();
  }));
});
