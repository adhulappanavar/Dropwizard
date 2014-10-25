package com.dwbook.phonebook.dao;

import org.skife.jdbi.v2.sqlobject.*;

public interface UserDAO {

    @SqlQuery("select count(*) from users where username = :username and password = :password")
    int getUser(@Bind("username") String username, @Bind("password") String password);
}
