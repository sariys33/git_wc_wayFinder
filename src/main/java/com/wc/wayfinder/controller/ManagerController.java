package com.wc.wayfinder.controller;

import com.wc.wayfinder.dto.ManagerForm;
import com.wc.wayfinder.dto.ManagerResponse;
import com.wc.wayfinder.serivce.ManagerService;
import com.wc.wayfinder.serivce.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
@Slf4j
@RequiredArgsConstructor
public class ManagerController {
    
    private final ManagerService managerService;
    private final PlaceService placeService;

    // 비밀번호 일치여부 확인
    @ResponseBody
    @PostMapping(value = "/{id}/checkPw", consumes = "application/json")
    public ResponseEntity<Void> checkPw(@PathVariable Long id, @RequestBody String password) {
        log.info("******** /{id}/checkPw");
        log.info("id : {}", id);
        log.info("password : {}", password);

        boolean result = false;
        result = managerService.checkPassword(id, password);

        return result ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 회원 탈퇴 요청
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        log.info("******** /{id}/delete POST");
        log.info("id : {}", id);

        managerService.remove(managerService.findById(id));

        return "redirect:/logout";
    }

    // 비밀번호 수정 요청
    @PostMapping("/{id}/modifyPw")
    public String modifyPwPro(@PathVariable Long id, ManagerForm form) {
        log.info("******** /{id}/modifyPw POST");
        log.info("id : {}", id);
        log.info("ManagerForm : {}", form);

        int result = 0;
        result = managerService.modifyPassword(form);
        if(result == 1) {
            return "redirect:/logout";
        } else {
            return "/manager/modifyPw.html";
        }
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("form") ManagerForm form) {
        return "manager/login";
    }


    @GetMapping("/signup")
    public String signup(@ModelAttribute("form") ManagerForm form) {
        return "manager/signup";
    }

    @PostMapping("/signup")
    public String signupPro(ManagerForm form) {
        log.info("******** /signup POST");
        managerService.save(form);
        return "redirect:/manager/login";
    }

    @GetMapping("/profile")
    public String profile(@ModelAttribute("form") ManagerForm form) {
        return "manager/profile";
    }

    @GetMapping("/modifyPw")
    public String modifyPw(@ModelAttribute("form") ManagerForm form) {
        return "manager/modifyPw";
    }

    @GetMapping("/detail")
    public String detail(@ModelAttribute("form") ManagerForm form) {
        return "manager/detail";
    }

    // 이메일 중복확인 요청
    @ResponseBody
    @PostMapping(value = "/checkEmail", consumes = "application/json")
    public ResponseEntity<Integer> checkEmail(@RequestBody String email) {
        log.info("email : {}", email);

        ManagerResponse manager = managerService.findByEmail(email);

        return manager == null ? new ResponseEntity<>(0, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/forgotPw")
    public String showForgotPasswordPage() {
        return "manager/forgotPw"; // 해당 Thymeleaf 템플릿 페이지로 이동
    }

}
