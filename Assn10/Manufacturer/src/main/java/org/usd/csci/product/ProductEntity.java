/*
 * ProductEntity.java 4/2/2015 11:30pm
 * $Name: Tyler Jones
 */
package org.usd.csci.product;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.usd.csci.manufacturer.ManufacturerEntity;

/**
 * This class represents a Product Entity.  Defines business rules, getters and setters, and comparison methods.
 * 
 * @author Tyler
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductEntity.findAll", query = "SELECT p FROM ProductEntity p"),
    @NamedQuery(name = "ProductEntity.findByProductId", query = "SELECT p FROM ProductEntity p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductEntity.findByPurchaseCost", query = "SELECT p FROM ProductEntity p WHERE p.purchaseCost = :purchaseCost"),
    @NamedQuery(name = "ProductEntity.findByQuantityOnHand", query = "SELECT p FROM ProductEntity p WHERE p.quantityOnHand = :quantityOnHand"),
    @NamedQuery(name = "ProductEntity.findByMarkup", query = "SELECT p FROM ProductEntity p WHERE p.markup = :markup"),
    @NamedQuery(name = "ProductEntity.findByAvailable", query = "SELECT p FROM ProductEntity p WHERE p.available = :available"),
    @NamedQuery(name = "ProductEntity.findByDescription", query = "SELECT p FROM ProductEntity p WHERE p.description = :description")})
public class ProductEntity implements Serializable, Comparable<ProductEntity> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    
    //Purchase cost must be greater than 0, with 2 places to the right of the decimal.
    @DecimalMin("0.01")
    @Column(name = "PURCHASE_COST")
    private BigDecimal purchaseCost;

    @Min(0) //QOH cannot be negative
    @Column(name = "QUANTITY_ON_HAND")
    private Integer quantityOnHand;
    
    //markup cannot be negative or greater than 100, 2 digits to the right of the decimal
    @DecimalMin("0.00")
    @DecimalMax("100.00")
    @Column(name = "MARKUP")
    private BigDecimal markup;

    @Size(max = 5)
    @Column(name = "AVAILABLE")
    private String available;

    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "MANUFACTURER_ID")
    @ManyToOne(optional = false)
    private ManufacturerEntity manufacturerId;

    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "PROD_CODE")
    @ManyToOne(optional = false)
    private ProductCodeEntity productCode;

    public ProductEntity() {
    }

    public ProductEntity(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public BigDecimal getMarkup() {
        return markup;
    }

    public void setMarkup(BigDecimal markup) {
        this.markup = markup;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ManufacturerEntity getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(ManufacturerEntity manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public ProductCodeEntity getProductCode() {
        return productCode;
    }

    public void setProductCode(ProductCodeEntity productCode) {
        this.productCode = productCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    /**
     * Two products are equal if their IDs are equal
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {

        if (object == null || productId == null) {
            return false;
        }

        if (!(object instanceof ProductEntity)) {
            return false;
        }
        ProductEntity other = (ProductEntity) object;
        if (!this.productId.equals(other.productId)) {
            return false;
        }
        return true;
    }

    /**
     * Should return the product's ID appended to its description
     *
     * @return
     */
    @Override
    public String toString() {
        return this.productId + this.description;
    }

    @Override
    public int compareTo(ProductEntity other) {
        return this.productId.compareTo(other.productId);
    }

}
