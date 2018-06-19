package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="t_query")
@NoArgsConstructor
@AllArgsConstructor
public class Query extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private int pictureID;//质疑的图片id
    private String requestorID;//发起者id
    private String reason;//质疑理由
    private int initTaskID;
}
