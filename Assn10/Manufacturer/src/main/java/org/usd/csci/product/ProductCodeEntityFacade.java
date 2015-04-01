/*
 * ProductEntityFacade.java 4/2/2015 11:30pm
 * $Name: Tyler Jones
 */
package org.usd.csci.product;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.usd.csci.utility.AbstractFacade;
import org.usd.csci.utility.InvalidEntityException;

/**
 * ProductCode SessionBean/Facade - Used to perform CRUD operations on Product
 * Codes. Overrides some AbstractFacades
 *
 * @author Tyler
 */
@Stateless
public class ProductCodeEntityFacade extends AbstractFacade<ProductCodeEntity> {

    @PersistenceContext(unitName = "ManufacturerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductCodeEntityFacade() {
        super(ProductCodeEntity.class);
    }

    /*
     *  Create - Create a a new Product Code.  Product Code must be unique
     *
     * @param productCode
     * @throws InvalidEntityException
     * @throws Exception
     */
    @Override
    public void create(ProductCodeEntity productCode) throws InvalidEntityException {
        if (productCode == null) {
            throw new InvalidEntityException("Product Code cannot be null");
        }
        //Verify that the product code is unique
        ProductCodeEntity locEntity = find(productCode.getProdCode());
        if (locEntity != null) {
            throw new InvalidEntityException("ProductCode must be unique.");
        }
        super.create(productCode);
    }

    /*
     *  Edit Product Code - Product Code Must Be Unique
     *
     * @param productCode
     * @throws InvalidEntityException
     * @throws Exception
     */
    @Override
    public void edit(ProductCodeEntity productCode) throws InvalidEntityException {
        if (productCode == null) {
            throw new InvalidEntityException("ProductCode CANNOT be null.");
        }
        //Verify that product code is unique
        ProductCodeEntity locEntity = find(productCode.getProdCode());
        if (locEntity != null) {
            throw new InvalidEntityException("Product Code is NOT Unique.");
        }
        //Perform Edit
        super.edit(productCode);
    }

    /**
     * ========================================================== Product Find
     * Methods ==========================================================
     */
    
//    /**
//     * Find product by Product code. Returns a list of products with a given
//     * ProductCode.
//     *
//     * @param productCode
//     * @return
//     */
//    public List<ProductEntity> findByProductCode(ProductCodeEntity productCode) {
//        System.out.println("IN ProductFacade -- FIND PRODUCT BY PRODUCT CODE");
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery cq = builder.createQuery();
//
//        //Build SQL Query Of Database
//        Root<ProductEntity> product = cq.from(ProductEntity.class);
//        cq.select(product); //SELECT product
//        cq.where(builder.equal(product.get("productCode"), productCode)); //WHERE prodCode = prodCode
//        return em.createQuery(cq).getResultList();  //Return list of results
//    }
//
//    /**
//     * Find product by MFG. Returns a list of products with a manufacturer.
//     *
//     * @param manufacturer
//     * @return
//     */
//    public List<ProductEntity> findByMfg(ManufacturerEntity manufacturer) {
//        System.out.println("IN ProductFacade -- FIND PRODUCT BY MFG ID");
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery cq = builder.createQuery();
//
//        //Build SQL Query Of Database
//        Root<ProductEntity> product = cq.from(ProductEntity.class);
//        cq.select(product); //SELECT product
//        //WHERE manufacturerID = manufacturer
//        cq.where(builder.equal(product.get("manufacturerId"), manufacturer));
//        return em.createQuery(cq).getResultList();  //Return list of results
//    }
//
//    /**
//     * Find all available products. Returns a list of available products.
//     *
//     * @return
//     */
//    public List<ProductEntity> findAllAvailable() {
//        System.out.println("IN ProductFacade -- FINDING ALL AVAILABLE PRODUCTS");
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery cq = builder.createQuery();
//
//        //Build SQL Query Of Database
//        Root<ProductEntity> product = cq.from(ProductEntity.class);
//        cq.select(product); //SELECT product
//        cq.where(builder.equal(product.get("available"), "TRUE")); //WHERE available = TRUE
//        return em.createQuery(cq).getResultList();  //Return list of results. Check if list is EMPTY, not null.
//    }
//
//    /**
//     * Find Products with a QuantityOnHand less than the limit passed. Returns a
//     * list of products with
//     *
//     * @param limit
//     * @return
//     */
//    public List<ProductEntity> findUnderQOHLimit(Integer limit) {
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery cq = builder.createQuery();
//
//        //Build SQL Query Of Database
//        Root<ProductEntity> product = cq.from(ProductEntity.class);
//        cq.select(product); //SELECT product
//        //WHERE the QOH is less than the QOH limit
//        cq.where(builder.lessThan(product.get(ProductEntity_.quantityOnHand), limit)); //WHERE prodCode = prodCode
//        return em.createQuery(cq).getResultList();  //Return list of results
//    }
}
