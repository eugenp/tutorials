package com.baeldung.mockito.callbacks;

public class Response {

    private Data data;
    private boolean isValid = true;

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

}
