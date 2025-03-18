package com.seleniumtraining.seleniumapp.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seleniumtraining.seleniumapp.domain.Yritys;
import com.seleniumtraining.seleniumapp.domain.YritysRepository;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/yritykset")
public class YritysController {

    private YritysRepository yritysRepository;

    public YritysController(YritysRepository yritysRepository) {
        this.yritysRepository = yritysRepository;
    }

    @GetMapping
    public Iterable<Yritys> getAllYritykset() {
        return yritysRepository.findAll();
    }

}
