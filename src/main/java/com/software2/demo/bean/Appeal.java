package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="t_appeal")
@NoArgsConstructor
@AllArgsConstructor
public class Appeal extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private int pictureID;//申诉的图片id
    private String workerID;//申诉的工人id
    private String requestorID;//任务发起者id
    private String reason;//申诉理由
    private int initTaskID;
    private int workTaskID;
    private boolean check;
}
