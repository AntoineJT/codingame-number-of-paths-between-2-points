import java.util.*;

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
        in.close();
        // System.out.println(dump);
        PathCalculator pc = new PathCalculator(walls, M, N);

        System.out.println(pc.numberOfPaths(0, 0));
    }
}

class PathCalculator {
    private final WallMap walls;
    private final int m;
    private final int n;

    public PathCalculator(WallMap walls, int m, int n) {
        this.walls = walls;
        this.m = m;
        this.n = m;
    }

    public int numberOfPaths(int x, int y) {
        // if start point or end point is a wall, then no path exists
        if (walls.isWall(x, y) || walls.isWall(n - 1, m - 1)) {
            return 0;
        }

       return recNumberOfPaths(x, y);
    }

    private int recNumberOfPaths(int x, int y) 
    {
        // end of the map exceeded
        if (x == m || y == n)
            return 0;

        // map fully explored
        if (x == n - 1 && y == m - 1)
            return 1;

        // this is a wall, don't count this path as a valid one
        if (walls.isWall(x, y))
            return 0;

        return recNumberOfPaths(x + 1, y) + recNumberOfPaths(x, y + 1);
    }
}

class WallMap {
    private final Map<String, Boolean> data;

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
