
package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name="t_picture")
@NoArgsConstructor
@AllArgsConstructor
public class picture{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String url;//图片地址
    private String pID;//发布者ID
    private String lID;//标注ID列表
    private String listOfAnswers;//正确答案列表
    private int tag;//标注类型(0:未规定标注方式1:wholelabel,2:方框标注,3:描边标注)
}