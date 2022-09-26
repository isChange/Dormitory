package com.ly.vo;

import lombok.Data;

@Data
public class StudentVo {
    private Integer id;
    private String number;
    private String name;
    private String gender;
    private String dormitoryName;
    private String state = "入住";
    private String createDate;
}
