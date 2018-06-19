package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerTaskVO extends Item{
    private String InitorID;//发布者ID
    private String InitorName;//发布者名字
    private String TaskRequest;//任务要求
    private int tag;//标注类型 0：无 1：整体 2：方框 3：描边
    private int credit;//积分
    private int actualCredit;//实际获得的积分
    private int num;//需要人数
    private int partnum;//参与人数
    long startTime;//众包开始时间（选填,不填默认为审核通过时间）
    long endtime;//众包结束时间（真正结束时间）
    long deadline;//众包截止时间（必填）
    private String URL;//第一张图的URL
    private int state;//任务状态 0：未完成 1：已完成 2：已关闭
    private int type;//获取方式 1：系统分配 2：自主领取
    private String kind;//种类
    private String mark;//标签
    private String requestorHeadShotUrl;//发起者头像url

    public WorkerTaskVO(int ID, String initorID, String initorName, String taskRequest, int tag, int credit, int actualCredit, int num, int partnum, long startTime, long endtime, long deadline, String URL, int state, int type,String kind,String mark,String requestorHeadShotUrl) {
        super(ID);
        InitorID = initorID;
        InitorName = initorName;
        TaskRequest = taskRequest;
        this.tag = tag;
        this.credit = credit;
        this.actualCredit = actualCredit;
        this.num = num;
        this.partnum = partnum;
        this.startTime = startTime;
        this.endtime = endtime;
        this.deadline = deadline;
        this.URL = URL;
        this.state = state;
        this.type = type;
        this.kind=kind;
        this.mark=mark;
        this.requestorHeadShotUrl=requestorHeadShotUrl;
    }


}
