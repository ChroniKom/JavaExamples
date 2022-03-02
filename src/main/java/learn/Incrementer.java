package learn;

public class Incrementer {
    public static void main(String[] args) {
        Incrementer incrementer = new Incrementer();

        System.out.println(incrementer.increment(new int[] {9, 9, 9}));
    }

    public int[] increment(int[] num) {
        for (int i = num.length - 1; i >= 0; i--) {
            if (num[i] < 9) {
                num[i]++;
                return num;
            } else {
                num[i] = 0;
            }
        }

        int[] results = new int[num.length + 1];
        results[0] = 1;

        return results;
    }
}
