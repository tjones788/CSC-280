/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.csci.manufacturer;

import java.io.File;
import java.util.List;
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

/**
 *
 * @author Tyler
 */
@RunWith(Arquillian.class)
public class ManufacturerEntityFacadeTest {

    @Inject
    ManufacturerEntityFacade manFacade;

    static public ManufacturerEntity manufacturer = new ManufacturerEntity();

    public ManufacturerEntityFacadeTest() {
    }

    @Deployment
    public static JavaArchive deploy() {
        FileAsset persistenceXml = new FileAsset(
                new File("src/main/resources/META-INF/persistence.xml"));
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ManufacturerEntityFacade.class)
                .addClass(ManufacturerEntity.class)
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
        manufacturer.setManufacturerId(19999996);
        manufacturer.setName("Test MFG");
        manufacturer.setRep("Tyler Jones");
        manufacturer.setPhone("555-555-1234");
        manufacturer.setEmail("test123@test123.com");
        manufacturer.setAddressline1("123 Main St");
        manufacturer.setAddressline2("PO BOX 123");
        manufacturer.setCity("Vermillion");
        manufacturer.setState("SD");
        manufacturer.setZip("57069-1234");
    }

    @After
    public void tearDown() {
        //        manFacade.remove(manufacturer);
    }

    /**
     * Test of findByName method, of class ManufacturerEntityFacade.
     * @throws org.usd.csci.manufacturer.InvalidEntityException
     */
    @Test
    public void testFindByName() throws InvalidEntityException, Exception {
        System.out.println("=== Testing findByName() ===");
        ManufacturerEntity locEntity = manFacade.findByName("James Deli");
        System.out.println("FOUND NAME: " + locEntity.getName());
        assertEquals("James Deli", locEntity.getName());
    }

    /**
     * Test of findByEmail method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testFindByEmail() {
        System.out.println("findByEmail");
        try {
            ManufacturerEntity locEntity = manFacade.findByEmail("www.sbw@example.com");
            System.out.println("FOUND EMAIL: " + locEntity.getEmail());
            assertEquals("www.sbw@example.com", locEntity.getEmail());
        } catch (InvalidEntityException e) {
            fail("Exception was thrown!");
        }
    }

    /**
     * Test of findByEmail method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testFindNullEmail() {
        System.out.println("=== Testing findNullEmail ===");
        try {
            ManufacturerEntity locEntity = manFacade.findByEmail(null);
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }

    /**
     * Test of findByCityAndState method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testFindByCityAndState() throws Exception {
        System.out.println("findByCityAndState");
        String city = "Burlington";
        String state = "OR";
        try {
            List<ManufacturerEntity> returnedList = manFacade.findByCityAndState(city, state);
            for (ManufacturerEntity mfg : returnedList) {
                System.out.println("City = " + mfg.getCity() + "  State = " + mfg.getState());
                assertEquals(city, mfg.getCity());
                assertEquals(state, mfg.getState());
            }
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }
    
        /**
     * Test of findByCityAndState method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testFindByNullCityAndState() throws InvalidEntityException, Exception {
        System.out.println("findByCityAndState");
        String city = "Testington";
        String state = "TZ";
        try {
            assertNull("Should not have found any Manufacturers" , manFacade.findByCityAndState(city, state));
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }

    /**
     * Test of create method, of class ManufacturerEntityFacade.
     *
     * @throws org.usd.csci.manufacturer.InvalidEntityException
     */
    @Test
    public void testCreate() throws InvalidEntityException, Exception {
        System.out.println("=== Test create() Manufacturer ===");
        try {
            //remove entity if it remains from a previous test.
            if (manFacade.findByName(manufacturer.getName()) != null) {
                manFacade.remove(manufacturer);
            }

            manFacade.create(manufacturer);
            System.out.println("CREATED MFG = " + manFacade.findByName(manufacturer.getName()));
            assertNotNull("Manufacturer was NOT Created successfully.", manFacade.findByName(manufacturer.getName()));
        } catch (InvalidEntityException e) {
            System.out.println("EXCEPTION THROWN");
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }
    
        /**
     * Test of create method, of class ManufacturerEntityFacade.
     *
     * @throws org.usd.csci.manufacturer.InvalidEntityException
     */
    @Test
    public void testCreateWithOutRep() throws InvalidEntityException, Exception {
        System.out.println("=== Test create() Manufacturer Without A Rep ===");
        try {
            //remove entity if it remains from a previous test.
            if (manFacade.findByName(manufacturer.getName()) != null) {
                manFacade.remove(manufacturer);
            }
            manufacturer.setRep(null);
            manFacade.create(manufacturer);
            assertNull("Manufacturer should NOT have been  created successfully.", manFacade.findByName(manufacturer.getName()));
        } catch (InvalidEntityException e) {
            System.out.println("EXCEPTION THROWN = " + e.getMessage());
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }

    /**
     * Test of edit method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testEdit() {
        System.out.println("=== Testing edit() of Manufacturer ===");
        try {
            //make sure manufacturer already exists
            if (manFacade.find(manufacturer.getManufacturerId()) == null) {
                manFacade.create(manufacturer);
            }
            manufacturer.setState("IA");
//            manufacturer.setName("Birders United");
            manFacade.edit(manufacturer);

            //make sure MFG was edited
            ManufacturerEntity locEntity = manFacade.findByName(manufacturer.getName());
            assertTrue("MFG was NOT Edited Successfully.", locEntity.getState().equals("IA"));
            System.out.println("EDITED MFG = " + manufacturer.getState());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test of invalidEdit method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testInvalidEdit(){
        System.out.println("=== Testing INVALID edit() of Manufacturer ===");
        try {
            //make sure manufacturer already exists
            if (manFacade.find(manufacturer.getManufacturerId()) == null) {
                manFacade.create(manufacturer);
            }
            manufacturer.setManufacturerId(12345);
//            manufacturer.setEmail("test456@test123.com");
            manFacade.edit(manufacturer);

            //make sure MFG was NOT edited
            ManufacturerEntity locEntity = manFacade.findByName(manufacturer.getName());
//            assertTrue("Manufacturer WAS edited." , manFacade.find(manufacturer.getManufacturerId()).equals(19999996));
        } catch (InvalidEntityException e) {
            System.out.println("EDIT EXCEPTION THROWN: " + e.getMessage());
             assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }

}
