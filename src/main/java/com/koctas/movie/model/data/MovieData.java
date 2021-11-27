package com.koctas.movie.model.data;

import lombok.Data;

/**
 * @author hkaynar on 27.11.2021
 */
@Data
public class MovieData {
    private Long id;

    private String title;

    private String release_date;

    private String original_title;

    private String original_language;

    private int popularity;

    private int vote_average;

    private int vote_count;
}
