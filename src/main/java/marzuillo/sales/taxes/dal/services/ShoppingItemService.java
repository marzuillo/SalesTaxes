package marzuillo.sales.taxes.dal.services;

import java.util.List;

import javax.persistence.EntityManager;

import marzuillo.sales.taxes.dal.entity.Category;
import marzuillo.sales.taxes.dal.entity.Product;
import marzuillo.sales.taxes.exception.CategoryNotFoundException;
import marzuillo.sales.taxes.exception.ProductNotFoundException;

public class ShoppingItemService {

	private EntityManager em;
	
	public ShoppingItemService(EntityManager em) {
		this.em=em;
	}
	
	public Category findCategory(String title) throws CategoryNotFoundException{
		List<Category> categories = em.createNamedQuery("Product.findCategory", Category.class)
				.setParameter(1, title)
				.getResultList();
		
		if(categories!=null && categories.size()>0) {
			return categories.get(0);
		}else {
			throw new CategoryNotFoundException("Category not found for product:"+title);
		}
	}
	
	public Product findProduct(String title) throws ProductNotFoundException{
		List<Product> products = em.createNamedQuery("Product.findProduct", Product.class)
				.setParameter(1, title)
				.getResultList();
		if(products!=null && products.size()>0) {
			return products.get(0);
		}else {
			throw new ProductNotFoundException("Product not found for product:"+title);
		}
	}
}
