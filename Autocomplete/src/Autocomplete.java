import java.util.Arrays;

public class Autocomplete {

    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    // Complexity: O(N log N), where N is the number of terms
    public Autocomplete(Term[] terms){

        if(terms == null || Arrays.asList(terms).contains(null))
            throw new NullPointerException("Argument is null");

        Arrays.sort(terms, Term.byLexicographicOrder());
        this.terms = terms;
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Complexity: O(log N + M log M), where M is the number of matching terms
    public Term[] allMatches(String prefix){

        if(prefix == null)
            throw new NullPointerException("Prefix is null");

        int first = RangeBinarySearch.
                firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

        if(first == -1)
            return new Term[0];

        int last = RangeBinarySearch.
                lastIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

        int numberOfMatches = last - first + 1;
        Term[] matches = new Term[numberOfMatches];

        for(int i = 0; i < numberOfMatches; i++){
            matches[i] = terms[first + i];
        }

        Arrays.sort(matches, Term.byReverseWeightOrder());

        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    // Complexity: O(log N)
    public int numberOfMatches(String prefix){

        if(prefix == null)
            throw new NullPointerException();

        int first = RangeBinarySearch.
                firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

        if(first == -1)
            return 0;

        int last = RangeBinarySearch.
                lastIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));



        return last - first + 1;
    }
}