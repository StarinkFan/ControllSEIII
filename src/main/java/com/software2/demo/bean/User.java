package com.software2.demo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name="t_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    private String ID;
    private String name;//昵称
    private String password; //密码
    private String sex;//性别
    private String wechat;//微信号
    private String address;//地址
    private String introduction;//介绍
    private int credit;//积分
    private int earncredit;//赚取的积分
    private int ranking;//排名
    private String listOfCRecord;//该用户花费记录
    private String listOfITask; //该用户发布的任务
    private String listOfWTask; //该用户接取的任务
    private String listOfTags;  //该用户给自己增加的标签
    private String listOfTitles; //该用户所获得的专家称号
    private String headShotUrl;//头像url
    private int onlinestatus;//登录状态
    private String sessionid;
}
