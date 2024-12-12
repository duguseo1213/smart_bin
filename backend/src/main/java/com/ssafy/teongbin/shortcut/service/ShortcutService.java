package com.ssafy.teongbin.shortcut.service;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.common.jwt.PrincipalDetails;
import com.ssafy.teongbin.shortcut.dto.request.AddShortcutRequestDto;
import com.ssafy.teongbin.shortcut.dto.request.DeleteShortcutRequestDto;
import com.ssafy.teongbin.shortcut.dto.request.UpdateShortcutRequestDto;
import com.ssafy.teongbin.shortcut.dto.response.ShortcutResponseDto;
import com.ssafy.teongbin.shortcut.entity.Shortcut;
import com.ssafy.teongbin.shortcut.repository.ShortcutRepository;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortcutService {
    private final ShortcutRepository shortcutRepository;
    private final UserRepository userRepository;

    // 숏컷 등록
    @Transactional
    public void addShortcut(PrincipalDetails user, AddShortcutRequestDto addShortcutRequestDto) {
        Optional<User> ou = userRepository.findByEmail(user.getUsername());
        if (ou.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
        // latitude, longitude 범위 이탈 여부
        Double latitude = addShortcutRequestDto.getLatitude();
        Double longitude = addShortcutRequestDto.getLongitude();

        if (latitude > 90 || latitude < -90) {
            throw new CustomException(ErrorType.INVALID_LOCATION);
        }
        if (longitude > 180 || longitude < -180) {
            throw new CustomException(ErrorType.INVALID_LOCATION);
        }

        //==============================
        // 줌 레벨 범위 이탈 여부 => 나중에 해야함.
//        int zoom_level = addShortcutRequestDto.getZoom_level();
//        if (zoom_level < 0) {
//            throw new CustomException(ErrorType.INVALID_ZOOM_LEVEL);
//        }
        //==============================

        Shortcut shortcut = Shortcut.builder()
                .user(ou.get())
                .nickname(addShortcutRequestDto.getNickname())
                .latitude(addShortcutRequestDto.getLatitude())
                .longitude(addShortcutRequestDto.getLongitude())
                .zoom_level(addShortcutRequestDto.getZoom_level())
                .build();
        shortcutRepository.save(shortcut);
    }

    // 숏컷 삭제
    @Transactional
    public void deleteShortcut(PrincipalDetails user, Long shortcut_id) {
        // User 존재 여부 검사
        Optional<User> ou = userRepository.findByEmail(user.getUsername());
        if (ou.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
        // Shortcut 존재 여부 검사
        Optional<Shortcut> optionalShortcut = shortcutRepository.findById(shortcut_id);
        if (optionalShortcut.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_SHORTCUT);
        }
        // 유저와 shortcut의 user가 다르다면?
        if (ou.get() != optionalShortcut.get().getUser()) {
            throw new CustomException(ErrorType.USER_SHORTCUT_MISMATCH);
        }
        // 삭제 로직
        shortcutRepository.deleteById(shortcut_id);
    }


    // 숏컷 수정
    @Transactional
    public void updateShortcut(PrincipalDetails user, Long shortcut_id, UpdateShortcutRequestDto dto) {
        // User 존재 여부 검사
        Optional<User> ou = userRepository.findByEmail(user.getUsername());
        if (ou.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
        // Shortcut 존재 여부 검사
        Optional<Shortcut> optionalShortcut = shortcutRepository.findById(shortcut_id);
        if (optionalShortcut.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_SHORTCUT);
        }
        // 유저와 shortcut의 user가 다르다면?
        if (ou.get() != optionalShortcut.get().getUser()) {
            throw new CustomException(ErrorType.USER_SHORTCUT_MISMATCH);
        }

        // 수정 로직
        Shortcut shortcut = optionalShortcut.get();
        shortcut.setNickname(dto.getNickname());

        shortcutRepository.save(shortcut);
    }







    // 숏컷 리스트
    public List<ShortcutResponseDto> getShortcutList(PrincipalDetails user) {
        // User 존재 여부 검사
        Optional<User> ou = userRepository.findByEmail(user.getUsername());
        if (ou.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
        List<Shortcut> shortcutList = shortcutRepository.findByUserId(ou.get().getId());

        List<ShortcutResponseDto> shortcutResponseDtoList = shortcutList.stream()
                .map(shortcut ->
                        ShortcutResponseDto
                                .fromEntity(
                                        shortcut.getId(),
                                        shortcut.getNickname(),
                                        shortcut.getLatitude(),
                                        shortcut.getLongitude(),
                                        shortcut.getZoom_level())
                )
                .toList();

        return shortcutResponseDtoList;
    }

}
