package com.seleniumtraining.seleniumapp.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface InterestStatusRepository extends CrudRepository<InterestStatus, Long> {
    Optional<InterestStatus> findByStatus(InterestStatusType interestStatusType);

}
