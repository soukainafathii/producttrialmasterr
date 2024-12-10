import { Injectable, inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, map, Observable} from 'rxjs';
import { Cart } from './cart.model';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080/api/cart';
  private cartSubject = new BehaviorSubject<Cart>({ items: [] });

  constructor() {
    this.loadCart();
  }

  loadCart(): void {
    this.http.get<Cart>(`${this.apiBaseUrl}`, { headers: this.getHeaders() })
      .subscribe({
        next: (cart) => this.cartSubject.next(cart),
        error: (err) => console.error('Failed to load cart', err),
      });
  }


  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    console.log('Token being sent:', token);
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getCartItems(): Observable<Cart> {
    return this.http.get<Cart>(`${this.apiBaseUrl}`, {headers: this.getHeaders(),
    });
  }

  getCart(): Observable<Cart> {
    return this.cartSubject.asObservable();
  }

  clearCart(): void {
    this.cartSubject.next({ items: [] });
  }

  addProductToCart(productId: number, quantity: number): Observable<Cart> {
    return this.http.post<Cart>(`${this.apiBaseUrl}/add`, null, {
      headers: this.getHeaders(),
      params: {
        productId: productId.toString(),
        quantity: quantity.toString(),
      },
    })
  .pipe(
      map((updatedCart) => {
        this.cartSubject.next(updatedCart);
        return updatedCart;
      })
    );
  }

  removeProductFromCart(productId: number): Observable<Cart> {
    return this.http.delete<Cart>(`${this.apiBaseUrl}/remove`, {
      headers: this.getHeaders(),
      params: { productId: productId.toString() },
    }).pipe(
      map((updatedCart) => {
        this.cartSubject.next(updatedCart);
        return updatedCart;
      })
    );
  }
}
