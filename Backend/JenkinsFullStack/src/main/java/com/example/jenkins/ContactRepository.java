package com.example.jenkins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    // JpaRepository already provides CRUD methods:
    // save(), findById(), findAll(), deleteById(), etc.
}
