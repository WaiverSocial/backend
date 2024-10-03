package com.github.waiversocial.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {

    @NonNull
    private String username, password;

}