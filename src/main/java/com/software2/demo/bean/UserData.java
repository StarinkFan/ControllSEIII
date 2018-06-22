package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author:Wang Mo
 * @Description：
 */
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int ID;
    String percents;
    String data0;
    String data1;
    String picNumbers;
    String credits;
}
