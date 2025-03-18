package com.seleniumtraining.seleniumapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface YritysRepository extends CrudRepository<Yritys, Long> {
    List<Yritys> findByYritysNimi(String yritysNimi);

    List<Yritys> findByPostinumero(String postinumero);

    List<Yritys> findByPostitoimipaikka(String postitoimipaikka);

    List<Yritys> findByInterestStatus(InterestStatusType interestStatus);
}
