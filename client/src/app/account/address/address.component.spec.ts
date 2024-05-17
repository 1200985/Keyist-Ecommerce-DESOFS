import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddressComponent } from './address.component';
import { AccountService } from 'src/app/services/account.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AddressComponent', () => {
  let component: AddressComponent;
  let fixture: ComponentFixture<AddressComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [AddressComponent],
      providers: [AccountService],
      imports: [HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
