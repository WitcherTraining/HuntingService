package com.epam.huntingService.entity;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class Organization {

    private Long id;
    private String name;
    private String description;
    private InputStream uploadingLogo;
    private String logo;
    private Integer languageID;
    private List <HuntingGround> huntingGrounds;

    public InputStream getUploadingLogo() {
        return uploadingLogo;
    }

    public void setUploadingLogo(InputStream uploadingLogo) {
        this.uploadingLogo = uploadingLogo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<HuntingGround> getHuntingGrounds() {
        return huntingGrounds;
    }

    public void setHuntingGrounds(List<HuntingGround> huntingGrounds) {
        this.huntingGrounds = huntingGrounds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Integer languageID) {
        this.languageID = languageID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(logo, that.logo) &&
                Objects.equals(languageID, that.languageID) &&
                Objects.equals(huntingGrounds, that.huntingGrounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, logo, languageID, huntingGrounds);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                ", languageID=" + languageID +
                ", huntingGrounds=" + huntingGrounds +
                '}';
    }
}
