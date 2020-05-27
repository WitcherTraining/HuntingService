package com.epam.huntingService.entity;

import java.util.Date;
import java.util.Objects;

public class CartItem {
    private Integer ID;
    private String organizationName;
    private String huntingGroundName;
    private String animalName;
    private String permitType;
    private Date animalTermBeginUDate;
    private Date animalTermEndUDate;
    private Integer animalQuota;
    private Double animalCost;
    private Double dailyPrice;
    private Double seasonPrice;
    private Long huntingGroundID;
    private Long animalID;

    public Double getAnimalCost() {
        return animalCost;
    }

    public void setAnimalCost(Double animalCost) {
        this.animalCost = animalCost;
    }

    public Double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public Double getSeasonPrice() {
        return seasonPrice;
    }

    public void setSeasonPrice(Double seasonPrice) {
        this.seasonPrice = seasonPrice;
    }

    public Date getAnimalTermBeginUDate() {
        return animalTermBeginUDate;
    }

    public void setAnimalTermBeginUDate(Date animalTermBeginUDate) {
        this.animalTermBeginUDate = animalTermBeginUDate;
    }

    public Date getAnimalTermEndUDate() {
        return animalTermEndUDate;
    }

    public void setAnimalTermEndUDate(Date animalTermEndUDate) {
        this.animalTermEndUDate = animalTermEndUDate;
    }

    public Integer getAnimalQuota() {
        return animalQuota;
    }

    public void setAnimalQuota(Integer animalQuota) {
        this.animalQuota = animalQuota;
    }

    public Long getAnimalID() {
        return animalID;
    }

    public void setAnimalID(Long animalID) {
        this.animalID = animalID;
    }

    public Long getHuntingGroundID() {
        return huntingGroundID;
    }

    public void setHuntingGroundID(Long huntingGroundID) {
        this.huntingGroundID = huntingGroundID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getHuntingGroundName() {
        return huntingGroundName;
    }

    public void setHuntingGroundName(String huntingGroundName) {
        this.huntingGroundName = huntingGroundName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(ID, cartItem.ID) &&
                Objects.equals(organizationName, cartItem.organizationName) &&
                Objects.equals(huntingGroundName, cartItem.huntingGroundName) &&
                Objects.equals(animalName, cartItem.animalName) &&
                Objects.equals(permitType, cartItem.permitType) &&
                Objects.equals(animalTermBeginUDate, cartItem.animalTermBeginUDate) &&
                Objects.equals(animalTermEndUDate, cartItem.animalTermEndUDate) &&
                Objects.equals(animalQuota, cartItem.animalQuota) &&
                Objects.equals(animalCost, cartItem.animalCost) &&
                Objects.equals(dailyPrice, cartItem.dailyPrice) &&
                Objects.equals(seasonPrice, cartItem.seasonPrice) &&
                Objects.equals(huntingGroundID, cartItem.huntingGroundID) &&
                Objects.equals(animalID, cartItem.animalID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, organizationName, huntingGroundName, animalName, permitType, animalTermBeginUDate, animalTermEndUDate, animalQuota, animalCost, dailyPrice, seasonPrice, huntingGroundID, animalID);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "ID=" + ID +
                ", organizationName='" + organizationName + '\'' +
                ", huntingGroundName='" + huntingGroundName + '\'' +
                ", animalName='" + animalName + '\'' +
                ", permitType='" + permitType + '\'' +
                ", animalTermBeginUDate=" + animalTermBeginUDate +
                ", animalTermEndUDate=" + animalTermEndUDate +
                ", animalQuota=" + animalQuota +
                ", animalCost=" + animalCost +
                ", dailyPrice=" + dailyPrice +
                ", seasonPrice=" + seasonPrice +
                ", huntingGroundID=" + huntingGroundID +
                ", animalID=" + animalID +
                '}';
    }
}
