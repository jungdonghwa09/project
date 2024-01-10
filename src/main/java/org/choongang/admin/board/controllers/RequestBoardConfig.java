package org.choongang.admin.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestBoardConfig {
    private String mode="add";
    private String gid= UUID.randomUUID().toString();
    @NotBlank
    private String bid;//게시판 아이디

    @NotBlank
    private String bName;//게시판 이름

    private boolean active;//사용여부
    private int rowsPerPage=20;//한페이지 게시글 수
    private int pageCountPc=10;
    private int pageCountMobile=5;//페이지구간 개수
    private boolean useReply; //답글사용여부
    private boolean useComment;
    private boolean useEditor;
    private boolean useUploadImage;
    private boolean useUploadFile;
    private String locationAfterWriting="list"; //글 쓴 후 이동 위치
    private String skin="default";
    private String category;//게시판 분류
    private String listAccessType="ALL";//글목록 권한
    private String viewAccessType="ALL";
    private String writeAccessType="ALL";
    private String replyAccessType="ALL";
    private String commentAccessType="ALL";
    private String htmlTop;
    private String htmlBottom;



}
