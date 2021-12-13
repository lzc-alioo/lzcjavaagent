package socket;

import com.example.lzcjavaagent2.model.MethodNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 悟心
 * @time: 2021/11/22 23:51
 * @description:
 */
public class TraceBuffer {
    public static List<MethodNode> list = new ArrayList<>();

    private static int size = 100;

    public static void add(MethodNode node){
        if(list.size()>size){
            list.remove(0);
            list.add(node);
        }
    }

}
