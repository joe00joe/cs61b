
/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */

    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        if(asciis.length<=1){
            return asciis;
        }
        int max=0;
        for(String s:asciis){
            max = max > s.length() ? max : s.length();
        }
        String[] apended=appendZero(asciis,max);
        for(int idx=max-1;idx>=0;idx--){
            sortHelperLSD(apended,idx);
        }
        String[] reduced=reduceZero(apended);
        return reduced;
    }

    private  static String [] appendZero(String[] asciis,int len){
        int index = 0;
        String[] res = new String[asciis.length];
        for (String s : asciis) {
            StringBuffer sbu = new StringBuffer();
            sbu.append(s);
            if (s.length() < len) {
                for (int i = 0; i < len - s.length(); i++) {
                    sbu.append((char) 0);
                }
            }
            res[index++] = sbu.toString();
        }
        return res;
    }

    private  static  String[] reduceZero(String[] asciis){
        int index = 0;
        String[] res = new String[asciis.length];
        for (String s : asciis) {
            StringBuffer sbu = new StringBuffer();
            sbu.append(s);
            for (int i = sbu.length() - 1; i >= 0 && sbu.charAt(i) == (char) 0; i--) {
                sbu.deleteCharAt(i);
            }
            res[index++] = sbu.toString();
        }
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int counts[]=new int[256];
        for(String s :asciis){
            counts[(int)s.charAt(index)]++;
        }
        int starts[] =new int[256];
        int pos=0;
        for(int i=0;i<counts.length;i++) {
            starts[i]=pos;
            pos+=counts[i];
        }

        String [] sorted= new String[asciis.length];
        for(String s:asciis){
            int place=starts[(int)s.charAt(index)];
            sorted[place]=s;
            starts[(int)s.charAt(index)]++;
        }
        for(int i=0;i<sorted.length;i++){
            asciis[i]=sorted[i];
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
    public static void main(String args[]) {
        String[] strs = {"abcc", "abcd", "ggg", "vcx", "crx", "pyf"};
        String[] sorted = sort(strs);
        for (String s : sorted) {
            System.out.print(s + " ");
        }
    }
}
