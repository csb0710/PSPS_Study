package com.study.psver1.service;

import com.study.psver1.dto.MemberDTO;
import com.study.psver1.entitiy.MemberEntity;
import com.study.psver1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일 db에서 조회
        // 2. db에서 조회한 비밀번호와 회원이 입력한 비밀번호 일치여부 확인

        Optional<MemberEntity> byMemberEmail =
                memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if(byMemberEmail.isPresent()){
            // 조회 결과 있음.
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // 비밀번호 일치
                // entity 객체 -> dto 객체 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else{
                // 비밀번호 불일치 ( 로그인 실패 )
                return null;
            }
        }else{
            // 조회 결과 없음.
            return null;
        }
    }
}
