package com.app.androidinitialproject.domain.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IntDef({
        InputErrorType.EMAIL, InputErrorType.PASSWORD, InputErrorType.VRM_NUMBER, InputErrorType.FIRST_NAME,
        InputErrorType.NEW_PASSWORD, InputErrorType.CONFIRM_PASSWORD, InputErrorType.MISMATCH_PASSWORD, InputErrorType.LAST_NAME
})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface InputErrorType {
    int EMAIL = 1;
    int PASSWORD = 2;
    int VRM_NUMBER = 3;
    int FIRST_NAME = 4;
    int LAST_NAME = 5;
    int MOBILE = 6;
    int NEW_PASSWORD = 7;
    int CONFIRM_PASSWORD = 8;
    int MISMATCH_PASSWORD = 9;

}
