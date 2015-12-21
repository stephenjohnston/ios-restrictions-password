package org.sjj;

/**
 * Created by stephen on 12/18/15.
 */
public class Guess {
    private boolean result;
    private String value;

    public Guess(boolean result, String value) {
        this.result = result;
        this.value = value;
    }

    public boolean isResult() {
        return result;
    }

    public String getValue() {
        return value;
    }
}
