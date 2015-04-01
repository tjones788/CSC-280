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
import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.usd.csci.utility.InvalidEntityException;
import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;

/**
 *
 * @author Tyler
 */
@RunWith(Arquillian.class)
public class ProductEntityTest {

    @Inject
    ManufacturerEntityFacade manFacade;

    @Inject
    ProductCodeEntityFacade prodCodeFacade;

    public static ProductEntity product = new ProductEntity();
    public static ProductCodeEntity prodCodeEntity = new ProductCodeEntity();
    public static ManufacturerEntity manufacturer = new ManufacturerEntity();

    static private Validator validator;

    //random number generator
    private static final Random random = new Random();

    public ProductEntityTest() {
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
                .addAsManifestResource(persistenceXml, "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
    }

    @BeforeClass
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        manufacturer.setManufacturerId(random.nextInt(15000000));
        manufacturer.setName("PRODUCT TEST X");
        manufacturer.setAddressLine1("123 Main St");
        manufacturer.setAddressLine2("Apt 7");
        manufacturer.setCity("Vermillion");
        manufacturer.setState("SD");
        manufacturer.setZip("57069");
        manufacturer.setPhone("800-123-4567");
        manufacturer.setFax("888-888-8888");
        manufacturer.setRep("Tyler Jones");
        manufacturer.setEmail("testProduct@gmail.com");

        System.out.println("RANDOM MANUFACTURER ID CREATED " + manufacturer.getManufacturerId());
        //setup product, with valid values
        product.setProductId(5001);
        product.setPurchaseCost(new BigDecimal("100.00"));
        product.setQuantityOnHand(10);
        product.setMarkup(new BigDecimal("10.00"));
        product.setAvailable("False");
        product.setDescription("Test Product");

        prodCodeEntity.setProdCode("SW");
        prodCodeEntity.setDiscountCode("H");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    /**
     * Test of setPurchaseCost method, of class ProductEntity.
     */
    @Test
    public void testSetPurchaseCost() {
        System.out.println("=== setPurchaseCost ===");
        product.setPurchaseCost(new BigDecimal("50.01"));

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "purchaseCost");
        assertEquals(0, constraintViolations.size());
        assertEquals(product.getPurchaseCost(), BigDecimal.valueOf(50.01));
        System.out.println("Product Purchase Cost set to: " + product.getPurchaseCost());
    }

    /**
     * Test of setPurchaseCost method, of class ProductEntity.
     */
    @Test
    public void testSetInvalidPurchaseCost() {
        System.out.println("=== setInvalidPurchaseCost ===");
        product.setPurchaseCost(new BigDecimal("-10.00"));

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "purchaseCost");
        assertTrue("Purchase cost should not have been set", constraintViolations.size() > 0);
        System.out.println("Constraint Violations: " + constraintViolations.size());
    }

    /**
     * Test of setQuantityOnHand method, of class ProductEntity.
     */
    @Test
    public void testSetQuantityOnHand() {
        System.out.println("===setQuantityOnHand===");
        int testQty = 10;
        product.setQuantityOnHand(testQty);

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "quantityOnHand");
        System.out.println("Constraint violation: " + constraintViolations.toString());
        assertEquals(0, constraintViolations.size());
        assertTrue(product.getQuantityOnHand() == testQty);
        System.out.println("Product QOH set to: " + product.getQuantityOnHand());
    }

    /**
     * Test of setQuantityOnHand method, of class ProductEntity.
     */
    @Test
    public void testSetInvalidQuantityOnHand() {
        System.out.println("===setInvalidQuantityOnHand===");
        int testQty = -5;
        product.setQuantityOnHand(testQty);

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "quantityOnHand");
        assertEquals(1, constraintViolations.size());
    }

    /**
     * Test of setMarkup method, of class ProductEntity.
     */
    @Test
    public void testSetMarkup() {
        System.out.println("===TEST SETTING THE MARKUP ON A PRODUCT===");
        BigDecimal testMarkup = new BigDecimal("10.05");
        product.setMarkup(testMarkup);

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "markup");

        //Assert there were no violations
        assertEquals(0, constraintViolations.size());
        //Assert the product's markup is equal to the test markup set
        assertEquals(product.getMarkup(), testMarkup);

        System.out.println("Product Markup has been set to: " + testMarkup);
    }

    /**
     * Test setting an INVALID setMarkup method, of class ProductEntity.
     */
    @Test
    public void testSetInvalidMarkup() {
        System.out.println("===TEST SETTING INVALID MARKUP ON A PRODUCT===");
        product.setMarkup(BigDecimal.valueOf(1000));

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "markup");

        //Assert that there is one violation
        assertEquals(1, constraintViolations.size());
    }

    /**
     * Test setting an Negative setMarkup method value, of class ProductEntity.
     */
    @Test
    public void testSetNegativeMarkup() {
        System.out.println("===TEST SETTING INVALID MARKUP ON A PRODUCT===");
        product.setMarkup(BigDecimal.valueOf(-100));

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validateProperty(product, "markup");

        //Assert that there is one violation
        assertEquals(1, constraintViolations.size());
    }

    /**
     * Test of setAvailable method, of class ProductEntity. Tests for TRUE
     */
    @Test
    public void testSetAvailableTrue() {
        System.out.println("=== TEST SETTING TRUE AVAILABILITY OF PRODUCT===");
        product.setAvailable("TRUE");
        //Assert the product availability is "TRUE"
        assertEquals(product.getAvailable(), "TRUE");
    }

    /**
     * Test of setAvailable method, of class ProductEntity. Tests for FALSE
     */
    @Test
    public void testSetAvailableFalse() {
        System.out.println("=== TEST SETTING FALSE AVAILABILITY OF PRODUCT===");
        product.setAvailable("FALSE");
        //Assert the product availability is "FALSE"
        assertEquals(product.getAvailable(), "FALSE");
    }

    /**
     * Test of setDescription method, of class ProductEntity.
     */
    @Test
    public void testSetDescription() {
        System.out.println("===TEST SETTING A PRODUCT DESCRIPTION===");
        String testDesc = "THIS IS A TEST PRODUCT AND IS ONLY FOR TESTS";

        product.setDescription(testDesc);
        //Assert the product description is equal to the test description
        assertEquals(product.getDescription(), testDesc);
    }

    /**
     * Test of setManufacturerId method, of class ProductEntity.
     *
     * @throws Exception
     */
    @Test
    public void testSetManufacturerId() throws Exception {
        System.out.println("===TEST SETTING MFG ID FOR A PRODUCT===");

        //Get the list of Manufacturer Entities out of the DB
        List<ManufacturerEntity> mfgList = manFacade.findAll();
        if (mfgList.isEmpty()) {
            //create one
            manFacade.create(manufacturer);
            mfgList = manFacade.findAll();
        }
        System.out.println("Found: " + mfgList.size() + " Manufacturers.");

        //get the first MFG from the list
        ManufacturerEntity localMfg = mfgList.get(0);
        System.out.println("1st MFG Name: " + localMfg.getName());
        //set the products manufacturer with the first one in the list
        product.setManufacturerId(localMfg);
        System.out.println("PRODUCT MFG ID = " + localMfg.getManufacturerId());

        //Make sure the name of the first MFG from the list matches the 
        //name of the manufacturer in the product
        ManufacturerEntity result = product.getManufacturerId();
        assertEquals(localMfg.getName(), result.getName());
    }

    /**
     * Test of setProductCode method, of class ProductEntity.
     */
    @Test
    public void testSetProductCode() {
        System.out.println("===TEST SETTING A PRODUCT CODE===");

        List<ProductCodeEntity> codeList = prodCodeFacade.findAll();
        //get a random product code
        ProductCodeEntity prodCode = codeList.get(random.nextInt(codeList.size() - 1));

        System.out.println("Random Product Code: " + prodCode.getProdCode());
        product.setProductCode(prodCode);
        //Assert the product code equals the productCode we set
        assertTrue(product.getProductCode().equals(prodCode));
        assertEquals(product.getProductCode().getProdCode(), prodCode.getProdCode());
    }

    /**
     * Test of equals method, of ProductEntity.
     */
    @Test
    public void testEquals() {
        System.out.println("===TEST EQUALS METHOD OF PRODUCT ENTITIES===");
        ProductEntity product1 = new ProductEntity();
        product1.setProductId(123456);
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(123456);

        assertTrue(product2.equals(product1));
    }

    /**
     * Test of NOT equals method, of ProductEntity.
     */
    @Test
    public void testNotEquals() {
        System.out.println("===TEST EQUALS METHOD OF PRODUCT ENTITIES===");
        ProductEntity product1 = new ProductEntity();
        product1.setProductId(123456);
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(000000);

        //Assert that the two products are not equal
        assertFalse(product2.equals(product1));
    }

    /**
     * Test of toString method, of class ManufacturerEntity.
     */
    @Test
    public void testToString() {
        System.out.println("===TEST TO STRING===");
        product.setProductId(111111);
        product.setDescription("Test Description");
        String localString = product.getProductId() + product.getDescription();

        //Assert the product toString() = productID + productDescription
        assertTrue(localString.equals(product.toString()));
        System.out.println("Product toString = " + product.toString());
    }

    /**
     * Test of compareTo method, of class ManufacturerEntity.
     */
    @Test
    public void testCompareTo() {
        System.out.println("===TEST COMPARE TO===");
        product.setProductId(111111);
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(111111);

        assertEquals(0, product2.compareTo(product));
    }

    /**
     * Test of compareTo method, of class ManufacturerEntity.
     */
    @Test
    public void testLTCompareTo() {
        System.out.println("===TEST LT COMPARE TO===");
        product.setProductId(111110);
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(111111);

        assertTrue(product2.compareTo(product) > 0);
    }

    /**
     * Test of compareTo method, of class ManufacturerEntity.
     */
    @Test
    public void testGTCompareTo() {
        System.out.println("===TEST GT COMPARE TO===");
        product.setProductId(111113);
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(111112);

        assertTrue(product2.compareTo(product) < 0);
    }

}
