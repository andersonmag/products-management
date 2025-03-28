package com.example.empiretechtestbackendjava.util;

import com.example.empiretechtestbackendjava.domain.User;

public class UserFactoryTest {

    public static User getModel() {
        return new User(1l, "user teste", "username", "123");
    }

}
