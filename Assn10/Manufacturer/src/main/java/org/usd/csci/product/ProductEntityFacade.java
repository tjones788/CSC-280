/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.csci.product;

import java.util.List;
import org.usd.csci.utility.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.utility.InvalidEntityException;

/**
 *
 * @author Tyler
 */
@Stateless
public class ProductEntityFacade extends AbstractFacade<ProductEntity> {

    @PersistenceContext(unitName = "ManufacturerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductEntityFacade() {
        super(ProductEntity.class);
    }

    /**
     * Create product method. Creates a new product.
     *
     * @param product
     * @throws InvalidEntityException
     */
    @Override
    public void create(ProductEntity product) throws InvalidEntityException {
        System.out.println("In ProductFacade -- CREATING PRODUCT");
        System.out.println("PRODUCT QOH: " + product.getQuantityOnHand());
        //verify product is not null
        if (product == null) {
            throw new InvalidEntityException("Product to be created CANNOT be null");
        }

        //verify that the product ID is unique
        if(find(product.getProductId()) != null){
            throw new InvalidEntityException("Product ID MUST be UNIQUE");
        }
        //verify that the purchase cost has been set
        if(product.getPurchaseCost() == null){
            throw new InvalidEntityException("Product purchase cost MUST be set.");
        }
        
        //Product’s product code and manufacturer attributes are required and should contain valid entities. (Note: check to make sure they exist in the database)
        
        
        //When the Product Entity is being created and the quantity on hand is 0, set available to “FALSE”, otherwise set available to “TRUE”.
        if(product.getQuantityOnHand() == 0 ){
            System.out.println("PRODUCT QOH: " + product.getQuantityOnHand());
            product.setAvailable("FALSE");
        }

        super.create(product);
    }

    /**
     * Edit Product method, edits a product if the ID and Manufacturer are
     * equal.
     *
     * @param product
     * @throws InvalidEntityException
     */
    @Override
    public void edit(ProductEntity product) throws InvalidEntityException {
        System.out.println("IN ProductFacade -- EDITING PRODUCT");

        ProductEntity productEdit = find(product.getProductId());

        //When the Product Entity is being updated; if the quantity on hand is 0 and available is “TRUE” or if the quantity on hand is greater than 0 and available is “FALSE”, throw an Invalid Entity Exception


//IF productId AND manufacID is equal?
        if (productEdit.getProductId().equals(product.getProductId())
                && productEdit.getManufacturerId().equals(product.getManufacturerId())) {
            super.edit(product);
        } else {
            throw new InvalidEntityException("EXCEPTION: Product ID MUST be unique.");
        }
    }

    /**
     * Find product by Product code. Returns a list of products with a given
     * ProductCode.
     *
     * @param productCode
     * @return
     */
    public List<ProductEntity> findAllByProductCode(ProductCodeEntity productCode) {
        System.out.println("IN ProductFacade -- FIND PRODUCT BY PRODUCT CODE");
        TypedQuery<ProductEntity> query = em.createNamedQuery("ProductEntity.findAllByProductCode", ProductEntity.class);
        query.setParameter("productCode", productCode);

        return query.getResultList();
    }

    /**
     * Find product by MFG. Returns a list of products with a manufacturer.
     *
     * @param manufacturer
     * @return
     */
    public List<ProductEntity> findAllByMfg(ManufacturerEntity manufacturer) {
        System.out.println("IN ProductFacade -- FIND PRODUCT BY MFG ID");
        TypedQuery<ProductEntity> query = em.createNamedQuery("ProductEntity.findAllByManufacturer", ProductEntity.class);
        query.setParameter("manufacturerId", manufacturer);
        return query.getResultList();  //Return list of results
    }

    /**
     * Find all available products. Returns a list of available products.
     *
     * @param availability
     * @return
     */
    public List<ProductEntity> findAllAvailable(String availability) {
        System.out.println("IN ProductFacade -- FINDING ALL AVAILABLE PRODUCTS");
        TypedQuery<ProductEntity> query = em.createNamedQuery("ProductEntity.findByAvailable", ProductEntity.class);
        query.setParameter("available", availability);
        return query.getResultList();  //Return list of results

    }

    /**
     * Find Products with a QuantityOnHand less than the limit passed. Returns a
     * list of products with
     *
     * @param limit
     * @return
     */
    public List<ProductEntity> findUnderQOHLimit(Integer limit) {
        TypedQuery<ProductEntity> query = em.createNamedQuery("ProductEntity.findByUnderQuantityOnHand", ProductEntity.class);
        query.setParameter("quantityOnHand", limit);
        return query.getResultList();  //Return list of results
    }

}
