/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.csci.product;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;

/**
 *
 * @author Tyler
 */
@RunWith(Arquillian.class)
public class ProductEntityFacadeTest {

    @Inject
    ManufacturerEntityFacade manFacade;

    @Inject
    ProductCodeEntityFacade prodCodeFacade;

    @Inject
    ProductEntityFacade productFacade;

    public static ProductEntity product = new ProductEntity();
    public static ProductCodeEntity prodCode;
    public static ManufacturerEntity manufacturer;

    //random number generator
    private static final Random random = new Random();

    public ProductEntityFacadeTest() {
    }

    @Deployment
    public static JavaArchive deploy() {
        FileAsset persistenceXml = new FileAsset(
                new File("src/main/resources/META-INF/persistence.xml"));
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ManufacturerEntityFacade.class)
                .addClass(ManufacturerEntity.class)
                .addClass(ProductCodeEntityFacade.class)
                .addClass(ProductCodeEntity.class)
                .addClass(ProductEntity.class)
                .addClass(ProductEntityFacade.class)
                .addAsManifestResource(persistenceXml, "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<ManufacturerEntity> manufacturers = manFacade.findAll();
        manufacturer = manufacturers.get(random.nextInt(manufacturers.size() - 1)); //get a random MFG each time this test class is ran.

        List<ProductCodeEntity> productCodes = prodCodeFacade.findAll();
        prodCode = productCodes.get(random.nextInt(productCodes.size() - 1)); //get a random Product Code each time this test class is ran.

        product.setProductId(100001);
        product.setAvailable("TRUE");
        product.setDescription("Test Product");
        product.setMarkup(new BigDecimal("50.00"));
        product.setManufacturerId(manufacturer);
        product.setProductCode(prodCode);
        product.setPurchaseCost(new BigDecimal("500.00"));
        product.setQuantityOnHand(100);
    }

    @After
    public void tearDown() {
        ProductEntity createdProd = productFacade.find(product.getProductId());
        if (createdProd != null) {
            productFacade.remove(product);
        }
    }

    /**
     * Test of create method, of class ProductEntityFacade.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        try {
            productFacade.create(product);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test of create method, of class ProductEntityFacade.
     */
    @Test
    public void testNonUniqueCreate() throws Exception {
        System.out.println("create");

        try {
            //make sure product is created, it shouldn't be.  It should have been removed after each test it was created in.
            ProductEntity prodCheck = productFacade.find(product.getProductId());
            if (prodCheck == null) {
                productFacade.create(product);
            }

            product.setProductId(product.getProductId());
            productFacade.create(product);
            fail("Exception should have been thrown.");
        } catch (Exception e) {
            System.out.println("Excpetion: " + e.getMessage());
        }
    }

    /**
     * Test of create method, of class ProductEntityFacade.
     */
    @Test
    public void testCreateWithZeroQOH() throws Exception {
        System.out.println("create product with ZERO QOH");
        try {
            product.setQuantityOnHand(0);
            System.out.println("Before QOH: " + product.getQuantityOnHand());
            productFacade.create(product);
        } catch (Exception e) {
            e.getMessage();
        }
        ProductEntity locEntity = productFacade.find(product.getProductId());
        String availVal = locEntity.getAvailable();
        System.out.println("After Available: " + availVal);
        assertTrue("Availability should have been set to false.", (locEntity.getAvailable().equalsIgnoreCase("false")));
    }

    /**
     * Test of edit method, of class ProductEntityFacade.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
    }

    /**
     * Test of findByProductCode method, of class ProductEntityFacade.
     */
    @Test
    public void testFindAllByProductCode() throws Exception {
        System.out.println("===findAllByProductCode===");
        //make sure there is a product in the database
        ProductEntity prodCheck = productFacade.find(product.getProductId());
        if (prodCheck == null) {
            try {
                productFacade.create(product);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        List<ProductEntity> returnedList = productFacade.findAllByProductCode(product.getProductCode());
        System.out.println("FOUND LIST SIZE: " + returnedList.size());

        //Assert the set ProductCode is equal to the product codes found by the list
        for (ProductEntity loopProd : returnedList) {
            System.out.println("Product Code: " + loopProd.getProductCode().getProdCode());
            assertEquals(product.getProductCode(), loopProd.getProductCode());
        }
    }

    /**
     * Test of findByMfg method, of ProductEntityFacade.
     *
     * @throws Exception
     */
    @Test
    public void testFindAllByMfg() throws Exception {
        System.out.println("===TEST FIND PRODUCT BY MFG===");
        //make sure a product has been created, with the manufacturer we setUp.
        //IS THIS NECESSARY?  
        ProductEntity prodCheck = productFacade.find(product.getProductId());
        if (prodCheck == null) {
            try {
                productFacade.create(product);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        List<ProductEntity> resultList = productFacade.findAllByMfg(manufacturer);
        Integer listSz = resultList.size();

        System.out.println("Found " + listSz + " Products made by manufacturer: " + manufacturer.getManufacturerId());
        for (int i = 0; i < listSz; i++) {

            ProductEntity listEntity = resultList.get(i);
            ManufacturerEntity listManufac = listEntity.getManufacturerId();

            //Assert that the product in the list has the same manufacturerId
            assertTrue(listManufac.getManufacturerId().equals(manufacturer.getManufacturerId()));
            System.out.println("Product ID: " + listEntity.getProductId() + " Manufac: "
                    + listManufac.getManufacturerId());
        }
    }

    /**
     * Test of findUnderQOHLimit method, of ProductEntityFacade.
     */
    @Test
    public void testFindUnderQOHLimit() {
        System.out.println("===TEST FIND UNDER QOH LIMIT===");
        //make sure there is a product created with a QOH
        ProductEntity prodCheck = productFacade.find(product.getProductId());
        if (prodCheck == null) {
            try {
                productFacade.create(product);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        Integer testLimit = 50;

        List<ProductEntity> productList = productFacade.findUnderQOHLimit(testLimit);
        Integer listSz = productList.size();

        //Loop through list and assert that each productQOH is under the testLimit
        System.out.println("Found " + listSz + " Products with QOH under: " + testLimit);
        for (ProductEntity loopProd : productList) {
            assertTrue(loopProd.getQuantityOnHand() < testLimit); //assert the QOH is under the limit
            System.out.println("Product ID = " + loopProd.getProductId() + //print out product ID and QOH
                    "Product QOH = " + loopProd.getQuantityOnHand()
            );
        }
    }

    /**
     * Test of findAllAvailable method, of ProductEntityFacade
     */
    @Test
    public void testFindAllAvailaible() {
        System.out.println("===TEST FIND ALL AVAILABLE PRODUCTS===");
        ProductEntity prodCheck = productFacade.find(product.getProductId());
        if (prodCheck == null) {
            try {
                productFacade.create(product);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        String value = "TRUE";
        List<ProductEntity> resultList = productFacade.findAllAvailable(value);
        Integer listSz = resultList.size();

        System.out.println(listSz + " products are available.");
        for (ProductEntity loopProd : resultList) {
            assertEquals("TRUE", loopProd.getAvailable());

            System.out.println("Product ID: " + loopProd.getProductId() + " Availability: " + loopProd.getAvailable());
        }
    }

}
