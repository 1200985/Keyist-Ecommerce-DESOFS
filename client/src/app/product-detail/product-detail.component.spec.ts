import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductDetailComponent } from './product-detail.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ProductService } from '../services/product.service';
import { provideMockStore } from '@ngrx/store/testing';

describe('ItemDetailComponent', () => {
  let component: ProductDetailComponent;
  let fixture: ComponentFixture<ProductDetailComponent>;
  let productServiceMock: jasmine.SpyObj<ProductService> = jasmine.createSpyObj(
    'ProductServiceSpy',
    ['forgotPasswordConfirm']
  );

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ProductDetailComponent],
      providers: [
        provideMockStore(),
        { provide: ProductService, useValue: productServiceMock },
      ],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
