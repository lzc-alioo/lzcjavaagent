package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//-javaagent:/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-asm/target/lzc-javaagent-asm-0.0.1-SNAPSHOT.jar=demo.MathGame%20run
public class MathGame {
    private static Random random = new Random();

    private int illegalArgumentCount = 0;

    public static void main(String[] args) throws InterruptedException {
        MathGame game = new MathGame();
        while (true) {
            game.run();
            game.otherMethod();
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public void run() throws InterruptedException {
        try {
            int number = random.nextInt() / 1000;
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);

        } catch (Exception e) {
            System.out.println(String.format("debug illegalArgumentCount:%3d, ", illegalArgumentCount) + e.getMessage());
        }

    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        for (int factor : primeFactors) {
            sb.append(factor).append('*');
        }
        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println("debug==" + sb);
        new Hello().testList();
    }

    public List<Integer> primeFactors(int number) {
        if (number < 2) {
            illegalArgumentCount++;
            throw new IllegalArgumentException("debug number is: " + number + ", need >= 2");
        }

        List<Integer> result = new ArrayList<Integer>();
        int i = 2;
        while (i <= number) {
            if (number % i == 0) {
                result.add(i);
                number = number / i;
                i = 2;
            } else {
                i++;
            }
        }

        return result;
    }

    public void otherMethod() {
        int x = 5;
        int y = 2;
        int z = x - y;
        System.out.println("otherMethod z=" + z);
    }
}
