public  class  Palindrome {
    public Deque<Character> wordToDeque(String word){
        LinkedListDeque wordDeque=new LinkedListDeque();
        for(int i=0;i<word.length();i++){
            char ch=word.charAt(i);
            wordDeque.addLast(ch);
        }
        return wordDeque;
    }
    public boolean isPalindrome(String word){
        Deque<Character> wordDeque=wordToDeque(word);
        if(wordDeque.size()==0 ||wordDeque.size()==1){
            return true;
        }
        while(wordDeque.size()>1){
            if (wordDeque.removeFirst()!=wordDeque.removeLast()){
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> wordDeque=wordToDeque(word);
        if(wordDeque.size()==0 ||wordDeque.size()==1){
            return true;
        }
        while(wordDeque.size()>1){
            if (!cc.equalChars(wordDeque.removeFirst(),wordDeque.removeLast())){
                return false;
            }
        }
        return true;
    }


}