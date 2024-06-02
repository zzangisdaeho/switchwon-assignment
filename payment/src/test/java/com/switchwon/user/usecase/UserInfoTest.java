package com.switchwon.user.usecase;

import com.switchwon.BaseAdaptorMockSetting;
import com.switchwon.user.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
class UserInfoTest extends BaseAdaptorMockSetting {

    @InjectMocks
    private UserInfo userInfo;

    @Test
    public void getUserTest(){
        User findUser = userInfo.getUser(user1.getUserId());

        assertEquals(findUser.getUserId(), user1.getUserId());
        assertEquals(findUser.getPoints(), user1.getPoints());
    }


}