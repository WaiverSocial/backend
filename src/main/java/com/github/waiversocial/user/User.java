package com.github.waiversocial.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {

    private final String username, password;
    private final long userid;

}