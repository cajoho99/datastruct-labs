/**
 *  The {@code Quick} class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/23quick">Section 2.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
import java.util.*;

public class Quick {
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(int[] a) {
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    public static void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;

        // Use insertionsort for small arrays
        if(hi - lo < 10){
            Insertion.sort(a, lo, hi);
            return;
        }

        int pivot = partition(a, lo, hi);
        sort(a, lo, pivot-1);
        sort(a, pivot+1, hi);
        assert isSorted(a, lo, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(int[] a, int lo, int hi) {
        int low = lo;
        int high = hi + 1;

        int mid = (lo + hi)/2;

        bound(a, lo, mid, hi);

        int v = a[lo];

        // a[lo] is unique largest element
        while (a[++low] < v) {
        }

        // a[lo] is unique smallest element
        while (v < a[--high]) {
        }

        // the main loop
        while (low < high) {
            exch(a, low, high);
            while (a[++low] < v) ;
            while (v < a[--high]) ;
        }

        // put partitioning item v at a[high]
        exch(a, lo, high);

        // now, a[lo .. high-1] <= a[high] <= a[high+1 .. hi]
        return high;
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/

    // exchange a[i] and a[j]
    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // return the index of the median element among a[i], a[j], and a[k]
    private static final int median3(int[] a, int i, int j, int k) {
        if (a[i] <= a[j]) {
            if (a[j] <= a[k]) return j;      /* a[i] <= a[j] <= a[k] */
            else if (a[i] <= a[k]) return k; /* a[i] <= a[k] <  a[j] */
            else return i;                   /* a[k] <  a[i] <= a[j] */
        } else {
            if (a[k] <= a[j]) return j;      /* a[k] <= a[j] <  a[i] */
            else if (a[k] <= a[i]) return k; /* a[j] <  a[k] <= a[i] */
            else return i;                   /* a[j] <  a[i] <  a[k] */
        }
    }

    private static void bound(int[] a, int i, int j, int k){
        int median = median3(a, i, j, k);

        int[] bounds = new int[3];

        bounds[1] = median;

        if(i == median){
            if(a[j] >= a[k]) {
                bounds[2] = j;
                bounds[0] = k;
            }
            else{
                bounds[2] = k;
                bounds[0] = j;
            }
        }
        else if(j == median){
            if(a[i] >= a[k]) {
                bounds[2] = i;
                bounds[0] = k;
            }
            else{
                bounds[2] = k;
                bounds[0] = i;
            }
        }
        else{
            if(a[j] >= a[i]) {
                bounds[2] = j;
                bounds[0] = i;
            }
            else{
                bounds[2] = i;
                bounds[0] = j;
            }
        }

        exch(a, i, bounds[1]);

        if(bounds[2] == i){
            exch(a, k, bounds[1]);
        }
        else
            exch(a, k, bounds[2]);
    }




   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    private static boolean isSorted(int[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (a[i] < a[i-1]) return false;
        return true;
    }

    // This class should not be instantiated.
    private Quick() { }
}

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
