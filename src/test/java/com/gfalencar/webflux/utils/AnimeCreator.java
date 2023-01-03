package com.gfalencar.webflux.utils;

import com.gfalencar.webflux.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Toradora")
                .build();
    }
    public static Anime createValidAnime() {
        return Anime.builder()
                .id("63b47c98ff353c5a39c80b6a")
                .name("Toradora")
                .build();
    }
    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .id("63b47c98ff353c5a39c80b6a")
                .name("Toradora 2")
                .build();
    }
}
