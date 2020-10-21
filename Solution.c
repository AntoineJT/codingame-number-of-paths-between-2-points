#include <stdio.h>
#include <stdbool.h>

static bool is_cached(int val) {
    return val != -1;
}

/*
    This function is broken when called with an empty (filled with -1) cache
    It must be called by number_of_paths with precomputed cache
*/
static int rec_number_of_paths(const int m, const int n, char const walls[n][m], int cache[n][m], int x, int y) {
    // end of the map exceeded
    if (x == m || y == n)
        return 0;

    // this is cached, so here we go
    if (is_cached(cache[x][y]))
        return cache[x][y];

    // this is uncached, so let us cache it
    cache[x][y] = rec_number_of_paths(m, n, walls, cache, x + 1, y) 
                + rec_number_of_paths(m, n, walls, cache, x, y + 1);
    return cache[x][y];
}

static bool is_wall(char val) {
    return val == '1';
}

int number_of_paths(const int m, const int n, char const walls[n][m], int x, int y) {
    // if start point or end point is a wall, then no path exists
    if (is_wall(walls[x][y]) || is_wall(walls[n - 1][m - 1]))
        return 0;

    // pre-compute some values and feed cache with it
    int cache[n][m]; // one more VLA
    for (int a = 0; a < n; ++a) {
        for (int b = 0; b < m; ++b) {
            // if it is a wall, don't count this path as a valid one (0)
            // else mark it as "uncached for now" (-1)
            cache[a][b] = is_wall(walls[a][b]) ? 0 : -1;
        }
    }
    // map fully explored
    cache[n - 1][m - 1] = 1;
    
    return rec_number_of_paths(m, n, walls, cache, x, y);
}

int main()
{
    int M, N;
    scanf("%d%d", &M, &N);
    fgetc(stdin);
    
    char walls[N][M]; // enjoy VLA
    for (int y = 0; y < M; ++y) {
        char ROW[101];
        fgets(ROW, 101, stdin);
        for (int x = 0; x < N; ++x) {
            walls[x][y] = ROW[x];
        }
    }

    printf("%d\n", number_of_paths(M, N, walls, 0, 0));
    return 0;
}
