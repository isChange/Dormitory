package com.ly.form;

import lombok.Data;

@Data
public class StudentForm {
    private Integer id;
    private String number;
    private String name;
    private String gender;
    private Integer dormitoryId;
    private Integer oldDormitoryId;
    private String state = "入住";
    private String createDate;

}
