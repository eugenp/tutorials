package com.baeldung.pattern.cleanarchitecture.usercreation;

public interface UserInputBoundary {
    UserResponseModel create(UserRequestModel requestModel);
}
