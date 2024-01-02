package com.springsecurity.entities;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public enum Role {
  USER(Collections.emptySet()),

  ADMIN(
      Set.of(
          Permission.ADMIN_CREATE,
          Permission.ADMIN_DELETE,
          Permission.ADMIN_READ,
          Permission.ADMIN_UPDATE,
          Permission.MANAGER_CREATE,
          Permission.MANAGER_DELETE,
          Permission.MANAGER_READ,
          Permission.MANAGER_UPDATE)),
  MANAGER(
      Set.of(
          Permission.MANAGER_CREATE,
          Permission.MANAGER_DELETE,
          Permission.MANAGER_READ,
          Permission.MANAGER_UPDATE));

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.name()))
        .collect(Collectors.toList());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return authorities;
  }

}
