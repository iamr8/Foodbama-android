package com.mikaco.foodbama.r8;

/**
 * Created by Arash on 1/11/2018.
 */

public class Enums {
    public enum mainEnum {
        NetError,
        Connected,
        NeedLogin

    }
    public enum mainViewStatus {
        error("error",0),
        success("success",1);

        private String stringValue;
        private int intValue;
        private mainViewStatus(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        @Override
        public String toString() {
            return stringValue;
        }

    }
}
