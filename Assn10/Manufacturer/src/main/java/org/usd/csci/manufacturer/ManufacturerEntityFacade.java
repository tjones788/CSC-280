/*
 * $ID: ManufacturerEntityFacade.java Version 1.0 - 3/3/2015 11:30pm
 * $Name: $Tyler Jones
 */
package org.usd.csci.manufacturer;

import org.usd.csci.utility.AbstractFacade;
import org.usd.csci.utility.InvalidEntityException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Manufacturer Session Bean/Facade - Used to perform CRUD operations on an
 * entity.
 *
 * @author Tyler
 */
@Stateless
public class ManufacturerEntityFacade extends AbstractFacade<ManufacturerEntity> {

    @PersistenceContext(unitName = "ManufacturerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManufacturerEntityFacade() {
        super(ManufacturerEntity.class);
    }

    /**
     * findByName - find all records with the name provided by parameter -
     * should always either be empty or one record because name must be unique.
     *
     * @param name
     * @return
     * @throws InvalidEntityException
     */
    public ManufacturerEntity findByName(String name) throws InvalidEntityException, NoResultException {
        if (name == null) {
            throw new InvalidEntityException("FindByName Parameter is missing.");
        }
        try {
            TypedQuery<ManufacturerEntity> cq = em.createNamedQuery("Manufacturer.findByName", ManufacturerEntity.class).setParameter("name", name);
            return cq.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * FindByEmail - Find Manufactuer by Email.  Email is required and must be unique. 
     *
     * @param email
     * @return ManufacturerEntity - A single ManufacturerEnity, found by unique email.
     * @throws InvalidEntityException
     * @throws NoResultException
     */
    public ManufacturerEntity findByEmail(String email) throws InvalidEntityException, NoResultException {
        if (email == null) {
            throw new InvalidEntityException("Find By Email Parameter cannot be null.");
        }
        try {
            TypedQuery<ManufacturerEntity> query = em.createNamedQuery("Manufacturer.findByEmail", ManufacturerEntity.class).setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /*
     *  FindByCityAndState - Find all manufacturers in a given City, State.  Returns a list of Manufacturers.
     *
     * @param city
     * @param state
     * @return
     * @throws InvalidEntityException
     * @throws Exception
     */
    public List<ManufacturerEntity> findByCityAndState(String city, String state) throws InvalidEntityException, Exception {
        if (city == null || state == null) {
            throw new InvalidEntityException("City and State parameters MUST be provided for findByCityAndState ()");
        }
        try {
            TypedQuery<ManufacturerEntity> query = em.createNamedQuery("Manufacturer.findByCityState", ManufacturerEntity.class);
            query.setParameter("city", city).setParameter("state", state);

            List<ManufacturerEntity> foundList = query.getResultList();
            if (foundList.isEmpty()) {
                return null;
            } else {
                return foundList;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *  Create - Create a a new Manufacturer.  ID, Name, Email must be unique.  Rep is required.
     *
     * @param manufacturer
     * @throws InvalidEntityException
     * @throws Exception
     */
    @Override
    public void create(ManufacturerEntity manufacturer) throws InvalidEntityException {
        if (manufacturer == null) {
            throw new InvalidEntityException("Manufacturer cannot be null");
        }
        //make sure id, name, and email are NOT NULL
        if (manufacturer.getManufacturerId() == null || manufacturer.getName() == null || manufacturer.getEmail() == null) {
            throw new InvalidEntityException("Id, Name, and Email are required fields for Manufacturer.");
        }
        //unique id
        ManufacturerEntity locEntity = find(manufacturer.getManufacturerId());
        if (locEntity != null) {
            throw new InvalidEntityException("Manufacturer id must be unique.");
        }
        //unique name and not null
        locEntity = findByName(manufacturer.getName());
        if (locEntity != null) {
            throw new InvalidEntityException("Manufacturer name must be unique.");
        }
        //unique email
        locEntity = findByEmail(manufacturer.getEmail());
        if (locEntity != null) {
            throw new InvalidEntityException("Manufacturer email address must be unique.");
        }
        //make sure rep has been set
        if (manufacturer.getRep() == null) {
            throw new InvalidEntityException("Rep is a required field.");
        }
        super.create(manufacturer);
    }

    /*
     *  Edit - Edit an existing Manufacturer.  ID, Name, Email must be unique.  Rep is required.
     *
     * @param manufacturer
     * @throws InvalidEntityException
     * @throws Exception
     */
    @Override
    public void edit(ManufacturerEntity manufacturer) throws InvalidEntityException {
        if (manufacturer == null) {
            throw new InvalidEntityException("Manufacturer CANNOT be null.");
        }

        //look for record with name(findbyname)
        ManufacturerEntity locEntity = findByName(manufacturer.getName());
        //If Manufacturer Name Exists, but does not have the same ID, it is not unique.
        if (locEntity != null) {
            if (!locEntity.getManufacturerId().equals(manufacturer.getManufacturerId())) {
                throw new InvalidEntityException("Manufacturer is NOT Unique.");
            }
        }
        //If Manufacturer Email Exists, but does not have the same ID, it is not unique.
        locEntity = findByEmail(manufacturer.getEmail());
        if (locEntity != null) {
            if (!locEntity.getManufacturerId().equals(manufacturer.getManufacturerId())) {
                throw new InvalidEntityException("Manufacturer is NOT Unique.");
            }
        }
        //Perform Edit
        super.edit(manufacturer);
    }
}
