package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//众包工人接受的任务
@Data
@Entity
@Table(name="t_worktask")
@NoArgsConstructor
@AllArgsConstructor
public class WorkTask{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int ID;
    int initTaskID;//众包发起者发起的任务id
    String workerID;//工人ID
    String listOfLabel;//标注ID列表
    int credit;//应获得的积分
    int actualCredit;//实际获得的积分
    Date starttime;
    Date endtime;
    int state;//完成状态，指该众包工人，而非整个众包的完成状态,0为未完成，1为完成，2 为已关闭已完成,3为已关闭未完成
    int type;//获取方式（系统分配1，自主领取2）

}
