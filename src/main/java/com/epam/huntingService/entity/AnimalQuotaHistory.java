package com.epam.huntingService.entity;

import java.util.Objects;

public class AnimalQuotaHistory {

    private Long id;
    private Integer year;
    private Integer animalQuota;
    private double dailyPrice;
    private double seasonPrice;
    private Long huntingGroundID;
    private Long animalID;
    private HuntingGround huntingGround;
    private Animal animal;

    public HuntingGround getHuntingGround() {
        return huntingGround;
    }

    public void setHuntingGround(HuntingGround huntingGround) {
        this.huntingGround = huntingGround;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public double getSeasonPrice() {
        return seasonPrice;
    }

    public void setSeasonPrice(double seasonPrice) {
        this.seasonPrice = seasonPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getAnimalQuota() {
        return animalQuota;
    }

    public void setAnimalQuota(Integer animalQuota) {
        this.animalQuota = animalQuota;
    }

    public Long getHuntingGroundID() {
        return huntingGroundID;
    }

    public void setHuntingGroundID(Long huntingGroundID) {
        this.huntingGroundID = huntingGroundID;
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
        AnimalQuotaHistory that = (AnimalQuotaHistory) o;
        return Double.compare(that.dailyPrice, dailyPrice) == 0 &&
                Double.compare(that.seasonPrice, seasonPrice) == 0 &&
                id.equals(that.id) &&
                year.equals(that.year) &&
                Objects.equals(animalQuota, that.animalQuota) &&
                huntingGroundID.equals(that.huntingGroundID) &&
                animalID.equals(that.animalID) &&
                Objects.equals(huntingGround, that.huntingGround) &&
                Objects.equals(animal, that.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, animalQuota, dailyPrice, seasonPrice, huntingGroundID, animalID, huntingGround, animal);
    }

    @Override
    public String toString() {
        return "AnimalQuotaHistory{" +
                "id=" + id +
                ", year=" + year +
                ", animalQuota=" + animalQuota +
                ", dailyPrice=" + dailyPrice +
                ", seasonPrice=" + seasonPrice +
                ", huntingGroundID=" + huntingGroundID +
                ", animalID=" + animalID +
                ", huntingGround=" + huntingGround +
                ", animal=" + animal +
                '}';
    }
}
