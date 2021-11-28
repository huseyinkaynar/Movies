package com.koctas.movie.model.entity.movie;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author hkaynar on 27.11.2021
 */
@Entity
@Data
public class MovieModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String release_date;

    private String original_title;

    private String original_language;

    private int popularity;

    private int vote_average;

    private int vote_count;
}
