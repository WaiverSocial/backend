package com.github.waiversocial.channel;

import com.github.waiversocial.user.User;

import java.io.File;
import java.util.List;

public interface IChannel {
    int send(User user, String data);
    List<Integer> getUsers();
    ChannelType getType();
}
