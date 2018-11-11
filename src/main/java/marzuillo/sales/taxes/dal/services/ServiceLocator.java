package marzuillo.sales.taxes.dal.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServiceLocator {

	private static ServiceLocator _instance;
	
	private static ShoppingItemService shoppingItemService=null; 
	private static EntityManagerFactory emf;
	private static EntityManager em;

	private ServiceLocator() {}
	
	public static ServiceLocator getInstance() {
		if(_instance==null) {
			_instance=new ServiceLocator();
			if(getEntityManager()==null) {
				 emf = Persistence.createEntityManagerFactory("sales-taxes");
			     setEntityManager(emf.createEntityManager());
			}
		}
		return _instance;
	}
	
	public ShoppingItemService getShoppingItemService() {
		if(shoppingItemService==null) {
			shoppingItemService=new ShoppingItemService(em);
		}
		return shoppingItemService;
	}
	
	public static EntityManager getEntityManager() {
		return em;
	}
	public static void setEntityManager(EntityManager entityManager) {
		em = entityManager;
	} 

}
