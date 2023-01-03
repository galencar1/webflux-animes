package com.gfalencar.webflux.service;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.repository.AnimeRepository;
import com.gfalencar.webflux.utils.AnimeCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;

    @Mock
    AnimeRepository animeRepositoryMock;

    private final Anime anime = AnimeCreator.createValidAnime();

    @BeforeEach
    public void SetUp(){
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(Flux.just(anime));
    }
    @Test
    @DisplayName("findAll returns a flux of anime")
    public void findAll_ReturnFluxOfAnime_WhenSuccesfull(){
        StepVerifier.create(service.findAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

}
