package com.dtl.rms_server.dtos.hiringnews;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HiringNewsCreateDTO {
    private String title;
    private LocalDate dueDate;
    private int quatity;
    private int salaryMin;
    private int salaryMax;
    private String description;
    private String benefits;
    private String requirements;
    private int status;
    private int isActive;
    private int accountId;

}
