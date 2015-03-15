/**
 * Assignment to teach dynamic programming using 3 simple example problems:
 * 1. Fibonacci numbers
 * 2. Longest common subsequence
 * 3. Edit distance
 * 
 * @author Julia Ting (julia.ting@gatech.edu)
 */


//Carey Crook


public class DynamicProgrammingAssignment {
	public static int num_calls = 0; //DO NOT TOUCH

	/**
	 * Calculates the nth fibonacci number: fib(n) = fib(n-1) + fib(n-2).
	 * Remember that fib(0) = 0 and fib(1) = 1.
	 * 
	 * This should NOT be done recursively - instead, use a 1 dimensional
	 * array so that intermediate fibonacci values are not re-calculated.
	 * 
	 * The running time of this function should be O(n).
	 * 
	 * @param n A number 
	 * @return The nth fibonacci number
	 */
	public static int fib(int n) {
		num_calls++; //DO NOT TOUCH
        int[] arr = new int[n + 1];
        arr[0] = 0;
        if (n == 0) { return arr[0]; }
        arr[1] = 1;
        if (n == 1) { return arr[1]; }
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
		return arr[n];
	}
	
	/**
	 * Calculates the length of the longest common subsequence between a and b.
	 * 
	 * @param a
	 * @param b
	 * @return The length of the longest common subsequence between a and b
	 */
	public static int lcs(String a, String b) {
		num_calls++; //DO NOT TOUCH
        int[][] res = new int[a.length() + 1][b.length() + 1];
        for (int j = 1; j < b.length() + 1; j++) {
            for (int i = 1; i < a.length() + 1; i++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    res[i][j] = res[i-1][j-1] + 1;
                } else {
                    if (res[i - 1][j] > res[i][j-1]) {
                        res[i][j] = res[i - 1][j];
                    } else {
                        res[i][j] = res[i][j-1];
                    }
                }
            }
        }
		return res[a.length()][b.length()];
	}

	/**
	 * Calculates the edit distance between two strings.
	 * 
	 * @param a A string
	 * @param b A string
	 * @return The edit distance between a and b
	 */
	public static int edit(String a, String b) {
		num_calls++; //DO NOT TOUCH
        int[][] res = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i < res.length; i++) {
            res[i][0] = res[i - 1][0] + 1;
        }
        for (int i = 1; i < res[0].length; i++) {
            res[0][i] = res[0][i - 1] + 1;
        }

        for (int i = 1; i < a.length() + 1; i++) {
            for (int j = 1; j < b.length() + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    /*if (res[i-1][j] < res[i][j-1]) {
                        if ()
                    }*/
                    res[i][j] = res[i-1][j-1];
                } else {
                    if (res[i-1][j-1] < res[i-1][j] && res[i-1][j-1] < res[i][j-1]) {
                        res[i][j] = res[i-1][j-1] + 1;
                    } else if (res[i-1][j] < res[i][j-1]) {
                        res[i][j] = res[i-1][j] + 1;
                    } else {
                        res[i][j] = res[i][j-1] + 1;
                    }
                }
            }
        }
		return res[a.length()][b.length()];
	}
}

