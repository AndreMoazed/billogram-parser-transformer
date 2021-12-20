package com.billogram.model.cd.catalogue;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CDOutputJsonModel {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    @JsonProperty("performer_lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty("performer_forename")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @JsonProperty("price_no_vat")
    public BigDecimal getPriceNoVat() {
        return priceNoVat;
    }

    public void setPriceNoVat(BigDecimal priceNoVat) {
        this.priceNoVat = priceNoVat;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    @JsonProperty("tot")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    private String title;
    private int published;
    private String lastname;
    private String firstname;
    private String origin;
    private BigDecimal priceNoVat;
    private BigDecimal vat;
    private BigDecimal total;
}
