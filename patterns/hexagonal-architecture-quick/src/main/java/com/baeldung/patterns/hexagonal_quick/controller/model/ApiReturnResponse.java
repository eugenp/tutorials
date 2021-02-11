package com.baeldung.patterns.hexagonal_quick.controller.model;

import com.baeldung.patterns.hexagonal_quick.domain.ReturnRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiReturnResponse {
    private ApiBook returnedBook;
    private float overdueCharge;

    public static ApiReturnResponse createFrom(ReturnRecord returnRecord) {
        float overduePrice = returnRecord.getOverdueChargeInCents() / 100f;
        return new ApiReturnResponse(ApiBook.createFrom(returnRecord.getBook()), overduePrice);
    }
}
