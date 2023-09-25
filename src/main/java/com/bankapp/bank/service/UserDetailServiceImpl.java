package com.bankapp.bank.service;

import com.bankapp.bank.dao.UserDao;
import com.bankapp.bank.domain.UserEntity;
import com.bankapp.bank.model.SecurityUserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new SecurityUserContext(userEntity.getId(), username, userEntity.getPassword(), userEntity.isEnabled(), userEntity.getAuthorities());
    }
}
