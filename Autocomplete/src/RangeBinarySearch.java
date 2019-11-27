import java.util.Comparator;


enum Direction {
    LOWER, HIGHER
}

public class RangeBinarySearch {

    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N), where N is the length of the array
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new NullPointerException();

        return binarySearch(a, key, comparator, Direction.LOWER);
    }

    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N)
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new NullPointerException();

        return binarySearch(a, key, comparator, Direction.HIGHER);
    }

    private static <Key> int binarySearch(Key[] a, Key key, Comparator<Key> comparator, Direction dir) {

        int lo = 0;
        int hi = a.length - 1;

        while (lo <= hi) {


            int mid;
            if (dir == Direction.LOWER) {
                mid = (hi + lo) / 2;
            } else
                // Fixes rounding error when moving upwards in array
                mid = (int) Math.ceil((hi + lo) / 2.0);


            int compareValue = comparator.compare(a[mid], key);

            if (compareValue > 0) hi = mid - 1;
            else if (compareValue < 0) lo = mid + 1;
            else {
                if (dir == Direction.LOWER) {
                    if (hi - lo <= 1)
                        return mid;
                    hi = mid;
                } else {
                    if (hi - lo <= 1)
                        return mid;
                    lo = mid;
                }
            }
        }

        return -1;
    }
}
