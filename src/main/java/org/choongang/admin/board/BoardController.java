package org.choongang.admin.board;

import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
public class BoardController implements ExceptionProcessor {
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "board";//주 메뉴코드
    }
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){//서브 메뉴들
        return Menu.getMenus("board");
    }
    @GetMapping
    public String list(Model model){
        commonProcess("list",model);
        //게시판 목록
        return "admin/board/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        commonProcess("add", model);
        //게시판 등록
        return "admin/board/add";
    }
    @PostMapping("/save")
    public String save(){
        //게시판 수정, 등록
        return "redirect:/admin/board";
    }
    @GetMapping("/posts")
    public String posts(Model model){
        //게시글 관리
        commonProcess("posts",model);
        return "admin/board/posts";
    }
    private void commonProcess(String mode, Model model){
        String pageTitle = "게시판 목록";
        mode = StringUtils.hasText(mode) ? mode : "list";



        if (mode.equals("add")) {
            pageTitle="게시판 등록";

        } else if (mode.equals("edit")) {
            pageTitle="게시판 수정";
        }else if(mode.equals("posts")){
            pageTitle="게시글 관리";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add") || mode.equals("edit")) {
            addCommonScript.add("ckeditor5/ckeditor");
            addScript.add("board/form");
        }
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode",mode);
        model.addAttribute("addCommonScript",addCommonScript);
        model.addAttribute("addScript",addScript);
    }
}
