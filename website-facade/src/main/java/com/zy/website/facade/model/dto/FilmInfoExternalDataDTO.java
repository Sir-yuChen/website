package com.zy.website.facade.model.dto;

import lombok.Data;

@Data
public class FilmInfoExternalDataDTO extends BaseDTO {

    private Long createdAt;
    private Long updatedAt;
    private String id;
    private String poster;
    private String name;
    private String genre;
    private String description;
    private String language;
    private String country;
    private String lang;
    private String shareImage;
    private String movie;
}
