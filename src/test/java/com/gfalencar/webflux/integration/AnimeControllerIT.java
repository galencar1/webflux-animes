package com.gfalencar.webflux.integration;

import com.gfalencar.webflux.domain.Anime;
import com.gfalencar.webflux.exception.CustomAttributes;
import com.gfalencar.webflux.repository.AnimeRepository;
import com.gfalencar.webflux.service.AnimeService;
import com.gfalencar.webflux.utils.AnimeCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import({AnimeService.class, CustomAttributes.class})
public class AnimeControllerIT {
    @MockBean
    private AnimeRepository animeRepositoryMock; // Moca o banco de dados.

    @Autowired
    private WebTestClient testClient;

    private final Anime anime = AnimeCreator.createValidAnime();

    @BeforeEach
    public void setUp(){
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(Flux.just(anime));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(anime));
    }


    @Test
    @DisplayName("listAll returns a flux of anime")
    public void listAll_ReturnFluxOfAnime_WhenSuccessful() {
        testClient
                .get()
                .uri("/getAllAnimes")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(anime.getId())
                .jsonPath("$.[0].name").isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("findById returns a Mono with Anime when its exists")
    public void findById_ReturnMonoAnime_WhenSuccesful() {
        testClient
                .get()
                .uri("/getAnime/{id}","63b47c98ff353c5a39c80b6a")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Anime.class)
                .isEqualTo(anime);
    }

    @Test
    @DisplayName("findById returns Mono error when anime does not exists")
    public void findById_ReturnMono_WhenEmptyMonoIsReturned(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.empty());

        testClient
                .get()
                .uri("/getAnime/{id}", "63b47c98ff353c5a39c80b6a")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }
}
