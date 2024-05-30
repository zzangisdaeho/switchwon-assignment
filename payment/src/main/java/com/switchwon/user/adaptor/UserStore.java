package com.switchwon.user.adaptor;

import com.switchwon.user.domain.User;

public interface UserStore {

    User findById(String userId);
}
