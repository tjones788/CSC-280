/*
 * ManufacturerEntity.java 2/17/2015 11:30pm
 * $Name: Tyler Jones
 */
package org.usd.csci.manufacturer;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.usd.csci.product.ProductEntity;

/**
 * Manufacturer Entity class created from sample database. Represents a row in
 * the manufacturer table.
 *
 * @author Tyler
 */
@Entity
@Table(name = "MANUFACTURER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manufacturer.findAll", query = "SELECT m FROM ManufacturerEntity m"),
    @NamedQuery(name = "Manufacturer.findByManufacturerId", query = "SELECT m FROM ManufacturerEntity m WHERE m.manufacturerId = :manufacturerId"),
    @NamedQuery(name = "Manufacturer.findByName", query = "SELECT m FROM ManufacturerEntity m WHERE m.name = :name"),
    @NamedQuery(name = "Manufacturer.findByCity", query = "SELECT m FROM ManufacturerEntity m WHERE m.city = :city"),
    @NamedQuery(name = "Manufacturer.findByState", query = "SELECT m FROM ManufacturerEntity m WHERE m.state = :state"),
    @NamedQuery(name = "Manufacturer.findByZip", query = "SELECT m FROM ManufacturerEntity m WHERE m.zip = :zip"),
    @NamedQuery(name = "Manufacturer.findByPhone", query = "SELECT m FROM ManufacturerEntity m WHERE m.phone = :phone"),
    @NamedQuery(name = "Manufacturer.findByEmail", query = "SELECT m FROM ManufacturerEntity m WHERE m.email = :email"),
    @NamedQuery(name = "Manufacturer.findByRep", query = "SELECT m FROM ManufacturerEntity  m WHERE m.rep = :rep"),
    @NamedQuery(name = "Manufacturer.findByCityState", query = "SELECT m FROM ManufacturerEntity m WHERE m.city = :city AND m.state = :state")})
public class ManufacturerEntity implements Serializable, Comparable<ManufacturerEntity> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull(message = "Manufacturer ID is Required")
    @Column(name = "MANUFACTURER_ID")
    private Integer manufacturerId;
    @Size(max = 30)
    @NotNull(message = "Manufacturer Name is Required and must be Unique")
    @Pattern(regexp = "^[A-Z]{1}\\w+((\\s|-)+\\w+)*", message = "Name should start with an Upper Case letter, optional space or - and more letters.")
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "ADDRESSLINE1")
    private String addressLine1;
    @Size(max = 30)
    @Column(name = "ADDRESSLINE2")
    private String addressLine2;
    @Size(max = 25)
    @Pattern(regexp = "^[A-Z]{1}\\w+((\\s|-)+\\w+)*", message = "City should start with an Upper Case letter, optional space or - and more letters.")
    @Column(name = "CITY")
    private String city;
    @Size(max = 2)
    @Pattern(regexp = "^((A[LKSZR])|(C[AOT])|(D[EC])|(F[ML])|(G[AU])|(HI)|(I[DLNA])|(K[SY])|(LA)|(M[EHDAINSOT])|(N[EVHJMYCD])|(MP)|(O[HKR])|(P[WAR])|(RI)|(S[CD])|(T[NX])|(UT)|(V[TIA])|(W[AVIY]))$", message = "State should be two uppercase characters, and a valid Abbreviation.")
    @Column(name = "STATE")
    private String state;
    @Pattern(regexp = "[0-9]{5}(-[0-9]{4})?$", message = "Zipcode should be 5 digits with options four digits (e.g. 99999-9999)")
    @Size(max = 11)
    @Column(name = "ZIP")
    private String zip;
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-([0-9]{4})$", message = "Invalid phone/fax format, should be as 999-999-9999")
    @Size(max = 12)
    @Column(name = "PHONE")
    private String phone;
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-([0-9]{4})$", message = "Invalid phone/fax format, should be as 999-999-9999")
    @Size(max = 12)
    @Column(name = "FAX")
    private String fax;                                    
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 40)
    @NotNull(message = "Manufacturer Email is Required and Must be Unique")
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 30)
    @NotNull(message = "Manufacturer Rep is Required.")
    @Pattern(regexp = "^([a-zA-Z]+(?:\\.)?(?:(?:'| )[a-zA-Z]+(?:\\.)?)*)$", message = "Invalid Rep Name.")
    @Column(name = "REP")
    private String rep;

    //Default Constructor
    public ManufacturerEntity() {
    }

    /**
     * Default with id
     *
     * @param manufacturerId
     */
    public ManufacturerEntity(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /*
     * Get Manufacturer ID
     * 
     * @return manufacturerId
     */
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    /*
     * Set ManufacturerID
     *
     * @param manufacturerId
     */
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /*
     * Get Manufacturer Name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /*
     * Set Manufacturer Name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Get Manufacturer Address Line 1
     *
     * @return addressline1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /*
     * Set Manufactuer addressLine1
     *
     * @param addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /*
     * Get Manufacturer Address Line 2
     *
     * @return addressline2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /*
     * Set Manufacturer addressLine2
     *
     * @param addressLine2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /*
     * Get Manufacturer City
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }

    /*
     * Set Manufacturer City
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /*
     * Get Manufacturer State
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /*
     * Set Manufacturer State
     *
     * @param statee
     */
    public void setState(String state) {
        this.state = state;
    }

    /*
     * Get Manufacturer Zip
     *
     * @return zip
     */
    public String getZip() {
        return zip;
    }

    /*
     * Set Manufacturer Zip
     *
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /*
     * Get Manufacturer Phone Number
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /*
     * Set Manufacturer Phone #
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*
     * Get Manufacturer Fax #
     *
     * @return fax
     */
    public String getFax() {
        return fax;
    }

    /*
     * Set Manufacturer Fax #
     *
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /*
     * Get Manufacturer Email Address
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /*
     * Set Manufacturer Email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Get Manufacturer Rep
     *
     * @return rep
     */
    public String getRep() {
        return rep;
    }

    /*
     * Set Manufacturer Rep
     *
     * @param rep
     */
    public void setRep(String rep) {
        this.rep = rep;
    }

    /**
     * hashCode() - using manufacturer Name
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    /**
     * Manufacturers are equal if their names are equal
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManufacturerEntity)) {
            return false;
        }
        ManufacturerEntity other = (ManufacturerEntity) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    /**
     * compareTo - compare the manufacturer id
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(ManufacturerEntity other) {
        if (other == null || this.name == null) {
            return -1;
        }
        return this.name.compareTo(other.getName());
    }

    /**
     * toString - display the manufacturer name
     *
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }
}
