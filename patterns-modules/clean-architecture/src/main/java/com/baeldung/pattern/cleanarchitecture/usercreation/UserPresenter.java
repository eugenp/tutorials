package com.baeldung.pattern.cleanarchitecture.usercreation;

interface UserPresenter {
    UserResponseModel prepareSuccessView(UserResponseModel user);

    UserResponseModel prepareFailView(String error);
}
