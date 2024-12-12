package com.ssafy.teongbin.hello;

import com.ssafy.teongbin.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ssafy.teongbin.common.exception.ErrorType;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HelloService {

    private final HelloRepository helloRepository;

    public Hello getHello(Long id){
        Optional<Hello> hello = helloRepository.findById(id);
        if ( hello.isEmpty() ){
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }

        return hello.get();
    }

    public void createHello(HelloRequestDto dto){
        System.out.println("Create Hello 2");
        Hello hello = Hello.builder()
                .subject(dto.getSubject())
                .content(dto.getContent())
                .build();

        if ( helloRepository.findBySubject(dto.getSubject()).isPresent() ) {
            System.out.println("중복 체크");
            throw new CustomException(ErrorType.DUPLICATED_USERID);
        }

        helloRepository.saveAndFlush(hello);
    }

    public void loginHello(HelloRequestDto dto){

    }

}
