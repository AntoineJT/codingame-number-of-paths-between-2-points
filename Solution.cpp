#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <cassert>

using WallGrid = std::vector<std::string>;

class WallMap 
{
    using Cache = std::vector<std::vector<int>>;
    static constexpr int UNCACHED = -1;

    WallGrid data;
    int m;
    int n;

    int RecNumberOfPaths(int x, int y, Cache& cache)
    {
        // end of the map exceeded
        if (x == m || y == n)
            return 0;

        // this is cached, so here we go
        if (cache.at(x).at(y) != UNCACHED)
            return cache.at(x).at(y);

        // this is uncached, so let's cache it
        cache[x][y] = RecNumberOfPaths(x + 1, y, cache) 
                    + RecNumberOfPaths(x, y + 1, cache);
        return cache.at(x).at(y);
    }

    public:
        WallMap(int m, int n): m(m), n(n), data({}) {}

        bool IsWall(int x, int y)
        {
            return data.at(x).at(y) == '1';
        }

        void AddRow(const std::string row)
        {
            data.push_back(row);
        }

        int ComputeNumberOfPaths(int x, int y)
        {
            // if start point or end point is a wall, then no path exists
            if (IsWall(x, y) || IsWall(n - 1, m - 1))
                return 0;

            // pre-compute some values and feed cache with it
            Cache cache = {};
            for (int a = 0; a < n; ++a)
            {
                cache.push_back({});
                for (int b = 0; b < m; ++b)
                {
                    // if it is a wall, don't count this path as a valid one (0)
                    // else mark it as "uncached for now" (-1)
                    cache[a].push_back(IsWall(a, b) ? 0 : UNCACHED);
                }
            }
            // map fully explored
            cache[n - 1][m - 1] = 1;

            return RecNumberOfPaths(x, y, cache);
        }
};

int main()
{
    int M;
    std::cin >> M; std::cin.ignore();
    int N;
    std::cin >> N; std::cin.ignore();

    auto walls = WallMap(M, N);
    for (int i = 0; i < M; ++i) {
        std::string ROW;
        getline(std::cin, ROW);
        walls.AddRow(std::move(ROW));
    }

    std::cout << walls.ComputeNumberOfPaths(0, 0) << std::endl;
    return 0;
}
