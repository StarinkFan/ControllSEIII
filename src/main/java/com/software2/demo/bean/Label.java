package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name="t_label")
@NoArgsConstructor
@AllArgsConstructor
public class Label{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    private String info;//标注信息
    private int pID;//所标的图片ID
    private String d;//标注对应的描述
    private String giverID;//标注者ID
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    private String lis;//图片选取点的集合
    private int tag;//标注类型（1:整体标注 2:方框标注,3:描边标注）
    private String color;//标注框的颜色
    private int state;//标注状况，0表示未评判，1表示正确，2表示错误,3待仲裁


}
