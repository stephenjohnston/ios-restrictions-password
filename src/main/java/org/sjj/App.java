package org.sjj;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java 8 Streams approach
 */
public class App 
{
    public String padWithFourZeros(int i) {
        return String.format("%04d", i);
    }
    public static Stream<String> findCode(String hash)
    {
        App app = new App();
        return IntStream.range(1, 10000)
                .mapToObj(app::padWithFourZeros)
                .map(s -> {
                    return new Guess(Pbkdf2.checkPbkdf2(hash, s), s);
                })
                .filter(guess -> guess.isResult())
                .map(guess -> guess.getValue());
    }

    public static void main(String[] args) {
        Optional<String> result = findCode("A+g=$aSbUXg==$M/p4734c8/SOXZnGgZot+BciAW0=").findFirst();
        if (result.isPresent())
            System.out.println(result.get());
        else
            System.out.println("not found");
    }
}
