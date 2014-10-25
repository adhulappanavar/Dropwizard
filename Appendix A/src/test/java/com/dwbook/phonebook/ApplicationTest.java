package com.dwbook.phonebook;

import static org.fest.assertions.api.Assertions.assertThat;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import com.dwbook.phonebook.representations.Contact;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import io.dropwizard.testing.junit.DropwizardAppRule;

public class ApplicationTest {
	
	private Client client;
	
	private Contact contactForTest = new Contact(0, "Jane", "Doe", "+987654321");
	
    @ClassRule
    public static final DropwizardAppRule<PhonebookConfiguration> RULE =
            new DropwizardAppRule<PhonebookConfiguration>(App.class, "config.yaml");
    
    @Before
    public void setUp() {
    	client = new Client();
        // Set the credentials to be used by the client
        client.addFilter(new HTTPBasicAuthFilter("wsuser", "wsp1"));
    }
    
    @Test
    public void createAndRetrieveContact() {
    	// Create a new contact by performing the appropriate http request (POST)
        WebResource contactResource = client.resource("http://localhost:8080/contact");
		ClientResponse response = contactResource
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, contactForTest);
		// Check that the response has the appropriate response code (201)
        assertThat(response.getStatus()).isEqualTo(201);
        
        // Retrieve the newly created contact
        String newContactURL = response.getHeaders().get("Location").get(0);
        WebResource newContactResource = client.resource(newContactURL);
        Contact contact = newContactResource.get(Contact.class);
        // Check that it has the same properties as the initial one
        assertThat(contact.getFirstName()).isEqualTo(contactForTest.getFirstName());
        assertThat(contact.getLastName()).isEqualTo(contactForTest.getLastName());
        assertThat(contact.getPhone()).isEqualTo(contactForTest.getPhone());
    }
}