package com.switchwon.paymentimpl.user.out.h2;

import com.switchwon.paymentimpl.user.out.jpa.repository.UserEntityRepository;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("h2")
@Service
@RequiredArgsConstructor
public class UserStoreH2 implements UserStore {

    private final UserEntityRepository userEntityRepository;

    @Override
    public User findById(String userId) {
        return userEntityRepository.findById(userId).orElseThrow();
    }
}
