import java.util.Comparator;

public class Term {

    private String query;
    private long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {

        if (query == null)
            throw new NullPointerException("Query is null");

        if (weight < 0)
            throw new IllegalArgumentException("Weight can not be negative: " + weight);

        this.query = query;
        this.weight = weight;

    }

    // Compares the two terms in lexicographic order by query.
    public static Comparator<Term> byLexicographicOrder() {
        return Comparator.comparing(o -> o.query);
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return Comparator.comparingLong(o -> -o.weight);
    }

    // Compares the two terms in lexicographic order,
    // but using only the first k characters of each query.
    public static Comparator<Term> byPrefixOrder(int k) {

        if (k < 0)
            throw new IllegalArgumentException("k can not be negative: " + k);

        return (o1, o2) -> {
            String o1Substring;
            String o2Substring;
            if (o1.query.length() < k) {
                o1Substring = o1.query;
            } else {
                o1Substring = o1.query.substring(0, k);
            }

            if (o2.query.length() < k) {
                o2Substring = o2.query;
            } else {
                o2Substring = o2.query.substring(0, k);
            }

            return o1Substring.compareTo(o2Substring);
        };
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by whitespace, followed by the query.
    public String toString() {
        return String.format("%12d    %s", this.weight, this.query);
    }
}
