package com.software2.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
//@Entity
//@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private int ID;
}
