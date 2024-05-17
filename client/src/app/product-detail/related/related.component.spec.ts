import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatedComponent } from './related.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ProductService } from 'src/app/services/product.service';

describe('RelatedComponent', () => {
  let component: RelatedComponent;
  let fixture: ComponentFixture<RelatedComponent>;
  let productServiceMock: jasmine.SpyObj<ProductService> = jasmine.createSpyObj(
    'ProductServiceSpy',
    ['forgotPasswordConfirm']
  );

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RelatedComponent],
      providers: [{ provide: ProductService, useValue: productServiceMock }],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
