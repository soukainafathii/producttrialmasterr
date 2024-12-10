import { Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import {ProductDetailsComponent} from "./features/product-details/product-details.component";

export const PRODUCTS_ROUTES: Routes = [
	{
		path: "list",
		component: ProductListComponent,
	},
  {
    path: ':id',
    component: ProductDetailsComponent
  },
	{ path: "**", redirectTo: "list" },
];
