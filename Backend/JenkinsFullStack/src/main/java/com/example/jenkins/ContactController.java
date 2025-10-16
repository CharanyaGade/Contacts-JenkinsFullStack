package com.example.jenkins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")   // root context
@CrossOrigin(origins = "*") // allow React frontend calls
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // ROOT endpoint
    @GetMapping
    public String home() {
        return "Jenkins Full Stack Deployment API is running...";
    }

    // CREATE
    @PostMapping("contacts")
    public Contact addContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // READ ALL
    @GetMapping("contacts")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // READ ONE
    @GetMapping("contacts/{id}")
    public Optional<Contact> getContactById(@PathVariable int id) {
        return contactRepository.findById(id);
    }

    // UPDATE
    @PutMapping("contacts/{id}")
    public Contact updateContact(@PathVariable int id, @RequestBody Contact updatedContact) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(updatedContact.getName());
                    contact.setPhone(updatedContact.getPhone());
                    contact.setEmail(updatedContact.getEmail());
                    return contactRepository.save(contact);
                })
                .orElseThrow(() -> new RuntimeException("Contact not found with id " + id));
    }

    // DELETE
    @DeleteMapping("contacts/{id}")
    public String deleteContact(@PathVariable int id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return "Contact deleted with id: " + id;
        }
        return "No contact found with id: " + id;
    }
}
