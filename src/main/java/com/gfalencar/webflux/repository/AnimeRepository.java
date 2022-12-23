package com.gfalencar.webflux.repository;

import com.gfalencar.webflux.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnimeRepository extends ReactiveCrudRepository<Anime, String> {
}
