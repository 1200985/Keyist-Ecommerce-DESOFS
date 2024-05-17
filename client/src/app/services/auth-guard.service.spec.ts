import { inject, TestBed } from '@angular/core/testing';

import { AuthGuardService } from './auth-guard.service';
import { Store } from '@ngrx/store';
import { RouterTestingModule } from '@angular/router/testing';

describe('AuthGuardService', () => {
  let storeMock: jasmine.SpyObj<Store>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthGuardService, { provide: Store, useValue: storeMock }],
      imports: [RouterTestingModule],
    });
  });

  it('should be created', inject(
    [AuthGuardService],
    (service: AuthGuardService) => {
      expect(service).toBeTruthy();
    }
  ));
});
