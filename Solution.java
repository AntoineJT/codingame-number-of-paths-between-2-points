import java.util.*;
import java.io.*;
import java.math.*;

class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int M = in.nextInt();
        int N = in.nextInt();
        
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        // String dump = "";
        // populate walls map
        WallMap walls = new WallMap();
        for (int y = 0; y < M; ++y) {
            String ROW = in.nextLine();
            int x = 0;
            for (char c : ROW.toCharArray()) {
                // dump += x + "," + y + " " + c + "\n";
                walls.put(x, y, c == '1');
                ++x;
            }
        }
        // System.out.println(dump);

        System.out.println(numberOfPaths(M, N, walls));
    }

    public static int numberOfPaths(int m, int n, WallMap walls) {
        // if start point or end point is a wall, then no path exists
        if (walls.isWall(0, 0) || walls.isWall(n - 1, m - 1)) {
            return 0;
        }

        return recNumberOfPaths(m, n);
    }

    private static int recNumberOfPaths(int m, int n) 
    {
        if (m == 1 || n == 1) 
            return 1;

        return recNumberOfPaths(m - 1, n) + recNumberOfPaths(m, n - 1);
    }
}

class WallMap {
    private Map<String, Boolean> data;

    public WallMap() {
        data = new HashMap<>();
    }

    public void put(int x, int y, boolean value) {
        data.put(getWallKey(x, y), value);
    }

    public boolean isWall(int x, int y) {
        return get(x, y);
    }

    public boolean get(int x, int y) {
        return data.get(getWallKey(x, y));
    }

    private static String getWallKey(int x, int y) {
        return x + "," + y;
    }
}
