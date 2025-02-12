package com.seleniumtraining.seleniumapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface YritysRepository extends CrudRepository<Yritys, Long> {
    List<Yritys> findByYritysNimi(String yritysNimi);

    List<Yritys> findByYTunnus(String yTunnus);

    List<Yritys> findByPostinumero(String postinumero);

    List<Yritys> findByPostitoimipaikka(String postitoimipaikka);

    List<Yritys> findByPuhelinnumero(String puhelinnumero);

    List<Yritys> findBySahkoposti(String sahkoposti);

    List<Yritys> findByWwwOsoite(String wwwOsoite);

    List<Yritys> findByInterestStatus(InterestStatus interestStatus);

}
