import java.util.BitSet;

public class ArrayDeque<T> {

    private int size;
    private int nextFrist;
    private int nextLast;
    private T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFrist = 0;
        nextLast = 1;
        size = 0;

    }

    private int minusOne(int index) {
        return Math.floorMod(index - 1, items.length);
    }

    private int plusOne(int index) {
        return Math.floorMod(index + 1, items.length);
    }

    private void resize(){
       if(size==items.length){
           expend();
       }
       if(items.length>=16 && size<(items.length/4)){
           reduce();
       }
    }

    private void expend(){
        resizeHelp(items.length*2);
    }
    private void reduce(){
        resizeHelp(items.length/2);
    }

    private void resizeHelp(int cap){
        T[] temp = items;
        int begin = plusOne(nextFrist);
        int end = minusOne(nextLast);
        items = (T[]) new Object[cap];
        nextFrist=0;
        nextLast=1;
        for(int i=begin;i!=end;i=Math.floorMod(i+ 1, temp.length)){
            items[nextLast]=temp[i];
            nextLast=plusOne(nextLast);
        }
        items[nextLast]=temp[end];
        nextLast=plusOne(nextLast);
    }

    public void addFirst(T item) {
        resize();
        items[nextFrist] = item;
        nextFrist = minusOne(nextFrist);
        size++;
    }


    public void addLast(T item) {
        resize();
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }

    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = plusOne(nextFrist); i != nextLast; i = plusOne(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removeItem = items[plusOne(nextFrist)];
        items[plusOne(nextFrist)] = null;
        nextFrist = plusOne(nextFrist);
        size--;
        resize();
        return removeItem;


    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removeItem = items[minusOne(nextLast)];
        items[minusOne(nextLast)] = null;
        nextLast = minusOne(nextLast);
        size--;
        resize();
        return removeItem;
    }


    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        index = Math.floorMod(plusOne(nextFrist) + index, items.length);
        return items[index];

    }
}



