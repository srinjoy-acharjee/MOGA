
import java.util.ArrayList;
import java.util.Random;


public class Unique_random 
{
    int d[];
    
    Unique_random(int size)
    {
        ArrayList<Integer> list = new ArrayList<>(size);
        for(int i = 1; i <= size; i++) 
        {
            list.add(i);
        }
        d=new int[size];
        Random rand = new Random();
        while(list.size() > 0 ) 
        {
            int index = rand.nextInt(list.size());
            d[size-list.size()]=  list.remove(index)-1;//remove the previous index number
        }
        
    }
    
}
