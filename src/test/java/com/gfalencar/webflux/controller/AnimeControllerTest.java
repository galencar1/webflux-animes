package com.gfalencar.webflux.controller;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.service.AnimeService;
import com.gfalencar.webflux.utils.AnimeCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(anime));

        BDDMockito.when(animeServiceMock.save(AnimeCreator.createAnimeToBeSaved()))
                .thenReturn(Mono.just(anime));

        BDDMockito.when(animeServiceMock.delete(ArgumentMatchers.anyString()))
                .thenReturn(Mono.empty());

        BDDMockito.when(animeServiceMock.update(AnimeCreator.createValidAnime()))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findAll returns a flux of anime")
    public void findAll_ReturnFluxOfAnime_WhenSuccesful(){
        StepVerifier.create(animeController.listAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns a Mono With anime when it exists")
    public void findById_ReturnMonoAnime_WhenSuccesful(){
        StepVerifier.create(animeController.listById("63b47c98ff353c5a39c80b6a"))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    @DisplayName("Saves creates an Anime when succesful")
    public void save_CreatesAnime_WhenSuccesful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        StepVerifier.create(animeController.saveAnime(animeToBeSaved))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    @DisplayName("delete removes the anime when succesfull")
    public void delete_RemovesAnime_WhenSuccesfull() {
        StepVerifier.create(animeController.deleteAnime("63b47c98ff353c5a39c80b6a"))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("update save updated anime and returns empty mono when succesful")
    public void update_SaveUpdatedAnime_WhenSuccesful() {
        StepVerifier.create(animeController.updateAnime(AnimeCreator.createValidAnime()))
                .expectSubscription()
                .verifyComplete();
    }



}