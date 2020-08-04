package com.baeldung.hexagonal.application.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class IssueBookRequest {

	@NotNull private UUID memberId;
	
	@NotNull private String memberName;
	
	@JsonCreator
	public IssueBookRequest(@JsonProperty("memberId") final UUID memberId,
			@JsonProperty("memberName") final String memberName)
	{
		this.memberId = memberId;
		this.memberName = memberName;
	}
	
	public UUID getMemberId() {
		return memberId;
	}
	
	public String getMemberName() {
		return memberName;
	}
}

