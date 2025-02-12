package com.seleniumtraining.seleniumapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seleniumtraining.seleniumapp.domain.InterestStatus;
import com.seleniumtraining.seleniumapp.domain.InterestStatusRepository;

@RestController
@RequestMapping("/intereststatus")
public class InterestStatusController {

    private InterestStatusRepository interestStatusRepository;

    public InterestStatusController(InterestStatusRepository interestStatusRepository) {
        this.interestStatusRepository = interestStatusRepository;
    }

    @GetMapping
    public Iterable<InterestStatus> getAllInterestStatuses() {
        return interestStatusRepository.findAll();
    }

}
