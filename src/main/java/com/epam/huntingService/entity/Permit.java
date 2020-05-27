package com.epam.huntingService.entity;

import java.util.Date;
import java.util.Objects;

public class Permit {

    private Long id;
    private Date orderDate;
    private Integer countOrderedAnimals;
    private String permitType;
    private Date huntingDay;
    private Long userID;
    private Long animalID;
    private Long huntingGroundID;
    private Long organizationID;
    private Double totalCost;
    private User user;
    private Animal animal;
    private HuntingGround huntingGround;
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Long organizationID) {
        this.organizationID = organizationID;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public HuntingGround getHuntingGround() {
        return huntingGround;
    }

    public void setHuntingGround(HuntingGround huntingGround) {
        this.huntingGround = huntingGround;
    }

    public Date getHuntingDay() {
        return huntingDay;
    }

    public void setHuntingDay(Date huntingDay) {
        this.huntingDay = huntingDay;
    }

    public Long getHuntingGroundID() {
        return huntingGroundID;
    }

    public void setHuntingGroundID(Long huntingGroundID) {
        this.huntingGroundID = huntingGroundID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCountOrderedAnimals() {
        return countOrderedAnimals;
    }

    public void setCountOrderedAnimals(Integer countOrderedAnimals) {
        this.countOrderedAnimals = countOrderedAnimals;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getAnimalID() {
        return animalID;
    }

    public void setAnimalID(Long animalID) {
        this.animalID = animalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permit permit = (Permit) o;
        return id.equals(permit.id) &&
                orderDate.equals(permit.orderDate) &&
                countOrderedAnimals.equals(permit.countOrderedAnimals) &&
                permitType.equals(permit.permitType) &&
                Objects.equals(huntingDay, permit.huntingDay) &&
                userID.equals(permit.userID) &&
                animalID.equals(permit.animalID) &&
                huntingGroundID.equals(permit.huntingGroundID) &&
                organizationID.equals(permit.organizationID) &&
                totalCost.equals(permit.totalCost) &&
                Objects.equals(user, permit.user) &&
                Objects.equals(animal, permit.animal) &&
                Objects.equals(huntingGround, permit.huntingGround) &&
                Objects.equals(organization, permit.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, countOrderedAnimals, permitType, huntingDay, userID, animalID, huntingGroundID, organizationID, totalCost, user, animal, huntingGround, organization);
    }

    @Override
    public String toString() {
        return "Permit{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", countOrderedAnimals=" + countOrderedAnimals +
                ", permitType='" + permitType + '\'' +
                ", huntingDay=" + huntingDay +
                ", userID=" + userID +
                ", animalID=" + animalID +
                ", huntingGroundID=" + huntingGroundID +
                ", organizationID=" + organizationID +
                ", totalCost=" + totalCost +
                ", user=" + user +
                ", animal=" + animal +
                ", huntingGround=" + huntingGround +
                ", organization=" + organization +
                '}';
    }
}
