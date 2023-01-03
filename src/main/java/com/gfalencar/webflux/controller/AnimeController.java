package com.gfalencar.webflux.controller;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.repository.AnimeRepository;
import com.gfalencar.webflux.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AnimeController {
    private final AnimeService animeService;

    @RequestMapping(value = "/getAllAnimes", method = RequestMethod.GET)
    public Flux<Anime> listAll(){
        return animeService.findAll();
    }

    @RequestMapping(value = "getAnime/{id}", method = RequestMethod.GET)
    public Mono<Anime> listById(@PathVariable String id){
        return animeService.findById(id);
    }

    @RequestMapping(value = "saveAnime", method = RequestMethod.POST)
    public Mono<Anime> saveAnime(@RequestBody Anime anime) {
        return animeService.save(anime).log();
    }
}
