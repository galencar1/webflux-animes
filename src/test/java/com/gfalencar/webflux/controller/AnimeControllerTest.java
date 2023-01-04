package com.gfalencar.webflux.controller;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.service.AnimeService;
import com.gfalencar.webflux.utils.AnimeCreator;
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

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    private final Anime anime = AnimeCreator.createValidAnime();

    @BeforeEach
    public void SetUp() {
        BDDMockito.when(animeServiceMock.findAll())
                .thenReturn(Flux.just(anime));
    }

    @Test
    @DisplayName("")
    public void findAll_ReturnFluxOfAnime_WhenSuccesful(){
        StepVerifier.create(animeController.listAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

}