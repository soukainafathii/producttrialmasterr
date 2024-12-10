import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of, tap, throwError} from "rxjs";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly path = "http://localhost:8080/api/products";

    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

    public get(): Observable<Product[]> {
        const headers = this.getHeaders();
        return this.http.get<Product[]>(this.path, { headers }).pipe(
            catchError((error) => {
                return this.http.get<Product[]>("assets/products.json");
            }),
            tap((products) => this._products.set(products)),
        );
    }

  public create(product: Product): Observable<Product> {
    const headers = this.getHeaders();
    return this.http.post<Product>(this.path, product, { headers }).pipe(
      tap((createdProduct: Product) => {
        this._products.update((products: Product[]) => [createdProduct, ...products]);
      }),
      catchError((error: any) => {
        console.error("Error creating product:", error);
        return throwError(() => error);
      })
    );
  }

    public update(product: Product): Observable<boolean> {
      const headers = this.getHeaders();
        return this.http.patch<boolean>(`${this.path}/${product.id}`, product,  { headers }).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => {
                return products.map(p => p.id === product.id ? product : p)
            })),
        );
    }


  public delete(productId: number): Observable<boolean> {
    const headers = this.getHeaders();
    return this.http.delete<boolean>(`${this.path}/${productId}`, { headers }).pipe(
      catchError((error) => {
        console.error('Error deleting product:', error);
        return of(false);
      }),
      tap(() => this._products.update(products => products.filter(product => product.id !== productId))),
    );
  }

  public getProductById(productId: number): Observable<Product> {
    const headers = this.getHeaders();
    return this.http.get<Product>(`${this.path}/${productId}`, { headers }).pipe(
      catchError((error) => {
        console.error('Error fetching product details:', error);
        return throwError(() => error);
      })
    );
  }

}
