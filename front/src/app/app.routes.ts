import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {authGuard} from "./shared/guards/auth.guard";

export const APP_ROUTES: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./login/login.routes').then((m) => m.LOGIN_ROUTES),
  },
  {
    path: 'register',
    loadChildren: () =>
      import('./register/register.routes').then((m) => m.REGISTER_ROUTES),
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES),
      canActivate: [authGuard],
  },
  {
    path: 'cart',
    loadChildren: () =>
      import('./cart/cart.routes').then((m) => m.CART_ROUTES),
      canActivate: [authGuard],
  },
  {
    path: 'wishlist',
    loadChildren: () =>
      import('./wishlist/wishlist.routes').then((m) => m.WISHLIST_ROUTES),
    canActivate: [authGuard],
  },
  {
    path: 'contact',
    loadChildren: () =>
      import('./contact/contact.routes').then((m) => m.CONTACT_ROUTES),
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
