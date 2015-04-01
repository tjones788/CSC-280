/*
 * ProdutCodeEntity.java 4/2/2015 11:30pm
 * $Name: Tyler Jones
 */
package org.usd.csci.product;

import java.io.Serializable;
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
@Table(name = "PRODUCT_CODE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCodeEntity.findAll", query = "SELECT p FROM ProductCodeEntity p"),
    @NamedQuery(name = "ProductCodeEntity.findByProdCode", query = "SELECT p FROM ProductCodeEntity p WHERE p.prodCode = :prodCode"),
    @NamedQuery(name = "ProductCodeEntity.findByDiscountCode", query = "SELECT p FROM ProductCodeEntity p WHERE p.discountCode = :discountCode"),
    @NamedQuery(name = "ProductCodeEntity.findByDescription", query = "SELECT p FROM ProductCodeEntity p WHERE p.description = :description")})
public class ProductCodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(max = 2)
    @Pattern(regexp = "^[A-Z]{2}$", message = "Product Code must be Two UPPERCASE characters, A-Z.")
    @Column(name = "PROD_CODE")
    private String prodCode;
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp = "[A-Z]+", message = "Discount code may only contain A-Z characters.") //can't use @pattern on char
    @Column(name = "DISCOUNT_CODE")
    private String discountCode;
    @Size(max = 10)
    @Column(name = "DESCRIPTION")
    private String description;

    public ProductCodeEntity() {
    }

    public ProductCodeEntity(String prodCode) {
        this.prodCode = prodCode;
    }

    public ProductCodeEntity(String prodCode, String discountCode) {
        this.prodCode = prodCode;
        this.discountCode = discountCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        if (this.prodCode == null) {
            return 0;
        }

        int hash = 0;
        hash += (prodCode != null ? prodCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        //if object = null or prodCode = null ;
        if (object == null || prodCode == null) {
            return false;
        }
        //If the object is not a ProductCodeEntity, return False
        if (!(object instanceof ProductCodeEntity)) {
            return false;
        }

        ProductCodeEntity other = (ProductCodeEntity) object;
        if (!this.prodCode.equals(other.prodCode)) {
            return false;
        }
        //ProductCode is equal if the prodCode is equal
        return true;
    }

    @Override
    public String toString() {
        return "Product Code = " + prodCode;
    }

}
