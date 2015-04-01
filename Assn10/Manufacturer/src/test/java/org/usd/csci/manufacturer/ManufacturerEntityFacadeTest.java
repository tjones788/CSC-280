/*
 * $ID: ManufacturerEntityFacadeTest.java Version 1.0 - 3/19/2015 11:30pm
 * $Name: $Tyler Jones
 */
package org.usd.csci.manufacturer;

import org.usd.csci.utility.InvalidEntityException;
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
 * This JUnit Test class is used to test the ManufacturerEntityFacade methods.  Includes all find methods, create, and edit.  Valid and Invalid attempts.
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
        manufacturer.setAddressLine1("123 Main St");
        manufacturer.setAddressLine2("PO BOX 123");
        manufacturer.setCity("Vermillion");
        manufacturer.setState("SD");
        manufacturer.setZip("57069-1234");
    }

    @After
    public void tearDown() {
        ManufacturerEntity locEntity = manFacade.find(manufacturer.getManufacturerId());
        if (locEntity != null) {
            manFacade.remove(manufacturer); //removes manmufacturer after each test
        }
    }

    /*
     * Test of findByName method, of class ManufacturerEntityFacade.
     *
     */
    @Test
    public void testFindByName() {
        System.out.println("=== Testing findByName() ===");
        try {
            //Make sure manufacturer exists
            if (manFacade.findByName(manufacturer.getName()) == null) {
                manFacade.create(manufacturer);
            }
            ManufacturerEntity locEntity = manFacade.findByName(manufacturer.getName());
            assertEquals(manufacturer.getName(), locEntity.getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /*
     * Test of findByName method, of class ManufacturerEntityFacade. Tries to
     * pass a null name to the findByName method(). Expects an invalid 
     */
    @Test
    public void testFindByNullName() {
        System.out.println("=== Testing findByNullName() ===");
        try {
            //Make sure manufacturer exists
            if (manFacade.findByName(manufacturer.getName()) == null) {
                manFacade.create(manufacturer);
            }
            ManufacturerEntity locEntity = manFacade.findByName(null);
            fail("Exception should have been thrown!");
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("FindByName Parameter is missing."));
        }
    }

    /**
     * Test of findByEmail method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testFindByEmail() {
        System.out.println("findByEmail");
        try {
            //make sure manufacturer already exists
            if (manFacade.find(manufacturer.getManufacturerId()) == null) {
                manFacade.create(manufacturer);
            }
            ManufacturerEntity locEntity = manFacade.findByEmail(manufacturer.getEmail());
            System.out.println("FOUND EMAIL: " + locEntity.getEmail());
            assertEquals(manufacturer.getEmail(), locEntity.getEmail());
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
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("Find By Email Parameter cannot be null."));
        }
    }

    /**
     * Test of findByCityAndState method, of class ManufacturerEntityFacade.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByCityAndState() throws Exception {
        System.out.println("=== Testing findByCityAndState ===");
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
            fail("Exception was thrown!");
        }
    }

    /**
     * Test of findByCityAndState method, of class ManufacturerEntityFacade.
     * 
     * @throws org.usd.csci.manufacturer.InvalidEntityException
     */
    @Test
    public void testFindByNullCityAndState() throws InvalidEntityException, Exception {
        System.out.println("=== Testing NULL findByCityAndState ===");
        String city = "Testingtown";
        String state = "TZ";
        try {
            assertNull("Should not have found any Manufacturers", manFacade.findByCityAndState(city, state));
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
        }
    }

    /**
     * Test of create method, of class ManufacturerEntityFacade.
     *
     */
    @Test
    public void testCreate() {
        System.out.println("=== Test create() Manufacturer ===");
        ManufacturerEntity locEntity = manFacade.find(manufacturer.getManufacturerId());
        //check if id is unique, delete if it's not
        if (locEntity != null) {
            manFacade.remove(manufacturer);
        }
        try {
            //look for MFG by name
            locEntity = manFacade.findByName(manufacturer.getName());
            if (locEntity != null) {
                manFacade.remove(locEntity);
            }
            //look for MFG by name
            locEntity = manFacade.findByName(manufacturer.getName());
            if (locEntity != null) {
                manFacade.remove(locEntity);
            }
            locEntity = manFacade.findByEmail(manufacturer.getEmail());
            if (locEntity != null) {
                manFacade.remove(locEntity);
            }
            //make sure rep is not null
            if (manufacturer.getRep() == null) {
                throw new InvalidEntityException("Manufacturer Rep must be provided.");
            }
            //make sure manufacturer is not null
            if (manufacturer == null) {
                throw new InvalidEntityException("Manufacturer is null.");
            }
            manFacade.create(manufacturer);
            assertNotNull("Manufacturer was NOT Created successfully.", manFacade.find(manufacturer.getManufacturerId())); //verify the manufacturer was created
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            fail(e.getMessage());
        }
    }

    /**
     * Test of create method, of class ManufacturerEntityFacade.
     *
     */
    @Test
    public void testCreateNullMfg() {
        System.out.println("=== Test create() Manufacturer ===");
        ManufacturerEntity locEntity = manFacade.find(manufacturer.getManufacturerId());
        //look for id, if it exists, delete it
        if (locEntity != null) {
            manFacade.remove(manufacturer);
        }
        try {
            locEntity = null;
            //make sure manufacturer is not null
            if (locEntity == null) {
                throw new InvalidEntityException("Manufacturer is null.");
            }
            fail("Exception should have been thrown!");
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("Manufacturer is null."));
        }
    }

    /**
     * Test of create method, of class ManufacturerEntityFacade. Tests creating
     * a MFG with a non-unique id
     */
    @Test
    public void testDuplicateIdCreate() {
        System.out.println("=== Test DuplicateIdCreate() Manufacturer ===");

        try {
            ManufacturerEntity locEntity = manFacade.findByName(manufacturer.getName());
            if (locEntity != null) {
                manFacade.remove(locEntity); //remove entity if it remains from a previous test.
            }
            //create the manufacturer
            manFacade.create(manufacturer);
            assertNotNull("MFG should have been Created", manFacade.find(manufacturer.getManufacturerId())); //assert mfg was created
            //try to create a duplicate mfg, expect exception
            manFacade.create(manufacturer);
            fail("Exception should have been thrown. ID is NOT unique.");
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("id must be unique."));
        }
    }

    /**
     * Test of create method, of class ManufacturerEntityFacade. Tests creating
     * a MFG with a non-unique Name
     *
     * @throws org.usd.csci.manufacturer.InvalidEntityException
     */
    @Test
    public void testDuplicateNameCreate() throws Exception {
        System.out.println("=== Test DuplicateNameCreate() Manufacturer ===");
        try {
            //remove entity if it remains from a previous test.
            if (manFacade.find(manufacturer.getManufacturerId()) != null) {
                manFacade.remove(manufacturer);
            }
            //create the manufacturer
            manFacade.create(manufacturer);
            assertNotNull("MFG should have been Created", manFacade.find(manufacturer.getManufacturerId())); //assert mfg was created
            //modify the mfg
            manufacturer.setManufacturerId(19999997); //set unique ID, try to create MFG with same name, diff ID
            manufacturer.setName("Test MFG");
            manFacade.create(manufacturer);
            fail("Exception should have been thrown. MFG Name is NOT unique.");
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("name must be unique."));
        }
    }

    /**
     * Test of create method, of class ManufacturerEntityFacade. Tests creating
     * a MFG with a non-unique email
     *
     */
    @Test
    public void testDuplicateEmailCreate() {
        System.out.println("=== Test DuplicateEmailCreate() Manufacturer ===");
        try {
            //remove entity if it remains from a previous test.
            if (manFacade.findByName(manufacturer.getName()) != null) {
                manFacade.remove(manufacturer);
            }
            //create the manufacturer
            manFacade.create(manufacturer);
            assertNotNull("MFG should have been Created", manFacade.find(manufacturer.getManufacturerId())); //assert mfg was created
            //try to create a duplicate mfg, expect exception
            manufacturer.setManufacturerId(19999998);
            manufacturer.setName("Test MFG2");
            manFacade.create(manufacturer);
            fail("Exception should have been thrown. MFG Email is NOT unique.");
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("email address must be unique"));
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
            ManufacturerEntity locEntity = manFacade.findByName(manufacturer.getName());
            if (locEntity != null) {
                manFacade.remove(locEntity); //remove entity if it remains from a previous test.
            }
            //set rep to null
            manufacturer.setRep(null);
            //try to create a duplicate mfg, expect exception
            manFacade.create(manufacturer);
            fail("Exception should have been thrown. Rep is REQUIRED.");
        } catch (InvalidEntityException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("Rep is a required field."));
        }
    }

    /**
     * Test of edit method, of class ManufacturerEntityFacade.
     */
    @Test
    public void testEdit() {
        System.out.println("=== Testing edit() of Manufacturer ===");
        try {
            ManufacturerEntity locEntity = manFacade.find(manufacturer.getManufacturerId());
            //check if Id is unique
            if (locEntity != null) {
                manFacade.remove(locEntity);
            }
            //look for MFG by name, delete if exists
            locEntity = manFacade.findByName(manufacturer.getName());
            if (locEntity != null) {
                manFacade.remove(locEntity);
            }
            locEntity = manFacade.findByEmail(manufacturer.getEmail());
            if (locEntity != null) {
                manFacade.remove(locEntity);
            }

            //make sure manufacturer already exists
            if (manFacade.find(manufacturer.getManufacturerId()) == null) {
                manFacade.create(manufacturer); //create will throw InvalidEntityException if not unique.
            }
            manufacturer.setState("IA");
            manufacturer.setZip("12345");
            manFacade.edit(manufacturer);

            //make sure MFG was edited
            locEntity = manFacade.find(manufacturer.getManufacturerId());
            assertTrue("MFG was NOT Edited Successfully.", locEntity.getState().equals("IA"));
            assertTrue("MFG was NOT Edited Successfully.", locEntity.getZip().equals("12345"));
            System.out.println("EDITED MFG = " + manufacturer.getState());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test of invalidEdit method, of class ManufacturerEntityFacade. Tests editing an entity with the same name, diff id, diff email
     */
    @Test
    public void testInvalidEdit() {
        System.out.println("=== Testing INVALID edit() of Manufacturer ===");
        try {
            //make sure manufacturer already exists
            if (manFacade.find(manufacturer.getManufacturerId()) == null) {
                manFacade.create(manufacturer);
            }
            manufacturer.setManufacturerId(12345);
            manufacturer.setEmail("test456@test123.com");
            manFacade.edit(manufacturer);

            //make sure MFG was NOT edited
            ManufacturerEntity locEntity = manFacade.findByName(manufacturer.getName());
            assertTrue("Manufacturer WAS edited.", locEntity.getManufacturerId().equals(19999996));
            fail("Exception should have been thrown!");
        } catch (InvalidEntityException e) {
            System.out.println("EDIT EXCEPTION THROWN: " + e.getMessage());
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidEntityException);
            String msg = e.getMessage();
            assertTrue("Wrong exception thrown." , msg.contains("Manufacturer")); 
           
        }
    }
}
