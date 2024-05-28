package com.switchwon.user.usecase;

import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInfo {

    private final UserStore userStore;

    public User getUser(String userId){
        return userStore.findById(userId);
    }
}
