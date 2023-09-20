package com.wc.wayfinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
@RequestMapping("${server.error.path:${error.path:/error}}")
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public String getErrorPath() {
        return "/error";
    }

    @GetMapping
    public ModelAndView handleError(WebRequest request) {
        // 에러 속성을 가져오고 필요한 정보를 추출합니다.
        Map<String, Object> errorAttributeMap = errorAttributes.getErrorAttributes
                (request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.EXCEPTION));

        // 커스텀 에러 페이지로 포워딩합니다.
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorCode", errorAttributeMap.get("status"));
        modelAndView.addObject("errorMessage", errorAttributeMap.get("message"));
        modelAndView.addObject("exceptionName", errorAttributeMap.get("exception"));
        return modelAndView;
    }

}
