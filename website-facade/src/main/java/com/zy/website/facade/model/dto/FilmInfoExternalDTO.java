package com.zy.website.facade.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilmInfoExternalDTO extends BaseDTO {

    private Long createdAt;
    private Long updatedAt;
    private String id;
    private String originalName;
    private String imdbVotes;
    private String imdbRating;
    private String rottenRating;
    private String rottenVotes;
    private String year;
    private String imdbId;
    private String alias;
    private String doubanId;
    private String type;
    private String doubanRating;
    private String doubanVotes;
    private String duration;
    private String episodes;
    private String totalSeasons;
    private String dateReleased;
    private String artRatings;
    private String actorRatings;
    private String soundRatings;
    private String storyRatings;
    private String enjoymentRatings;
    private String totalVotes;
    private List<FilmInfoExternalDataDTO> data;

}
