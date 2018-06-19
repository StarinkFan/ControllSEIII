package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//众包发起者发起的任务
@Data
@Entity
@Table(name="t_inittask")
@NoArgsConstructor
@AllArgsConstructor
public class InitTask{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String listOfP;//图片ID
    private String InitorID;//发起者ID
    private String InitorName;//发起者名字
    private String request;//需求
    private int tag;//标注类型（选填，若填写，则该任务全部图片都需采用该标注方式）1整体2框3描边
    private int credit;//积分
    private int num;//人数
    private String remarks;//用于存放未过审原因
    private String listOfWoker;//参与者ID列表
    private long startTime;//众包开始时间（选填,不填默认为审核通过时间）
    private long endTime;//众包真正结束时间
    private long deadline;//众包截止时间（必填）
    private int state;//项目完成状态（具体对应规则未定）(初定0未完成，1完成,2正在进行,3审核中，4未过审）
    private String listOfTags;//该任务的标签
    private String kind;
    private int hasQuestion;//0表示没有问题，1表示有问题
    private String question;//问题
    private String listOfOptions;//选项
    private String answer;//答案
    private String wrongAnswerWorker;//回答错误的工人id列表
}
