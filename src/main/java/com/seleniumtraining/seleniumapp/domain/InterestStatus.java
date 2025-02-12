package com.seleniumtraining.seleniumapp.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class InterestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InterestStatusType status;

    @OneToMany(mappedBy = "interestStatus")
    @JsonManagedReference
    private List<Yritys> yritykset = new ArrayList<>();

    public List<Yritys> getYritykset() {
        return yritykset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InterestStatusType getStatus() {
        return status;
    }

    public void setStatus(InterestStatusType status) {
        this.status = status;
    }

    public void addYritys(Yritys yritys) {
        yritykset.add(yritys);
        yritys.setInterestStatus(this);
    }
}