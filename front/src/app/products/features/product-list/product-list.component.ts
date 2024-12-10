import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import {CurrencyPipe, NgClass, NgForOf, NgIf} from "@angular/common";
import { RatingModule } from 'primeng/rating';
import {FormsModule} from "@angular/forms";
import { Router } from "@angular/router";
import {CartService} from "../../../cart/data-access/cart.service";
import {WishlistService} from "../../../wishlist/data-access/wishlist.service";
import {RatingComponent} from "ngx-bootstrap/rating";
import {NgxStarsModule} from "ngx-stars";

const emptyProduct: Product = {
  id : undefined,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: undefined,
  updatedAt: undefined,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, NgIf, NgClass, RatingModule, FormsModule, NgForOf, NgxStarsModule],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);
  private readonly wishListService = inject(WishlistService);
  private readonly router = inject(Router);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  ngOnInit() {
    this.productsService.get().subscribe();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    if (product.id) {
      this.productsService.delete(product.id).subscribe();
    } else {
      alert('Unable to delete the product. The product ID is missing.');
    }
  }

  public onSave(product: Product) {
    const { createdAt, updatedAt, ...productPayload } = product;

    if (this.isCreation) {
      this.productsService.create(productPayload).subscribe({
        next: () => {
          console.log("Product created successfully");
          this.closeDialog();
        },
        error: (err) => {
          if (err.status === 409 && err.error === "The product code already exists.") {
            alert("The product code already exists. Please use a different code.");
          } else {
            alert("Failed to create product. Please try again.");
          }
        },
      });
    } else {
      this.productsService.update(productPayload).subscribe({
        next: () => {
          console.log("Product updated successfully");
          this.closeDialog();
        },
        error: (err) => {
          console.error("Error updating product:", err);
          alert("Failed to update product. Please try again.");
        },
      });
    }
  }

  public addToCart(productId: number): void {
    this.cartService.addProductToCart(productId, 1).subscribe({
      next: () => {
        console.error("Produit ajouté au panier");
      },
      error: (err) => {
        console.error("Echec d'ajout du produit au panier", err);
        alert("Failed to add product to cart. Please try again.");
      },
    });
  }

  public addToWishlist(productId: number): void {
    this.wishListService.addProductToWishlist(productId).subscribe({
      next: () => {
        console.error("Produit ajouté à la list d'envie");
      },
      error: (err) => {
        console.error("Echec d'ajout du produit à la list d'envie", err);
        alert("Failed to add product to wishlist. Please try again.");
      },
    });
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  get isAdmin(): boolean {
    const userEmail = localStorage.getItem('userEmail');
    return userEmail === 'admin@admin.com';
  }

  public viewDetails(productId: number): void {
    this.router.navigate(['/products', productId]);
  }
}
