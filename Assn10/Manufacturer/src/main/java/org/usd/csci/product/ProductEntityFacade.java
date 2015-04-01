/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.csci.product;

import org.usd.csci.utility.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @Override
    public void create(ProductEntity product) {

    }

    @Override
    public void edit(ProductEntity product) {

    }

}
