package com.study.board2.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 클래스가 db에 있는 테이블을 의미한다는 것을 나타냄
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String password;

    private String filename;

    private String filepath;

    private Integer hits;
}
