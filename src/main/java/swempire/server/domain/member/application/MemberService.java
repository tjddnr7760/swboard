package swempire.server.domain.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swempire.server.domain.member.dao.MemberRepository;
import swempire.server.domain.member.domain.Member;
import swempire.server.domain.member.dto.SignUpRequest;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean signUpMember(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        log.info("sign up in service layer start, member : {}", member.getEmail());
        try {
            verifyMember(member);
        } catch (IllegalStateException e) {
            log.error("sign-up service layer failed, member : {}", member.getEmail());
            return false;
        }
        member.encodeMemberPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        log.info("member sign-up done");
        return true;
    }

    private void verifyMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalStateException();
        }
    }
}
