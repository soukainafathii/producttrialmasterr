import { Component, OnInit, inject } from '@angular/core';
import { CartService } from '../../data-access/cart.service';
import { Cart } from '../../data-access/cart.model';
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {RatingModule} from "primeng/rating";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-cart',
  standalone: true,
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  imports: [
    NgForOf,
    NgIf,
    NgClass,
    RatingModule,
    FormsModule
  ]
})
export class CartComponent implements OnInit {
  cart: Cart = { items: [] };

  private readonly cartService = inject(CartService);

  ngOnInit(): void {
    this.loadCart();
    this.cartService.getCart().subscribe({
      next: (cart) => (this.cart = cart),
      error: (err) => console.error('Failed to load cart', err),
    });
  }
  loadCart(): void {
    this.cartService.getCartItems().subscribe({
      next: (cart) => (this.cart = cart),
      error: (err) => console.error('Failed to load cart', err),
    });
  }

  addToCart(productId: number, quantity: number): void {
    this.cartService.addProductToCart(productId, quantity).subscribe();
  }

  removeFromCart(productId: number): void {
    this.cartService.removeProductFromCart(productId).subscribe({
      next: (updatedCart) => (this.cart = updatedCart),
      error: (err) => console.error('Failed to remove product', err),
    });
  }
}
