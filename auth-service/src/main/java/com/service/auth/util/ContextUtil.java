package com.service.auth.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContextUtil {

    private static final String SYSTEM = "system";

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication) && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return StringUtil.checkNull(userDetails.getUsername());
        }

        return StringUtils.EMPTY;
    }

    public static List<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && !CollectionUtils.isEmpty(authentication.getAuthorities())) {
            return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}