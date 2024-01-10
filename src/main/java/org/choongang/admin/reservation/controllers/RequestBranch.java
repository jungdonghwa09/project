package org.choongang.admin.reservation.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class RequestBranch {

    private String mode = "add_branch";
    private Long cCode;//지점코드

    @NotBlank
    private String cName;
    //지점명

    @NotBlank
    private String zonecode;

    @NotBlank
    private String address;
    private String addressSub;

    @NotBlank
    private String telNum;

    @NotBlank
    private String openHour;//운영시간
    private List<String> bookYoil;//예약요일 설정
    private String bookAvlShour;
    private String bookAvlSmin;
    private String bookAvlEhour;
    private String bookAvlEmin;
    private String bookNotAvl;
    private boolean bookHdy;//공휴일 예약 가능 여부
    private String bookBlock;//예약블록
    private int bookCapacity;//0이면 불가,-1이면 무제한

}

