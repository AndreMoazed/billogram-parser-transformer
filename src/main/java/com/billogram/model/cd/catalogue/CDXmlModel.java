package com.billogram.model.cd.catalogue;

import java.math.BigDecimal;
import java.util.Objects;

public class CDXmlModel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private String title;
    private String artist;
    private String country;
    private String company;
    private BigDecimal price;
    private int year;

    // Override the equals method so that filtering can be done
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CDXmlModel)) {
            return false;
        }
        CDXmlModel objCD = (CDXmlModel) obj;
        if (Objects.equals(this.artist, objCD.getArtist()) && Objects.equals(this.title, objCD.getTitle()) &&
                Objects.equals(this.country, objCD.getCountry()) && Objects.equals(this.company, objCD.getCompany()) &&
                Objects.equals(this.price, objCD.getPrice()) && this.year == objCD.getYear()) {
            return true;
        }
        return false;
    }
}
