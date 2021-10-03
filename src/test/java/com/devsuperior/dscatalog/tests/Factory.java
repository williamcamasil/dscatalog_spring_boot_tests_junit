package com.devsuperior.dscatalog.tests;

import java.time.Instant;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

//Classe responsável por instanciar os objetos
public class Factory {
	
	//Função estatica para retornar um objeto produto
	public static Product createProduct() {
		Product product = new Product(1l, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(2L, "Electronics"));
		return product;
	}
	
	//Instancia o produto
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
}
