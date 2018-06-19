package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="t_complaint")
@NoArgsConstructor
@AllArgsConstructor
public class Complaint extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private int pictureID;//投诉的图片id
    private String workerID;//投诉的工人id
    private String requestorID;//发起者id
    private String reason;//投诉理由
    private int initTaskID;
    private int workTaskID;
}
