package com.app.restapimesen.events.user;

import com.app.restapimesen.entity.user.Users;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UserInsertEvent extends ApplicationEvent {
    private final Users users;

    public UserInsertEvent(Users users) {
        super(users);
        this.users = users;
    }

}
