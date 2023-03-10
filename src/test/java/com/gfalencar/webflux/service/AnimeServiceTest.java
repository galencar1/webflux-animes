package com.gfalencar.webflux.service;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.repository.AnimeRepository;
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
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

        BDDMockito.when(animeRepositoryMock.findById(anime.getId()))
                .thenReturn(Mono.just(anime));

        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createAnimeToBeSaved()))
                .thenReturn(Mono.just(anime));

        BDDMockito.when(animeRepositoryMock.delete(ArgumentMatchers.any(Anime.class)))
                .thenReturn(Mono.empty());

        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createValidAnime()))
                .thenReturn(Mono.empty());
    }
    @Test
    @DisplayName("findAll returns a flux of anime")
    public void findAll_ReturnFluxOfAnime_WhenSuccesfull(){
        StepVerifier.create(service.findAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono with anime when it exists")
    public void findById_ReturnMonoAnime_WhenSuccesfull(){
        StepVerifier.create(service.findById("63b47c98ff353c5a39c80b6a"))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono error when anime does not exists")
    public void findById_ReturnMonoError_WhenEmptyMonoIsReturned(){
        BDDMockito.when(animeRepositoryMock.findById(anime.getId()))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.findById("63b47c98ff353c5a39c80b6a"))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("save creates an anime when succesfull")
    public void saved_CreatesAnime_WhenSuccesfull() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        StepVerifier.create(service.save(animeToBeSaved))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    @DisplayName("delete removes the anime when succesfull")
    public void delete_RemovesAnime_WhenSuccesfull() {
        StepVerifier.create(service.delete("63b47c98ff353c5a39c80b6a"))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("delete return Mono error when anime does not exists")
    public void delete_ReturnMonoError_WhenEmptyMonoIsReturned(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyString()))
                        .thenReturn(Mono.empty());

        StepVerifier.create(service.delete("63b47c98ff353c5a39c80b6a"))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("update save updated anime and returns empty mono when succesful")
    public void update_SaveUpdatedAnime_WhenSuccesful() {
        StepVerifier.create(service.update(AnimeCreator.createValidAnime()))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("update returns Mono error when anime does exists")
    public void update_ReturnError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyString()))
                        .thenReturn(Mono.empty());

        StepVerifier.create(service.update(AnimeCreator.createValidAnime()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }


}
