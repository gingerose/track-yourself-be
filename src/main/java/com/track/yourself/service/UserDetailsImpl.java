package com.track.yourself.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.track.yourself.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final Integer serialVersionUID = 1;

    private Integer id;
    private String login;

    @JsonIgnore
    private String password;
    private String picture;
    private String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String login, String password, String picture, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.picture = picture;
        this.username = username;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserDetailsImpl(
                user.getUser_id(),
                user.getLogin(),
                user.getPassword(),
                user.getPicture(),
                user.getUsername(), authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public Integer getUserId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }
    public String getName() {
        return username;
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
