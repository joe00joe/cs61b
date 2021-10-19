import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class BinaryTrie implements Serializable {
    private  Node root;
    private  class  Node implements Comparable<Node>, Serializable {
        private  final char ch;
        private  final int freq;
        private  Node left;
        private  Node right;
        Node(char ch,int freq,Node left,Node right){
            this.ch=ch;
            this.freq=freq;
            this.left=left;
            this.right=right;
        }
        private  boolean isLeaf(){
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        public int compareTo(Node that){
              return this.freq- that.freq;
        }

    }
    public BinaryTrie(Map<Character, Integer> frequencyTable){
        MinPQ<Node> pq=new MinPQ<Node>();
        for(Map.Entry<Character, Integer> entry :frequencyTable.entrySet()){
            pq.insert(new Node(entry.getKey(),entry.getValue(),null,null));
        }
        while(pq.size()>1){
            Node left=pq.delMin();
            Node right=pq.delMin();
            pq.insert(new Node('\0',left.freq+right.freq,left,right));
        }
        root=pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence){
           Node p=root;
           BitSequence bs=new BitSequence();
           for(int i=0;i<querySequence.length();i++) {
               int b = querySequence.bitAt(i);
               if(b==0){ p=p.left;}
               if(b==1){ p=p.right;}
               if (p.isLeaf()) {
                   bs=querySequence.firstNBits(i+1);
                   break;
               }

           }
           return new Match(bs,p.ch);

    }
    public Map<Character, BitSequence> buildLookupTable(){
        Map<Character, BitSequence> lookupTable =new HashMap<>();
        buildLookupTableHelper(lookupTable,root,"");
        return lookupTable;

    }
    private void  buildLookupTableHelper( Map<Character, BitSequence> st,Node x,String s){
        if(!x.isLeaf()){
            buildLookupTableHelper(st,x.left,s+'0');
            buildLookupTableHelper(st,x.right,s+'1');
        }
        else{
            st.put(x.ch,new BitSequence(s));
        }
    }



}
