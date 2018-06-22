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
    public void init(String name){
        this.name = name;
        achieve = 0;
        num_of_right = 0;
        num_of_complete = 0;
        time = 0;
        marks = 0;
    }

    public double[] toArray(){
        double[] array = new double[5];
        array[0] = achieve;
        if(num_of_complete!=0)
            array[1] = num_of_right/(double)num_of_complete;
        else
            array[1] = 0;
        if(time != 0)
            array[2] = num_of_complete/time;
        else
            array[2] = 0;
        array[3] = num_of_complete;
        array[4] = marks;
        return array;
    }
}
