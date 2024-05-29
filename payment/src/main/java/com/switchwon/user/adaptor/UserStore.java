package com.switchwon.user.adaptor;

import com.switchwon.user.domain.User;

public interface UserStore {

    User findById(String id);

    //동시성 방지를 위해 DB에 Lock을 걸고 찾아와야함.
    User findUserForTrade(String id);
}
