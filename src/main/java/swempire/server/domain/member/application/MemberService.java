package swempire.server.domain.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swempire.server.domain.member.dao.MemberRepository;
import swempire.server.domain.member.domain.Member;
import swempire.server.domain.member.dto.SignUpDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUpMember(SignUpDto signUpDto) {
        // 멤버로 변환
        Member member = signUpDto.toMember();

        // 이메일 중복여부 검증


        // 회원가입


        //
    }
}
