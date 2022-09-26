package com.ly.vo;

import lombok.Data;

@Data
public class MoveoutVo {
    private Integer id;
    private String studentName;
    private String dormitoryName;
    private String reason;
    private String createDate;
}
