package guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

/**
 * Created by liekkas on 15/6/5.
 */
public class MapTest {

    @Test
    public void testBiMap(){
        BiMap map = HashBiMap.create();
        map.put(1,"one");
        map.put(2,"two");
        map.put(3,"three");

        System.out.println(map);
        System.out.println(map.inverse());
        System.out.println(map);
    }

}
