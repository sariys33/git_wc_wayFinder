package com.wc.wayfinder.controller;

import com.wc.wayfinder.serivce.MailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailSendService mailService;

    //이메일 인증
    @ResponseBody
    @PostMapping(value = "/authCode", consumes = "application/json")
    public String mailConfirm(@RequestBody String email) throws Exception {
        log.info("email : {}", email);
        try {
            String code = mailService.sendSimpleMessage (email);
            System.out.println("인증코드 : " + code);
            return code;
        } catch (Exception e) {
            e.printStackTrace();  // 콘솔에 오류를 출력
            return "오류! 이메일을 정확히 입력해주세요";
        }
    }

    // 비밀번호 찾기
    @ResponseBody
    @PostMapping(value = "/user/tempPw", consumes = "application/json")
    public ResponseEntity<String> userForgotPw(@RequestBody String email) throws Exception {
        log.info("email : {}", email);
        String temporaryPassword = mailService.sendTemporaryPassword(email);
        return temporaryPassword != null ? new ResponseEntity<>(temporaryPassword, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 비밀번호 찾기
    @ResponseBody
    @PostMapping(value = "/manager/tempPw", consumes = "application/json")
    public ResponseEntity<String> mgrForgotPw(@RequestBody String email) throws Exception {
        log.info("email : {}", email);
        String temporaryPassword = mailService.sendTemporaryPasswordForMgr(email);
        return temporaryPassword != null ? new ResponseEntity<>(temporaryPassword, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}