package com.example.jenkins;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "*")  // React runs on 5173 by default
public class ContactController {

    private List<Contact> contacts = new ArrayList<>();
    private int nextId = 1;

    // CREATE
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        contact.setId(nextId++);
        contacts.add(contact);
        return contact;
    }

    // READ
    @GetMapping
    public List<Contact> getAllContacts() {
        return contacts;
    }

    // UPDATE
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable int id, @RequestBody Contact updatedContact) {
        for (Contact c : contacts) {
            if (c.getId() == id) {
                c.setName(updatedContact.getName());
                c.setPhone(updatedContact.getPhone());
                c.setEmail(updatedContact.getEmail());
                return c;
            }
        }
        return null;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id) {
        contacts.removeIf(c -> c.getId() == id);
        return "Contact deleted with id: " + id;
    }
}
