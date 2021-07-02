package com.baeldung.hexagonalarchinjava.infrastracture.repository.cassandra;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType
public class ParticipantEntity {

	private UUID participantId;
	private String email;
	private String name;
	private Long contactNumber;
	private int age;

	public ParticipantEntity(UUID participantId, String email, String name, Long contactNumber, int age) {
		super();
		this.participantId = participantId;
		this.email = email;
		this.name = name;
		this.contactNumber = contactNumber;
		this.age = age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((participantId == null) ? 0 : participantId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipantEntity other = (ParticipantEntity) obj;
		if (participantId == null) {
			if (other.participantId != null)
				return false;
		} else if (!participantId.equals(other.participantId))
			return false;
		return true;
	}

	public UUID getParticipantId() {
		return participantId;
	}

	public void setParticipantId(UUID participantId) {
		this.participantId = participantId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
