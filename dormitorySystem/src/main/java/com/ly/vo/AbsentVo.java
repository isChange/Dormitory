package com.ly.vo;

import lombok.Data;

@Data
public class AbsentVo {
    private Integer id;
    private String buildingName;
    private String dormitoryName;
    private String studentName;
    private String dormitoryAdminName;
    private String createDate;
    private String reason;
}
