package com.ssafy.teongbin.trash.service;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.common.jwt.PrincipalDetails;
import com.ssafy.teongbin.trash.dto.request.NewTrashcanRequest;
import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.trash.repository.TrashcanRepository;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrashcanService {

    private final TrashcanRepository trashcanRepository;
    private final UserRepository userRepository;

    @Transactional
    public void join(NewTrashcanRequest newTrashcanRequest, PrincipalDetails userIn) {
        User user;

        Optional<User> ou = userRepository.findByEmail(userIn.getUsername());
        if ( ou.isPresent() ) {
            user = ou.get();
            System.out.println("user = " + user);

        } else {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }

        Trashcan trashcan = new Trashcan();
        trashcan.setUser(user);
        String sn = newTrashcanRequest.getSerialNumber();
        if ( sn.isEmpty() ) {
            throw new CustomException(ErrorType.NOT_FOUND_SERIAL);
        }

        // 시리얼 넘버 중복되는지 확인
        Trashcan ifTrashcan = trashcanRepository.findBySerialNumber(sn);
        if (ifTrashcan != null) {
            throw new CustomException(ErrorType.REGISTERED_TRASHCAN);
        }

        trashcan.setSerialNumber(sn);
        String nn = newTrashcanRequest.getNickname();
        if ( nn.isEmpty() ) {
            throw new CustomException(ErrorType.NOT_FOUND_NICKNAME);
        }
        trashcan.setNickname(nn);
        double x = newTrashcanRequest.getLatitude();
        if (x>90||x<-90) {
            throw new CustomException(ErrorType.INVALID_LOCATION);
        }
        double y = newTrashcanRequest.getLongitude();
        if (y>180||y<-180) {
            throw new CustomException(ErrorType.INVALID_LOCATION);
        }
        trashcan.setLatitude(newTrashcanRequest.getLatitude());
        trashcan.setLongitude(newTrashcanRequest.getLongitude());
        trashcanRepository.save(trashcan);
    }

    @Transactional
    public void deleteTrashcan(Long trashcanId, PrincipalDetails userIn) {
        Optional<User> ou = userRepository.findByEmail(userIn.getUsername());
        Optional<Trashcan> ot = trashcanRepository.findById(trashcanId);

        if ( ou.isPresent() ) {
            if ( ot.isPresent() ){
                // 토큰 유저와 Trashcan의 user가 다르다면?
                if (ou.get()!= ot.get().getUser()) {
                    throw new CustomException(ErrorType.USER_TRASHCAN_MISMATCH);
                }
                trashcanRepository.deleteById(trashcanId);
            } else {
                throw new CustomException(ErrorType.NOT_FOUND_TRASHCAN);
            }
        }
        else {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
    }

    @Transactional
    public void update(Long id, String nickname, Double latitude, Double longitude, PrincipalDetails userIn) {
        Optional<User> ou = userRepository.findByEmail(userIn.getUsername());
        if ( ou.isPresent() ) {
            Trashcan trashcan = trashcanRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("쓰레기통이 존재하지 않습니다."));
            if (nickname==null || nickname.isEmpty()) {
                throw new CustomException(ErrorType.NOT_FOUND_NICKNAME);
            }
            trashcan.setNickname(nickname);
            if (90<latitude || latitude<-90) {
                throw new CustomException(ErrorType.INVALID_LOCATION);
            }
            if (180<longitude || longitude<-180) {
                throw new CustomException(ErrorType.INVALID_LOCATION);
            }
            trashcan.setLatitude(latitude);
            trashcan.setLongitude(longitude);
        }
        else {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
    }

    public Trashcan findOne(Long trashcanId) {
        // 컨트롤러에서 어차피 값들만 반환할거라 여기서는 엔티티 반환해도 됨
        return trashcanRepository.findById(trashcanId).get();
    }
}
