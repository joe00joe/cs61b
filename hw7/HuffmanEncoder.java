import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        int[] counts =new int[256];
        Map<Character, Integer> freqTable=new HashMap<>();
        for(int i=0;i<inputSymbols.length;i++){
            counts[(int)inputSymbols[i]]++;
        }
        for(int i=0;i<counts.length;i++){
            if(counts[i]>0){
                freqTable.put((char)i,counts[i]);
            }
        }
        return freqTable;
    }
    public static void main(String[] args) {
        char[] inputChars = FileUtils.readFile(args[0]);
        String hufFile = args[0] + ".huf";
        Map<Character, Integer> freqTable = buildFrequencyTable(inputChars);
        BinaryTrie trie = new BinaryTrie(freqTable);
        ObjectWriter objectWriter = new ObjectWriter(hufFile);
        objectWriter.writeObject(trie);
        Map<Character, BitSequence> lookupTable = trie.buildLookupTable();
        List<BitSequence> bitSequences = new ArrayList<>();
        for (char c : inputChars) {
            bitSequences.add(lookupTable.get(c));
        }
        BitSequence hugeBitSquence = BitSequence.assemble(bitSequences);
        objectWriter.writeObject(hugeBitSquence);

    }
}
