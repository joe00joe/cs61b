public class  LinkedListDeque<T> {
    public class  TypeNode{
        public  T item;
        public  TypeNode pre;
        public  TypeNode next;
        public  TypeNode(T i,TypeNode p,TypeNode n){
            item=i;
            pre=p;
            next=n;
        }
    }
    private int size;
    private TypeNode fristSent;
    private TypeNode lastSent;

    public  LinkedListDeque(){
        fristSent=new TypeNode(null,null,null);
        lastSent=new TypeNode(null,fristSent,null);
        fristSent.next=lastSent;
        size=0;
    }

    public void addFirst(T item){
        TypeNode addNode=new TypeNode(item,fristSent,fristSent.next);
        fristSent.next.pre=addNode;
        fristSent.next=addNode;
        size++;

    }

    public void addLast(T item){
        TypeNode addNode =new TypeNode(item,lastSent.pre,lastSent);
        lastSent.pre.next=addNode;
        lastSent.pre=addNode;
        size++;
    }

    public boolean isEmpty(){
        if(size==0) {
            return true;
        }
        else{
            return false;
        }

    }
    public int size(){
        return size;
    }
    public void printDeque(){
        TypeNode p=fristSent.next;
        while(p!=lastSent){
            System.out.print(p.item + " ");
            p=p.next;
        }
        System.out.println();
    }
    public T removeLast(){
        if(size==0){
            return null;
        }
        TypeNode removeNode=lastSent.pre;
        lastSent.pre=removeNode.pre;
        removeNode.pre.next=lastSent;
        size--;
        return  removeNode.item;
    }
    public T removeFirst(){
        if(size==0){
            return null;
        }
        TypeNode removeNode=fristSent.next;
        fristSent.next=removeNode.next;
        removeNode.next.pre=fristSent;
        size--;
        return removeNode.item;
    }
    public T get(int index){
        TypeNode p=fristSent.next;
        while(p!=lastSent){
            if (index==0){
                return p.item;
            }
            index--;
            p=p.next;
        }
        return null;
    }

    private T getRHelp(TypeNode node,int index){
        if (node==lastSent){
            return null;
        }
        if(index==0){
            return node.item;
        }
        else{
            return getRHelp(node.next,index-1);
        }
    }
    public T getRecursive(int index){
        return getRHelp(fristSent.next,index);
    }
}