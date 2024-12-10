import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {BehaviorSubject, map, Observable} from 'rxjs';
import { Wishlist } from './wishlist.model';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = 'http://localhost:8080/api/wishlist';
  private wishlistSubject = new BehaviorSubject<Wishlist>({ products: [] });

  constructor() {
    this.loadWishlist();
  }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    console.log('Token being sent:', token);
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  loadWishlist(): void {
    this.http.get<Wishlist>(`${this.apiBaseUrl}`, { headers: this.getHeaders() }).subscribe({
      next: (wishlist) => this.wishlistSubject.next(wishlist),
      error: (err) => console.error('Failed to load wishlist', err),
    });
  }

  getWishlist(): Observable<Wishlist> {
    return this.wishlistSubject.asObservable();
  }

  addProductToWishlist(productId: number): Observable<Wishlist> {
    return this.http.post<Wishlist>(`${this.apiBaseUrl}/add`, null, {
      headers: this.getHeaders(),
      params: { productId: productId.toString() },
    }).pipe(
      map((updatedWishlist) => {
        this.wishlistSubject.next(updatedWishlist);
        return updatedWishlist;
      })
    );
  }

  removeProductFromWishlist(productId: number): Observable<Wishlist> {
    return this.http.delete<Wishlist>(`${this.apiBaseUrl}/remove`, {
      headers: this.getHeaders(),
      params: { productId: productId.toString() },
    }).pipe(
      map((updatedWishlist) => {
        this.wishlistSubject.next(updatedWishlist);
        return updatedWishlist;
      })
    );
  }
}
