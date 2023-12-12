package swempire.server.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swempire.server.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
