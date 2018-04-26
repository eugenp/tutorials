package com.baeldung.exceptions;

public class ExceptionsService {
    
    private static String VALUE_VALID = "valid value";
    private static String VALUE_NA = "NA";
    private static String VALUE_EMPTY = "";
    
    public static void throwUncheckedException() {
        throw new RuntimeException("Something wrong");
    }

    public static void throwSomeException() throws SomeException {
        throw new SomeException("Something wrong");
    }

    public static void throwYetAnotherException() throws YetAnotherException {
        throw new YetAnotherException("Something wrong");
    }
    
    
    public static String call_throwUncheckedException() {
        throwUncheckedException();
        return VALUE_VALID;
    }

    public static String call_throwUncheckedException_catch() {
        try {
            throwUncheckedException();
            return VALUE_VALID;
        } catch (RuntimeException e) {
            return VALUE_EMPTY;
        }
    }

    public static String call_throwUncheckedException_catchAsParent() {
        try {
            throwUncheckedException();
            return VALUE_VALID;
        } catch (Exception e) {
            return VALUE_EMPTY;
        }
    }

    public static String call_throwSomeException_catch() {
        try {
            throwSomeException();
            return VALUE_VALID;
        } catch (SomeException e) {
            return VALUE_EMPTY;
        }
    }

    public static String call_throwSomeException_throws() throws SomeException {
        throwSomeException();
        return VALUE_VALID;
    }

    public static String call_throwCheckedException_rethrowChained() throws YetAnotherException {
        try {
            throwSomeException();
            return VALUE_VALID;
        } catch (SomeException e) {
            throw new YetAnotherException("SomeException caught " + e.getMessage(), e);
        }
    }
    
    public static String call_throwSomeOtherException_catch() {
        try {
            throwSomeException();
            return VALUE_VALID;
        }
        catch (SomeException e) {
            return VALUE_NA;
        }
        catch (Exception e) {
            return VALUE_EMPTY;
        }
    }

    public static String call_bothCheckedExceptions_throws() throws YetAnotherException, SomeException {
        throwYetAnotherException();
        throwSomeException();
        return VALUE_VALID;
    }

    public static String call_bothCheckedExceptions_joinCatch() {
        try {
            throwYetAnotherException();
            throwSomeException();
            return VALUE_VALID;
        }
        catch (YetAnotherException | SomeException e) {
            return VALUE_NA;
        }
        catch (Exception e) {
            return VALUE_EMPTY;
        }
    }

    public static String call_throwSomeOtherException_splitCatch() {
        try {
            throwYetAnotherException();
            throwSomeException();
            return VALUE_VALID;
        }
        catch (SomeException e) {
            return VALUE_NA;
        }
        catch (YetAnotherException e) {
            return VALUE_EMPTY;
        }
    }

    public static String call_bothCheckedExceptions_throwsParent() throws Exception {
        throwYetAnotherException();
        throwSomeException();
        return VALUE_VALID;
    }

    public static String call_bothCheckedExceptions_catchParent() {
        try {
            throwYetAnotherException();
            throwSomeException();
            return VALUE_VALID;
        }
        catch (Exception e) {
            return VALUE_EMPTY;
        }
    }
}
