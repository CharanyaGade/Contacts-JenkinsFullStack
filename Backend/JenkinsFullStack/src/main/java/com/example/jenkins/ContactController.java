package com.example.jenkins;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contactapi")
@CrossOrigin(origins = "*")  // React runs on 5173 by default
public class ContactController {

    private List<Contact> contacts = new ArrayList<>();
    private int nextId = 1;

    // Home
    @GetMapping("/")
    public String home() {
        return "Jenkins Full Stack Deployment API is running...";
    }

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        contact.setId(nextId++);
        contacts.add(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getContactById(@PathVariable int id) {
        for (Contact c : contacts) {
            if (c.getId() == id) {
                return new ResponseEntity<>(c, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Contact with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    // UPDATE
    @PutMapping("/update")
    public ResponseEntity<?> updateContact(@RequestBody Contact updatedContact) {
        for (Contact c : contacts) {
            if (c.getId() == updatedContact.getId()) {
                c.setName(updatedContact.getName());
                c.setPhone(updatedContact.getPhone());
                c.setEmail(updatedContact.getEmail());
                return new ResponseEntity<>(c, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Cannot update. Contact with ID " + updatedContact.getId() + " not found.", HttpStatus.NOT_FOUND);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        boolean removed = contacts.removeIf(c -> c.getId() == id);
        if (removed) {
            return new ResponseEntity<>("Contact with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete. Contact with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
