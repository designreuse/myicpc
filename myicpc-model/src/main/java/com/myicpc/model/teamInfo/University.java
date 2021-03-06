package com.myicpc.model.teamInfo;

import com.myicpc.model.IdGeneratedObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * Represents university which team(s) participate in contest
 * <p/>
 * This is mirror of Institution from CM
 *
 * @author Roman Smetana
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "idgen", sequenceName = "University_id_seq")
public class University extends IdGeneratedObject {
    private static final long serialVersionUID = 2166770559486231031L;

    /**
     * Institution alias id from CM
     */
    private Long externalId;

    /**
     * Institution alias id from CM
     */
    @Column(unique = true)
    private Long externalUnitId;

    /**
     * University name
     */
    private String name;
    /**
     * University short name
     */
    private String shortName;
    /**
     * Hashtag for university
     */
    private String twitterHash;
    /**
     * University homepage
     */
    private String homepageURL;
    /**
     * State, if applicable, of the university
     */
    private String state;
    /**
     * Country of the university
     */
    private String country;
    /**
     * University latitude on the map
     */
    private Double latitude;
    /**
     * University longtitude on the map
     */
    private Double longitude;

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(final Long externalId) {
        this.externalId = externalId;
    }

    public Long getExternalUnitId() {
        return externalUnitId;
    }

    public void setExternalUnitId(Long externalUnitId) {
        this.externalUnitId = externalUnitId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getTwitterHash() {
        return twitterHash;
    }

    public void setTwitterHash(final String twitterHash) {
        this.twitterHash = twitterHash;
    }

    public String getHomepageURL() {
        return homepageURL;
    }

    public void setHomepageURL(final String homepageURL) {
        this.homepageURL = homepageURL;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
