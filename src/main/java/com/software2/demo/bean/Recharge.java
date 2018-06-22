package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="recharge")
@NoArgsConstructor
@AllArgsConstructor
public class Recharge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String uid;
    private String cardID;
    private String cardPW;
    private int money;
    private Date date;
}
