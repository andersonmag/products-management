import { ImageProduct } from "./image-product";

export interface Product {
    id: number;
    title: string;
    description: string;
    price: number;
    images: ImageProduct[];
}
