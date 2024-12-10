import {
  Component, OnInit, signal,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { Router } from '@angular/router';
import {LoginService} from "./login/data-access/login.service";
import {NgIf} from "@angular/common";
import {TooltipModule} from "primeng/tooltip";
import {CartService} from "./cart/data-access/cart.service";


@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, NgIf, TooltipModule],
})
export class AppComponent implements OnInit{
  title = "ALTEN SHOP";

  public cartItemCount = signal<number>(0);

  constructor(private loginService: LoginService, private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.cartService.loadCart();
    this.cartService.getCart().subscribe((cart) => {
      const totalItems = cart.items.reduce((sum, item) => sum + item.quantity, 0);
      this.cartItemCount.set(totalItems);
    });
  }

  isAuthenticated(): boolean {
    return this.loginService.isAuthenticated();
  }

  goToCart(): void {
    this.router.navigate(['/cart']);
  }

  goToWishlist(): void {
    this.router.navigate(['/wishlist']);
  }

  logout(): void {
    this.loginService.logout();
    this.cartService.clearCart();
    this.cartItemCount.set(0);
    this.router.navigate(['/login']);
  }
}
