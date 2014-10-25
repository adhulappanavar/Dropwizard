package com.dwbook.phonebook;

import org.skife.jdbi.v2.DBI;
import com.dwbook.phonebook.dao.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class PhonebookAuthenticator implements Authenticator<BasicCredentials, Boolean> {

    private final UserDAO userDao;

    public PhonebookAuthenticator(DBI jdbi) {
        userDao = jdbi.onDemand(UserDAO.class);
    }

    public Optional<Boolean> authenticate(BasicCredentials c) throws AuthenticationException {
        boolean validUser = (userDao.getUser(c.getUsername(), c.getPassword()) == 1);
        if (validUser) {
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
