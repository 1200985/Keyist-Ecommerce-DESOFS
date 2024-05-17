import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationComponent } from './confirmation.component';
import { Store } from '@ngrx/store';
import { RouterTestingModule } from '@angular/router/testing';

describe('ConfirmationComponent', () => {
  let component: ConfirmationComponent;
  let fixture: ComponentFixture<ConfirmationComponent>;
  let storeMock: jasmine.SpyObj<Store> = jasmine.createSpyObj('StoreSpy', [
    'select',
    'dispatch',
  ]);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmationComponent],
      providers: [{ provide: Store, useValue: storeMock }],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
