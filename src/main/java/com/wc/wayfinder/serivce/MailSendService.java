package com.wc.wayfinder.serivce;

import com.wc.wayfinder.domain.Managers;
import com.wc.wayfinder.domain.Users;
import com.wc.wayfinder.repository.ManagerRepository;
import com.wc.wayfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;


    private String ePw; // 인증번호

    // 메일 내용 작성
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);// 보내는 대상
        message.setSubject("WayFinder 회원가입 이메일 인증");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1>  WayFinder 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("dlcksgh789@naver.com", "BathRoomAdmin"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송
    public String createKey() {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) { // 6자리
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    public String sendSimpleMessage(String to) throws Exception {

        ePw = createKey(); // 랜덤 인증번호 생성

        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to); // 메일 발송
        try {
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            log.error("Error sending email to: " + to, es);  // 로깅 활용
            throw new RuntimeException("Error sending email.", es); // RuntimeException으로 원래 예외를 포장해서 던짐
        }

        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }

    // 비밀번호 초기화 및 임시 비밀번호 발송 로직
    public String sendTemporaryPassword(String email) throws Exception {
        // 비밀번호 초기화 로직 및 임시 비밀번호 생성
        String temporaryPassword = generateTemporaryPassword();
        Users findUser = userRepository.findByEmail(email);
        Long id = findUser.getId();
        int result = userRepository.modifyPassword(id, passwordEncoder.encode(temporaryPassword));
        if(result == 1) {
            // 이메일 발송 로직
            MimeMessage message = createTemporaryPasswordMessage(email, temporaryPassword);
            try {
                emailSender.send(message);
            } catch (MailException es) {
                es.printStackTrace();
                log.error("Error sending email to: " + email, es);
                throw new RuntimeException("Error sending email.", es);
            }
            return temporaryPassword;
        }
        return null;
    }

    // 비밀번호 초기화 및 임시 비밀번호 발송 로직 (관리자용)
    public String sendTemporaryPasswordForMgr(String email) throws Exception {
        // 비밀번호 초기화 로직 및 임시 비밀번호 생성
        String temporaryPassword = generateTemporaryPassword();
        Managers findManager = managerRepository.findByEmail(email);
        Long id = findManager.getId();
        int result = managerRepository.modifyPassword(id, passwordEncoder.encode(temporaryPassword));
        if(result == 1) {
            // 이메일 발송 로직
            MimeMessage message = createTemporaryPasswordMessage(email, temporaryPassword);
            try {
                emailSender.send(message);
            } catch (MailException es) {
                es.printStackTrace();
                log.error("Error sending email to: " + email, es);
                throw new RuntimeException("Error sending email.", es);
            }
            return temporaryPassword;
        }
        return null;
    }

    // 임시 비밀번호 생성 로직
    private String generateTemporaryPassword() {
        StringBuffer pw = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 10; i++) { // 10자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨
            switch (index) {
                case 0:
                    pw.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    pw.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    pw.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return pw.toString();
    }

    // 이메일 발송 내용 생성 로직
    private MimeMessage createTemporaryPasswordMessage(String to, String temporaryPassword)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); // 이메일 수신자 설정
        message.setSubject("WayFinder 임시 비밀번호 발송"); // 이메일 제목 설정

        // 이메일 내용 생성
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1>안녕하세요,</h1>";
        msgg += "<h2>WayFinder 임시 비밀번호가 발송되었습니다.</h2>";
        msgg += "<br>";
        msgg += "<p>아래 임시 비밀번호로 로그인 후 비밀번호를 변경해주세요.</p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>임시 비밀번호</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "<strong>" + temporaryPassword + "</strong><div><br/> "; // 임시 비밀번호 삽입
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html"); // 내용, charset 타입, subtype

        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("dlcksgh789@naver.com", "WayFinder 관리자")); // 보내는 사람 설정

        return message;
    }

}