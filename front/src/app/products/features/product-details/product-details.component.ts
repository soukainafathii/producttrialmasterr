import {Component, inject, OnInit} from '@angular/core';
import {Product} from "../../data-access/product.model";
import {ProductsService} from "../../data-access/products.service";
import {ActivatedRoute} from "@angular/router";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {RatingModule} from "primeng/rating";
import {FormsModule} from "@angular/forms";
import {Button} from "primeng/button";
import {CartService} from "../../../cart/data-access/cart.service";

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [
    NgIf,
    NgClass,
    RatingModule,
    FormsModule,
    Button,
    NgForOf
  ],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css'
})
export class ProductDetailsComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);

  product?: Product;

  ngOnInit(): void {
    const productId = this.route.snapshot.params['id'];
    if (productId) {
      this.productsService.getProductById(productId).subscribe({
        next: (product) => (this.product = product),
        error: (err) => console.error('Failed to fetch product details', err),
      });
    }
  }

  public addToCart(productId: number | undefined): void {
    if (!productId) {
      console.error("Product ID is undefined. Cannot add to cart.");
      return;
    }
    this.cartService.addProductToCart(productId, 1).subscribe({
      next: () => {
        console.error("Product added to cart");
      },
      error: (err) => {
        console.error("Failed to add product to cart", err);
        alert("Failed to add product to cart. Please try again.");
      },
    });
  }
}
