import React, { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [contacts, setContacts] = useState([]);
  const [newContact, setNewContact] = useState({ name: "", phone: "", email: "" });

  // Load contacts from backend
  useEffect(() => {
    fetch("http://localhost:2009/contacts")
      .then((res) => res.json())
      .then((data) => setContacts(data))
      .catch((err) => console.error("Error fetching contacts:", err));
  }, []);

  // Add contact
  const addContact = () => {
    if (!newContact.name || !newContact.phone || !newContact.email) {
      alert("Please fill all fields!");
      return;
    }

    fetch("http://localhost:2009/contacts", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newContact),
    })
      .then((res) => res.json())
      .then((contact) => {
        setContacts([...contacts, contact]);
        setNewContact({ name: "", phone: "", email: "" });
      })
      .catch((err) => console.error("Error adding contact:", err));
  };

  // Delete contact
  const deleteContact = (id) => {
    fetch(`http://localhost:2009/contacts/${id}`, {
      method: "DELETE",
    })
      .then(() => setContacts(contacts.filter((c) => c.id !== id)))
      .catch((err) => console.error("Error deleting contact:", err));
  };

  return (
    <div className="container">
      <h1>📖 Contact Book</h1>

      {/* Input Form */}
      <div className="form">
        <input
          type="text"
          placeholder="Name"
          value={newContact.name}
          onChange={(e) => setNewContact({ ...newContact, name: e.target.value })}
        />
        <input
          type="text"
          placeholder="Phone"
          value={newContact.phone}
          onChange={(e) => setNewContact({ ...newContact, phone: e.target.value })}
        />
        <input
          type="email"
          placeholder="Email"
          value={newContact.email}
          onChange={(e) => setNewContact({ ...newContact, email: e.target.value })}
        />
        <button onClick={addContact}>➕ Add Contact</button>
      </div>

      {/* Contact List */}
      <ul className="list">
        {contacts.map((c) => (
          <li key={c.id}>
            <span>{c.name} | {c.phone} | {c.email}</span>
            <button onClick={() => deleteContact(c.id)}>❌ Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;