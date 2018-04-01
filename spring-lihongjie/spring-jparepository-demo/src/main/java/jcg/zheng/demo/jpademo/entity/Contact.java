package jcg.zheng.demo.jpademo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jcg.zheng.demo.jpademo.type.PhoneType;

@Entity
@Table(name = "CONTACT")
public class Contact {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "First_Name")
	private String firstName;

	@Column(name = "Last_Name")
	private String lastName;

	@Column(name = "Email")
	private String email;

	@Column(name = "Phone_Number")
	private String phoneNumber;

	@Column(name = "Phone_Type")
	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;

	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Contact_Note.class)
	private List<Contact_Note> notes;

	public Contact() {
	}

	public Contact(String firstName, String lastName, String email, PhoneType phoneType, String phone) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneType = phoneType;
		this.phoneNumber = phone;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PhoneType getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}

	public List<Contact_Note> getNotes() {
		if (this.notes == null) {
			this.notes = new ArrayList<>();
		}
		return this.notes;
	}
	
	public void addNote(Contact_Note note){
		getNotes().add(note);
		note.setContact(this);
	}

	public void setNotes(List<Contact_Note> addresses) {
		this.notes = addresses;
	}

}
