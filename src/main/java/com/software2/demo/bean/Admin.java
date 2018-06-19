package com.software2.demo.bean;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Table(name="t_admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin{
    @Id
    String ID;
    String name;//昵称
    String password; //密码
    String infomation;//管理信息
}
