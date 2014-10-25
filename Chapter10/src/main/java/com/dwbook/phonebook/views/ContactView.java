package com.dwbook.phonebook.views;

import com.dwbook.phonebook.representations.Contact;
import io.dropwizard.views.View;

public class ContactView extends View {

    private final Contact contact;

    public ContactView(Contact contact) {
        super("/views/contact.mustache");
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }
}
