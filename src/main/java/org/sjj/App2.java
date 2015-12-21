package org.sjj;

/**
 *  Iterative approach
 */
public class App2 {
    public static String findCode(String hash) {
        boolean stop = false;
        String found_passcode = null;
        int i = 0;

        while (!stop) {
            String s = String.format("%04d", i);
                if (Pbkdf2.checkPbkdf2(hash, s)) {
                    found_passcode = s;
                    stop = true;
                }
                else {
                    i++;
                }
            if (i > 9999) {
                stop = true;
            }
        }

        return found_passcode;
    }

    public static void main(String[] args) {
        String result = findCode("A+g=$aSbUXg==$M/p4734c8/SOXZnGgZot+BciAW0=");
        System.out.println("result: " + result);
    }
}

// keep track of the stop variable
// keep track of i, did we increment it in just the right place?  What happens if it doesn't increment?
// keep track of found_passcode
// off by one error?
// did we loop too many times?  did we loop not enough?
// variables here are mutated, luckily they are all local variables.
// if these had been member variables or even worse, static variables, things get even more complex (subtle bugs even in single threaded app, but what about multi-threaded?).
