package com.nba.season.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20)
    private String name;
    @Column(length = 2)
    private String initials;
    @Column(length = 4)
    private String state;
    @Column(length = 20)
    private String stadium;

}
