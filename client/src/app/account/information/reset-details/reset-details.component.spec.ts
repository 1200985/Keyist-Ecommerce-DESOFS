import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetDetailsComponent } from './reset-details.component';
import { AccountService } from 'src/app/services/account.service';
import { provideMockStore } from '@ngrx/store/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ResetDetailsComponent', () => {
  let component: ResetDetailsComponent;
  let fixture: ComponentFixture<ResetDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ResetDetailsComponent],
      providers: [provideMockStore(), AccountService],
      imports: [RouterTestingModule, HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
