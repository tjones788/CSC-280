/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.edu.manufacturer.entity;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tyler
 */
@Entity
@Table(name = "MANUFACTURER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manufacturer.findAll", query = "SELECT m FROM Manufacturer m"),
    @NamedQuery(name = "Manufacturer.findByManufacturerId", query = "SELECT m FROM Manufacturer m WHERE m.manufacturerId = :manufacturerId"),
    @NamedQuery(name = "Manufacturer.findByName", query = "SELECT m FROM Manufacturer m WHERE m.name = :name"),
    @NamedQuery(name = "Manufacturer.findByAddressline1", query = "SELECT m FROM Manufacturer m WHERE m.addressline1 = :addressline1"),
    @NamedQuery(name = "Manufacturer.findByAddressline2", query = "SELECT m FROM Manufacturer m WHERE m.addressline2 = :addressline2"),
    @NamedQuery(name = "Manufacturer.findByCity", query = "SELECT m FROM Manufacturer m WHERE m.city = :city"),
    @NamedQuery(name = "Manufacturer.findByState", query = "SELECT m FROM Manufacturer m WHERE m.state = :state"),
    @NamedQuery(name = "Manufacturer.findByZip", query = "SELECT m FROM Manufacturer m WHERE m.zip = :zip"),
    @NamedQuery(name = "Manufacturer.findByPhone", query = "SELECT m FROM Manufacturer m WHERE m.phone = :phone"),
    @NamedQuery(name = "Manufacturer.findByFax", query = "SELECT m FROM Manufacturer m WHERE m.fax = :fax"),
    @NamedQuery(name = "Manufacturer.findByEmail", query = "SELECT m FROM Manufacturer m WHERE m.email = :email"),
    @NamedQuery(name = "Manufacturer.findByRep", query = "SELECT m FROM Manufacturer m WHERE m.rep = :rep")})
public class ManufacturerEntity implements Serializable, Comparable<ManufacturerEntity> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MANUFACTURER_ID")
    private Integer manufacturerId;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "ADDRESSLINE1")
    private String addressline1;
    @Size(max = 30)
    @Column(name = "ADDRESSLINE2")
    private String addressline2;
    @Size(max = 25)
    @Column(name = "CITY")
    private String city;
    @Size(max = 2)
    @Pattern(regexp = "[A-Z]{2}",
            message = "State should be two uppercase characters.")
    @Column(name = "STATE")
    private String state;
    @Pattern(regexp = "[0-9]{5}(-[0-9]{4})?$",
            message = "Zipcode should be 5 digits with options four digits (e.g. 99999-9999)")
    @Size(max = 11)
    @Column(name = "ZIP")
    private String zip;
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-([0-9]{4})$",
            message = "Invalid phone/fax format, should be as 999-999-9999")
    @Size(max = 12)
    @Column(name = "PHONE")
    private String phone;
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-([0-9]{4})$",
            message = "Invalid phone/fax format, should be as 999-999-9999")
    @Size(max = 12)
    @Column(name = "FAX")
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 40)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 30)
    @Column(name = "REP")
    private String rep;

    public ManufacturerEntity() {
    }

    public ManufacturerEntity(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    /**
     * hashCode() - using manufacturer id
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manufacturerId != null ? manufacturerId.hashCode() : 0);
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
     * compareto - compare the manufacturer id
     * 
     * @param other
     * @return 
     */
    @Override
    public int compareTo(ManufacturerEntity other) {
        return this.manufacturerId = other.getManufacturerId();
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
