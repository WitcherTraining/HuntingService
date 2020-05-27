package com.epam.huntingService.entity;

import java.util.List;
import java.util.Objects;

public class Animal {

    private Long id;
    private String name;
    private Integer languageID;
    private AnimalLimitHistory animalLimitHistory;
    private AnimalQuotaHistory animalQuotaHistory;
    private List<AnimalQuotaHistory> animalQuotaHistories;
    private List <AnimalLimitHistory> animalLimitHistories;

    public AnimalQuotaHistory getAnimalQuotaHistory() {
        return animalQuotaHistory;
    }

    public void setAnimalQuotaHistory(AnimalQuotaHistory animalQuotaHistory) {
        this.animalQuotaHistory = animalQuotaHistory;
    }

    public List<AnimalQuotaHistory> getAnimalQuotaHistories() {
        return animalQuotaHistories;
    }

    public void setAnimalQuotaHistories(List<AnimalQuotaHistory> animalQuotaHistories) {
        this.animalQuotaHistories = animalQuotaHistories;
    }

    public AnimalLimitHistory getAnimalLimitHistory() {
        return animalLimitHistory;
    }

    public void setAnimalLimitHistory(AnimalLimitHistory animalLimitHistory) {
        this.animalLimitHistory = animalLimitHistory;
    }

    public List<AnimalLimitHistory> getAnimalLimitHistories() {
        return animalLimitHistories;
    }

    public void setAnimalLimitHistories(List<AnimalLimitHistory> animalLimitHistories) {
        this.animalLimitHistories = animalLimitHistories;
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
        Animal animal = (Animal) o;
        return id.equals(animal.id) &&
                name.equals(animal.name) &&
                languageID.equals(animal.languageID) &&
                Objects.equals(animalLimitHistory, animal.animalLimitHistory) &&
                Objects.equals(animalQuotaHistory, animal.animalQuotaHistory) &&
                Objects.equals(animalQuotaHistories, animal.animalQuotaHistories) &&
                Objects.equals(animalLimitHistories, animal.animalLimitHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, languageID, animalLimitHistory, animalQuotaHistory, animalQuotaHistories, animalLimitHistories);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", languageID=" + languageID +
                ", animalLimitHistory=" + animalLimitHistory +
                ", animalQuotaHistory=" + animalQuotaHistory +
                ", animalQuotaHistories=" + animalQuotaHistories +
                ", animalLimitHistories=" + animalLimitHistories +
                '}';
    }
}
