package com.gfalencar.webflux.repository;

import com.gfalencar.webflux.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AnimeRepository extends ReactiveCrudRepository<Anime, String> {
    @Override
    Mono<Anime> findById(String id);

    @Override
    Mono<Anime> save(Anime anime);
}
