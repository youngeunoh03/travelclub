package io.namoosori.tc.utility;

public class NoSuchBoardException extends RuntimeException{
    //
    private static final long serialVersionUID = 5867172506387382920L;

    public NoSuchBoardException(String message){
        super(message);
    }
}
