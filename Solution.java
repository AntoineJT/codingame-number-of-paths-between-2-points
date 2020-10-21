import java.util.*;

class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int M = in.nextInt();
        int N = in.nextInt();
        
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        // populate walls map
        WallMap walls = new WallMap(M, N);
        for (int y = 0; y < M; ++y) {
            String ROW = in.nextLine();
            for (int x = 0; x < N; ++x) {
                walls.put(x, y, ROW.charAt(x));
            }
        }
        in.close();
        
        PathCalculator pc = new PathCalculator(walls);
        System.out.println(pc.numberOfPaths(0, 0));
    }
}

class PathCalculator {
    private final WallMap walls;
    private final int m;
    private final int n;

    public PathCalculator(WallMap walls) {
        this.walls = walls;
        this.m = walls.getM();
        this.n = walls.getN();
    }

    public int numberOfPaths(int x, int y) {
        // if start point or end point is a wall, then no path exists
        if (walls.isWall(x, y) || walls.isWall(n - 1, m - 1))
            return 0;

        // pre-compute some values and feed cache with it
        int[][] cache = new int[n][m];
        for (int b = 0; b < m; ++b) {
            for (int a = 0; a < n; ++a) {
                // if it is a wall, don't count this path as a valid one (0)
                // else mark it as "uncached for now" (-1)
                cache[a][b] = walls.isWall(a, b) ? 0 : -1;
            }
        }
        // map fully explored
        cache[n - 1][m - 1] = 1;

        return recNumberOfPaths(x, y, cache);
    }

    private int recNumberOfPaths(int x, int y, int[][] cache)
    {
        // end of the map exceeded
        if (x == m || y == n)
            return 0;

        // this is cached, so here we go
        if (cache[x][y] != -1)
            return cache[x][y];

// those things are commented because they have been precalculated by numberOfPaths function
// calling this function with empty cache will break it so
/*
        // map fully explored
        if (x == n - 1 && y == m - 1)
            return 1;

        // this is a wall, don't count this path as a valid one
        if (walls.isWall(x, y))
            return 0;
*/

        // this is not cached, so let us cache it
        cache[x][y] = recNumberOfPaths(x + 1, y, cache) + recNumberOfPaths(x, y + 1, cache);
        return cache[x][y];
    }
}

class WallMap {
    private final char[][] data;
    private final int m;
    private final int n;

    public WallMap(int m, int n) {
        this.m = m;
        this.n = n;
        data = new char[n][m];
    }

    public void put(int x, int y, char value) {
        data[x][y] = value;
    }

    public boolean isWall(int x, int y) {
        return get(x, y) == '1';
    }

    public char get(int x, int y) {
        return data[x][y];
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}
