package io.namoosori.tc.utility;

public class NoSuchMembershipException extends RuntimeException {
    //
    private static final long serialVersionUID = 5867172506387382920L;

    public NoSuchMembershipException(String message) {
        super(message);
    }
}