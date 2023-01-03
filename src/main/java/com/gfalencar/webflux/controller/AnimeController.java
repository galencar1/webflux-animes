package com.gfalencar.webflux.controller;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AnimeController {
    private final AnimeService animeService;

    @RequestMapping(value = "/getAllAnimes", method = RequestMethod.GET)
    public Flux<Anime> listAll(){
        return animeService.findAll();
    }

    @RequestMapping(value = "/getAnime/{id}", method = RequestMethod.GET)
    public Mono<Anime> listById(@PathVariable String id){
        return animeService.findById(id);
    }

    @RequestMapping(value = "/saveAnime", method = RequestMethod.POST)
    public Mono<Anime> saveAnime(@Valid @RequestBody Anime anime) {
        return animeService.save(anime).log();
    }

    @RequestMapping(value = "/updateAnime", method = RequestMethod.PUT)
    public Mono<Void> updateAnime(@Valid @RequestBody Anime anime){
        return animeService.update(anime);
    }

    @RequestMapping(value = "deleteAnime/{id}", method = RequestMethod.DELETE)
    public Mono<Void> deleteAnime(@PathVariable String id){
        return animeService.delete(id);
    }
}
