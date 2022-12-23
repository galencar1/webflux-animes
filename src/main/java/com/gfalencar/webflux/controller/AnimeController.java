package com.gfalencar.webflux.controller;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AnimeController {
    private final AnimeRepository animeRepository;

    @RequestMapping(value = "/getAllAnimes", method = RequestMethod.GET)
    public Flux<Anime> listAll(){
        log.info("Buscando animes no DB!");
        return animeRepository.findAll();
    }
}
