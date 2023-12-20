package swempire.server.global.auth.memberDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import swempire.server.domain.member.dao.MemberRepository;
import swempire.server.domain.member.domain.Member;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Member member = byEmail.orElseThrow(() -> new IllegalStateException("회원 정보가 없습니다."));

        return new MemberDetails(member);
    }
}
