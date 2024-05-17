import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOrdersComponent } from './list-orders.component';
import { OrderService } from 'src/app/services/order.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('ListOrdersComponent', () => {
  let component: ListOrdersComponent;
  let fixture: ComponentFixture<ListOrdersComponent>;
  let orderServiceMock: jasmine.SpyObj<OrderService> = jasmine.createSpyObj(
    'OrderServiceSpy',
    ['getAllOrdersCount']
  );

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ListOrdersComponent],
      providers: [{ provide: OrderService, useValue: orderServiceMock }],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
