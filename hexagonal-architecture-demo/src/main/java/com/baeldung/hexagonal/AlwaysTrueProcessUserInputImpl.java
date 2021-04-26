package com.baeldung.hexagonal;

class AlwaysTrueProcessUserInputImpl implements ProcessUserInput {
    
    @Override
    public boolean processUserInput() {         
        return true;
    }

    @Override
    public void presentError(String message) {
        System.err.println(message);
    }
}