package com.app.androidinitialproject.domain.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({DataRequestType.USER_LOGIN, DataRequestType.SYNC_DATA, DataRequestType.TICKET_IMAGE, DataRequestType.DRIVE_OFF
})
public @interface DataRequestType {

    int USER_LOGIN = 101;
    int SYNC_DATA = 102;
    int TICKET_IMAGE = 103;
    int DRIVE_OFF = 104;


}
