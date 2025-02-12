package com.seleniumtraining.seleniumapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seleniumtraining.seleniumapp.domain.InterestStatus;
import com.seleniumtraining.seleniumapp.domain.InterestStatusRepository;
import com.seleniumtraining.seleniumapp.domain.InterestStatusType;
import com.seleniumtraining.seleniumapp.domain.Yritys;
import com.seleniumtraining.seleniumapp.domain.YritysRepository;

@Service
public class InterestStatusService {

    @Autowired
    private InterestStatusRepository interestStatusRepository;

    @Autowired
    private YritysRepository yritysRepository;

    public void updateYritysInterestStatus(String yritysName, InterestStatusType interestStatusType) {
        // Etsi yritys nimen perusteella
        Yritys yritys = yritysRepository.findByYritysNimi(yritysName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Yritystä ei löytynyt"));

        // Etsi tai luo uusi InterestStatus
        InterestStatus interestStatus = interestStatusRepository.findByStatus(interestStatusType)
                .orElseGet(() -> {
                    InterestStatus newInterestStatus = new InterestStatus();
                    newInterestStatus.setStatus(interestStatusType);
                    return interestStatusRepository.save(newInterestStatus);
                });

        // Plisää yritys InterestStatusin yritykset-listaan
        interestStatus.addYritys(yritys);

        // Tallenna Yritys ja InterestStatus
        yritysRepository.save(yritys);
        interestStatusRepository.save(interestStatus);
    }
}