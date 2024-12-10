export interface CartItem {
  productId: number;
  productName: string;
  productPrice: number;
  inventoryStatus: "INSTOCK" | "LOWSTOCK" | "OUTOFSTOCK";
  rating: number;
  quantity: number;
  image: string;
}

export interface Cart {
  items: CartItem[];
}
