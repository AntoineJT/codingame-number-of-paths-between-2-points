class WallMap:
    def __init__(self):
        self._data = {}

    def get(self, x, y) -> int:
        return self._data.get(self._getkey(x, y))

    def put(self, x, y, val: int) -> None:
        self._data[self._getkey(x, y)] = val

    def is_wall(self, x, y) -> bool:
        return int(self.get(x, y)) == 1

    @staticmethod
    def _getkey(x, y) -> str:
        return f"{x},{y}"

class PathCalculator:
    def __init__(self, walls: WallMap, m: int, n: int):
        self._walls = walls
        self._m = m
        self._n = n

    def number_of_paths(self, x: int, y: int) -> int:
        # if start point or end point is a wall, then no path exists
        if self._walls.is_wall(x, y) \
            or self._walls.is_wall(self._n - 1, self._m - 1):
            return 0
        
        # pre-compute some values and feed cache with it
        cache = {}
        for a in range(n):
            cache[a] = {}
            for b in range(m):
                # if it is a wall, don't count this path as a valid one (0)
                # else mark it as "uncached for now" (-1)
                cache[a][b] = 0 if self._walls.is_wall(a, b) else -1
        # map fully explored
        cache[n - 1][m - 1] = 1

        return self._rec_number_of_paths(x, y, cache)

    def _rec_number_of_paths(self, x, y, cache) -> int:
        """
        This function is broken when called with an empty (filled with -1) cache
        It must be called by numberOfPaths with precomputed cache
        """
        
        # end of the map exceeded
        if x == m or y == n:
            return 0

        # this is cached, so here we go
        if cache[x][y] != -1:
            return cache[x][y]

        # this is not cached, so let us cache it
        cache[x][y] = self._rec_number_of_paths(x + 1, y, cache) \
                    + self._rec_number_of_paths(x, y + 1, cache)
        return cache[x][y]


m = int(input())
n = int(input())

wallmap = WallMap()

for y in range(m):
    row = input()
    for x in range(n):
        wallmap.put(x, y, row[x])

pc = PathCalculator(wallmap, m, n)
print(pc.number_of_paths(0, 0))

