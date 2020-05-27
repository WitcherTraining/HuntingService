package com.epam.huntingService.entity;

import java.util.List;
import java.util.Objects;

public class HuntingGround {

    private Long id;
    private String name;
    private String description;
    private String district;
    private Long organizationID;
    private Integer languageID;
    private Organization organization;
    private List<Animal> animals;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
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

    public Long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Long organizationID) {
        this.organizationID = organizationID;
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
        HuntingGround that = (HuntingGround) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                district.equals(that.district) &&
                organizationID.equals(that.organizationID) &&
                languageID.equals(that.languageID) &&
                Objects.equals(organization, that.organization) &&
                Objects.equals(animals, that.animals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, district, organizationID, languageID, organization, animals);
    }

    @Override
    public String toString() {
        return "HuntingGround{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", district='" + district + '\'' +
                ", organizationID=" + organizationID +
                ", languageID=" + languageID +
                ", organization=" + organization +
                ", animals=" + animals +
                '}';
    }
}
