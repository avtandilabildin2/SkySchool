package ru.hogwarts.school.exceptions;

public class StudentPrintException extends RuntimeException {
    public StudentPrintException(String message, Throwable cause) {
        super(message, cause);
    }
}
