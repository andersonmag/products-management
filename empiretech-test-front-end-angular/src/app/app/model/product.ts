import { ImageProduct } from "./imageProduct";

export interface Product {
    id: number;
    title: string;
    description: string;
    price: number;
    images: ImageProduct[];
}
