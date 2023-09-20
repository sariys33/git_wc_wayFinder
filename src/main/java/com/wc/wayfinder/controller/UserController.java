package com.wc.wayfinder.controller;

import com.wc.wayfinder.dto.UserForm;
import com.wc.wayfinder.dto.UserResponse;
import com.wc.wayfinder.serivce.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 비밀번호 일치여부 확인
    @ResponseBody
    @PostMapping(value = "/{id}/checkPw", consumes = "application/json")
    public ResponseEntity<Void> checkPw(@PathVariable Long id, @RequestBody String password) {
        log.info("******** /{id}/checkPw");
        log.info("id : {}", id);
        log.info("password : {}", password);

        boolean result = false;
        result = userService.checkPassword(id, password);

        return result ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 회원 탈퇴 요청
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        log.info("******** /{id}/delete POST");
        log.info("id : {}", id);

        userService.remove(userService.findById(id));

        return "redirect:/logout";
    }

    // 비밀번호 수정 요청
    @PostMapping("/{id}/modifyPw")
    public String modifyPwPro(@PathVariable Long id, UserForm form, HttpSession session) {
        log.info("******** /{id}/modifyPw POST");
        log.info("id : {}", id);
        log.info("UserForm : {}", form);

        int result = 0;
        result = userService.modifyPassword(form);
        if(result == 1) {
            return "redirect:/logout";
        } else {
            return "/user/modifyPw.html";
        }
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("form") UserForm form) {
        return "user/login";
    }


    @GetMapping("/signup")
    public String signup(@ModelAttribute("form") UserForm form) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPro(UserForm form) {
        log.info("******** /signup POST");
        userService.save(form);
        return "redirect:/user/login";
    }

    @GetMapping("/profile")
    public String profile(@ModelAttribute("form") UserForm form) {
        return "user/profile";
    }

    @GetMapping("/modifyPw")
    public String modifyPw(@ModelAttribute("form") UserForm form) {
        return "user/modifyPw";
    }

    @ResponseBody
    @PostMapping(value = "/checkEmail", consumes = "application/json")
    public ResponseEntity<Integer> checkEmail(@RequestBody String email) {
        log.info("email : {}", email);

        UserResponse user = userService.findByEmail(email);

        return user == null ? new ResponseEntity<>(0, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/forgotPw")
    public String showForgotPasswordPage() {
        return "user/forgotPw"; // 해당 Thymeleaf 템플릿 페이지로 이동
    }

}
