package swempire.server.global.auth.memberDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import swempire.server.domain.member.domain.Member;

import java.util.Collection;
import java.util.Collections;

public class MemberDetails extends Member implements UserDetails {

    public MemberDetails(Member member) {
        super(
                member.getEmail(),
                member.getPassword(),
                member.getName()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
