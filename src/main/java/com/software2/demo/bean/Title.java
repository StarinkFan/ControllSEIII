package com.software2.demo.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Title {
    private String name;
    private int achieve;
    private int num_of_right;
    private double time;
    private int num_of_complete;
    private double marks;
    public double[] toArray(){
        double[] array = new double[5];
        array[0] = achieve;
        array[1] = num_of_right/(double)num_of_complete;
        array[2] = num_of_complete/time;
        array[3] = num_of_complete;
        array[4] = marks;
        return array;
    }
}
