package com.epam.huntingService.entity;

import java.util.Date;
import java.util.Objects;

public class AnimalLimitHistory {
    private Long id;
    private Integer year;
    private Integer allLimit;
    private Double monthlyCalculationIndex;
    private Double animalCostInMCI;
    private Double animalCost;
    private Date termBegin;
    private Date termEnd;
    private Long animalID;
    private Animal animal;

    public Double getAnimalCost() {
        return animalCost;
    }

    public void setAnimalCost(Double animalCost) {
        this.animalCost = animalCost;
    }

    public Long getAnimalID() {
        return animalID;
    }

    public void setAnimalID(Long animalID) {
        this.animalID = animalID;
    }

    public Date getTermBegin() {
        return termBegin;
    }

    public void setTermBegin(Date termBegin) {
        this.termBegin = termBegin;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
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

    public Integer getAllLimit() {
        return allLimit;
    }

    public void setAllLimit(Integer allLimit) {
        this.allLimit = allLimit;
    }

    public Double getMonthlyCalculationIndex() {
        return monthlyCalculationIndex;
    }

    public void setMonthlyCalculationIndex(Double monthlyCalculationIndex) {
        this.monthlyCalculationIndex = monthlyCalculationIndex;
    }

    public Double getAnimalCostInMCI() {
        return animalCostInMCI;
    }

    public void setAnimalCostInMCI(Double animalCostInMCI) {
        this.animalCostInMCI = animalCostInMCI;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalLimitHistory that = (AnimalLimitHistory) o;
        return id.equals(that.id) &&
                year.equals(that.year) &&
                Objects.equals(allLimit, that.allLimit) &&
                monthlyCalculationIndex.equals(that.monthlyCalculationIndex) &&
                animalCostInMCI.equals(that.animalCostInMCI) &&
                animalCost.equals(that.animalCost) &&
                termBegin.equals(that.termBegin) &&
                termEnd.equals(that.termEnd) &&
                animalID.equals(that.animalID) &&
                Objects.equals(animal, that.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, allLimit, monthlyCalculationIndex, animalCostInMCI, animalCost, termBegin, termEnd, animalID, animal);
    }

    @Override
    public String toString() {
        return "AnimalLimitHistory{" +
                "id=" + id +
                ", year=" + year +
                ", allLimit=" + allLimit +
                ", monthlyCalculationIndex=" + monthlyCalculationIndex +
                ", animalCostInMCI=" + animalCostInMCI +
                ", animalCost=" + animalCost +
                ", termBegin=" + termBegin +
                ", termEnd=" + termEnd +
                ", animalID=" + animalID +
                ", animal=" + animal +
                '}';
    }
}
