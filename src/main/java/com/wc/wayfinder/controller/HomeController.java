package com.wc.wayfinder.controller;

import com.wc.wayfinder.dto.ApiDataDTO;
import com.wc.wayfinder.dto.Role;
import com.wc.wayfinder.dto.ToiletResponse;
import com.wc.wayfinder.security.domain.CustomUser;
import com.wc.wayfinder.serivce.ApiDataService;
import com.wc.wayfinder.serivce.ImageUpload;
import com.wc.wayfinder.serivce.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final PlaceService placeService;
    private final ImageUpload imageUpload;
    private final ApiDataService apiDataService;


    // 메인 페이지
    @GetMapping("/main")
    public String main(@AuthenticationPrincipal CustomUser customUser, HttpSession session, Model model) {
        log.info("******** main");
        log.info("CustomUser : {}", customUser);
        boolean roleCheck = false;
        // 권한 확인
        if(customUser != null) {
            Collection<GrantedAuthority> authorities = customUser.getAuthorities();
            roleCheck = authorities.stream().anyMatch(r -> r.getAuthority().equals(Role.USER.getValue()));
            if(roleCheck) {
                log.info("Role == USER");
                session.setAttribute("user", customUser.getUser());
            } else {
                roleCheck = authorities.stream().anyMatch(r -> r.getAuthority().equals(Role.MANAGER.getValue()));
                if(roleCheck) {
                    log.info("Role == MANAGER");
                    session.setAttribute("manager", customUser.getManager());
                }
            }
        }
        // 화장실 정보 담은 리스트 보내주기
        List<ToiletResponse> toiletList = placeService.getToiletList();
        model.addAttribute("toiletList", toiletList);
        //log.info("List<ToiletResponse> : {}", toiletList);
        return "main";
    }

    // 이미지 화면에 띄울때 필요한 이미지 요청 경로
    // -> 로컬에 실제 파일을 저장해서 서버에서 파일 보내줘야 화면에 띄울 수 있다
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource getImages(@PathVariable String filename) throws MalformedURLException {
        // 서버(로컬)에 저장되어 있는 실제 경로
        return new UrlResource("file:" + imageUpload.getFilepath(filename));
    }

    // 이미지 삭제 요청
    @ResponseBody
    @DeleteMapping("/images/delete")
    public ResponseEntity<Boolean> deleteImage(@RequestBody List<Long> list) {
        log.info("******** /image/delete");
        log.info("list : {}", list);

        int result = -1;
        if(list.size() > 0) {
            result = 0;
            for(int i = 0; i < list.size(); i++) {
                // 받은 리스트에서 이미지의 id 값 한개씩 꺼내 삭제 시키기
                Long id = list.get(i);
                result += placeService.deleteImage(id);
            }
        }
        // 전부 삭제 성공했다면 ok
        return result == list.size() ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    /*
    //DB 에 JSON 타입 저장
    @GetMapping("/apiTest")
    public String apiTest() {
        return "apiTest";
    }

    @ResponseBody
    @PostMapping(value = "/saveToiletData", consumes = "application/json")
    public ResponseEntity<String> saveToilet(@RequestBody List<ApiDataDTO> list) {
        log.info("list : {}", list);
        // db 에 저장
        //apiDataService.saveApiData(list);
        // db 에 추가
        //apiDataService.updateData(list);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    */


}
