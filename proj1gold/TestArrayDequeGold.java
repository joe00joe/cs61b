import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
       @Test
       public  void TestAddRemove(){
           StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
           ArrayDequeSolution<Integer> ads= new ArrayDequeSolution<>();
           String failMessage=new String();;
           for (int i = 0; i < 100; i += 1) {
               double numberBetweenZeroAndOne = StdRandom.uniform();
               if (numberBetweenZeroAndOne > 0.75) {
                   sad.addFirst(i);
                   ads.addFirst(i);
                   failMessage=failMessage+"addFirst(" + i + ")\n";

               }
               else if (numberBetweenZeroAndOne > 0.5){
                   sad.addLast(i);
                   ads.addLast(i);
                   failMessage=failMessage+"addLast(" + i + ")\n";
               }
               else if (numberBetweenZeroAndOne > 0.25){
                   if(!sad.isEmpty() && !ads.isEmpty()) {
                       Integer a = sad.removeFirst();
                       Integer b = ads.removeFirst();
                       failMessage=failMessage+"removeFirst()\n";
                       assertEquals(failMessage,b,a);
                   }
               }
               else{
                   if (!sad.isEmpty() && !ads.isEmpty()){
                       Integer a = sad.removeLast();
                       Integer b = ads.removeLast();
                       failMessage=failMessage+"removeLast()\n";
                       assertEquals(failMessage,b,a);
                   }
               }
           }
       }
}