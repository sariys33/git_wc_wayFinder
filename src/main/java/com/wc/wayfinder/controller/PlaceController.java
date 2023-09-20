package com.wc.wayfinder.controller;

import com.wc.wayfinder.dto.*;
import com.wc.wayfinder.serivce.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/places")
@Slf4j
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    // 상세 정보 추가 페이지
    @GetMapping("/new")
    public String newForm(@RequestParam("oid") Long id, Model model) {
        log.info("******** /new GET");
        log.info("id : {}", id);

        PlaceForm form = new PlaceForm();
        form.setObject_id(id);
        model.addAttribute("form", form);
        ToiletResponse toilet = placeService.getInfoById(id);
        model.addAttribute("toilet", toilet);

        return "place/add";
    }

    // 정보 추가 요청
    @PostMapping("/new")
    public String addNewPro(@ModelAttribute("form") PlaceForm form) throws IOException {
        log.info("******** /new POST");
        log.info("PlaceForm : {}", form);
        int result = placeService.add(form);
        // 정보 추가 성공시
        if(result == 1) {
            // 보내줄 경로에 필요한 값 가져오기
            ToiletResponse findInfo = placeService.getInfoById(form.getObject_id());
            Long place_id = findInfo.getPlace_id();
            if(place_id > 0) {
                return "redirect:/places/"+place_id+"/detail";
            }
        }
        return "place/add.html";
    }

    // 상세페이지 보여주기
    @GetMapping("/{place_id}/detail")
    public String detail(@PathVariable("place_id") Long id, Model model) {
        log.info("******** /{id}/detail GET");
        log.info("id : {}", id);
        PlaceResponse findPlace = placeService.findById(id);
        log.info("Place : {}", findPlace);
        model.addAttribute("place", findPlace);
        return "place/detail";
    }

    // 메인페이지에 띄워줄 장소 상세 정보
    @ResponseBody
    @GetMapping("/{place_id}/show")
    public ResponseEntity<PlaceResponse> getWithOutReview(@PathVariable("place_id") Long place_id) {
        log.info("******** ajax /{id}/show GET");
        log.info("place_id : {}", place_id);
        PlaceResponse findPlace = placeService.findById(place_id);
        return findPlace != null ? new ResponseEntity<>(findPlace, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 화장실 정보 갱신 페이지
    @GetMapping("/{place_id}/update")
    public String updateInfo(@PathVariable Long place_id, Model model,
                             @ModelAttribute("form") PlaceForm form) {
        log.info("******** /place_id/update GET");
        log.info("place_id : {}", place_id);

        PlaceResponse findPlace = placeService.findById(place_id);
        log.info("findPlace : {}", findPlace);
        model.addAttribute("place", findPlace);

        return "place/infoUpdate";
    }

    // 화장실 정보 갱신 요청
    @PostMapping("/{place_id}/update")
    public String updateInfoPro(@PathVariable Long place_id,
                                @ModelAttribute PlaceForm formData) throws IOException {
        log.info("******** /place_id/update POST");
        log.info("place_id : {}", place_id);
        log.info("formData : {}", formData);

        int result = placeService.update(place_id, formData);
        return result == 1 ? "redirect:/places/"+place_id+"/detail" : "place/infoUpdate.html";
    }

    // 화장실 검색 요청
    @ResponseBody
    @GetMapping(value = "/search/{pageNum}", consumes = "application/json")
    public ResponseEntity<SearchResponse> search(@RequestParam("keyword") String keyword,
                                                 @PathVariable("pageNum") int pageNum){
        log.info("******** /search GET");
        log.info("keyword : {}", keyword);
        log.info("pageNum : {}", pageNum);

        PageInfo pageInfo = new PageInfo(pageNum, 20, keyword);
        SearchResponse searched = placeService.searchByKeyword(pageInfo);

        return searched != null ? new ResponseEntity<>(searched, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 화장실 정보 가져오기
    @ResponseBody
    @GetMapping("/{object_id}/info")
    public ResponseEntity<ToiletResponse> getInfo(@PathVariable("object_id") Long id) {
        log.info("object_id : {}", id);
        ToiletResponse toilet = placeService.getInfoById(id);
        return toilet != null ? new ResponseEntity<>(toilet, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
