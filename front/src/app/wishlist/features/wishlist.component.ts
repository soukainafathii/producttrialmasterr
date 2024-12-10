import { Component, OnInit, inject } from '@angular/core';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import {Wishlist} from "../data-access/wishlist.model";
import {WishlistService} from "../data-access/wishlist.service";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {TooltipModule} from "primeng/tooltip";

@Component({
  selector: 'app-wishlist',
  standalone: true,
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css'],
  imports: [
    RatingModule,
    FormsModule,
    NgIf,
    NgClass,
    NgForOf,
    TooltipModule,
  ],
})
export class WishlistComponent implements OnInit {
  wishlist: Wishlist = { products: [] };

  private readonly wishlistService = inject(WishlistService);

  ngOnInit(): void {
    this.wishlistService.getWishlist().subscribe({
      next: (wishlist) => (this.wishlist = wishlist),
      error: (err) => console.error('Failed to load wishlist', err),
    });
  }

  removeFromWishlist(productId: number): void {
    this.wishlistService.removeProductFromWishlist(productId).subscribe({
      next: (updatedWishlist) => (this.wishlist = updatedWishlist),
      error: (err) => console.error('Failed to remove product from wishlist', err),
    });
  }
}

