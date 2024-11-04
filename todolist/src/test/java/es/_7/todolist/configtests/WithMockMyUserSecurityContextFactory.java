package es._7.todolist.configtests;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class WithMockMyUserSecurityContextFactory implements WithSecurityContextFactory<WithMockMyUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockMyUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(customUser.userSub())
                .password("")
                .authorities(Collections.emptyList())
                .build();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, "", Collections.emptyList());

        context.setAuthentication(auth);
        return context;
    }
}