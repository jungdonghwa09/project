package org.choongang.admin.reservation.controllers;

import jakarta.validation.Valid;
import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/reservation")
public class ReservationController implements ExceptionProcessor {
    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "reservation";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("reservation");
    }

    @GetMapping
    public String list(Model model) {
        //예약현황, 예약 관리
        commonProcess("list",model);
        return "admin.reservation/list";
    }

    @GetMapping("/add_branch")
    public String addBranch(@ModelAttribute RequestBranch form, Model model) {
        //지점등록
        commonProcess("add_branch",model);
        return "admin/reservation/add_branch";
    }

    @PostMapping("/save_branch")
    public String saveBranch(@Valid RequestBranch form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(mode,model);

        if(errors.hasErrors()){
            return "admin/reservation/"+mode;
        }
        return "redirect:/admin/reservation/branch";
    }
    @GetMapping("/branch")
    public String branchList(Model model){
        commonProcess("branch", model);
        return "Admin/reservation/branch_list";
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "예약현황";
        mode = Objects.requireNonNullElse(mode, "list");

        if (mode.equals("add_branch")) {
            pageTitle = "지점 등록";

        } else if (mode.equals("edit_branch")) {
            pageTitle = "지점 수정";
        } else if (mode.equals("branch")) {
            pageTitle = "지점 목록";
        }
        model.addAttribute("pageTitle", pageTitle);
    model.addAttribute("subMenuCode",mode);
    }
}