/**
 * Created by kber0001 on 30/06/2015.
 */
public class StringLengthComparator implements java.util.Comparator<String> {
    @Override
    public int compare(String o1, String o2) {

        if (o1 == null) {
            return o2 == null ? 0 : -1;
        } else if (o2 == null) {
            return 1;
        } else {
            int o1Length = o1.length();
            int o2Length = o2.length();
            return o1Length - o2Length;
        }
    }
}
