package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.ImageFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageUpload {

    @Value("${file.dir}") // application.properties 에서 지정한 경로 가져오기
    private String imgDir;

    // 이미지 저장
    public List<ImageFile> uploadImages(List<MultipartFile> imageFiles) throws IOException {
        List<ImageFile> imgList = new ArrayList<>(); // 이미지 담을 객체 만들어 두기
        for (MultipartFile img : imageFiles) { // 받은 이미지 리스트 정보 하나씩 꺼내기
            if(!img.isEmpty()) { // 비어있지 않다면
                imgList.add(uploadImage(img)); // 한개씩 저장 시키고 담기
            }
        }
        return imgList;
    }

    // 이미지 한개 저장 시키기
    private ImageFile uploadImage(MultipartFile imageFile) throws IOException {
        if(imageFile.isEmpty()) { // 저장할 이미지가 없다면 종료
            return null;
        }
        String orgFilename = imageFile.getOriginalFilename(); // 실제 파일 이름
        String filename = makeFilename(orgFilename); // UUID 로 만든(덮어쓰기 되지 않게 할) 파일 이름

        // 받은 이미지 파일을 파일경로 + UUID 넣어 실제 파일로 저장시키기
        imageFile.transferTo(new File(getFilepath(filename)));
        return new ImageFile(orgFilename, filename);
    }

    // 파일 이름 포함한 파일 전체 경로
    public String getFilepath(String filename) {
        return imgDir + filename;
    }

    // 이미지 이름 UUID 로 변환 시키기
    private String makeFilename(String orgFilename) {
        String ext = extractExt(orgFilename); // 확장자명 분리
        String uuid = UUID.randomUUID().toString();// UUID 생성
        return uuid + ext;
    }

    // 확장자명 분리 시키기
    private String extractExt(String orgFilename) {
        int idx = orgFilename.lastIndexOf(".");// 마지막 . 기준의 위치
        String ext = orgFilename.substring(idx); // 해당 위치 자르기
        return ext;
    }

}
