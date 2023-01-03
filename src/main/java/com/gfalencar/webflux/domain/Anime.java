package com.gfalencar.webflux.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class Anime {
    @Id
    private String id;
    @NotNull
    @NotEmpty(message = "The name os this anime can't not this empty")
    private String name;

}
