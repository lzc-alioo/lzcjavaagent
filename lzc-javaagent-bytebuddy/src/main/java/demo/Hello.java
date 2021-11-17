package demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello {
    public static final String FLAG = "我是常量";

    public Hello() {
    }

    public void display() {
//        long var2 = System.nanoTime();

        for (int var1 = 0; var1 < 6; ++var1) {
            System.out.println(">>>>>>>>>>我是常量");
        }

//        MethodTag methodTag = new MethodTag("demo.Hello","display");
//        ProfilingAspect.point(var2,methodTag,null,null);
    }

    public List<String> testList() {
        ArrayList list = new ArrayList();
        list.add("Tome2");
        list.add("Jack");
        list.add("Lily");
        printList(list);
        System.out.println(">>>>>>>>>>testList > list.size = " + list.size());
        return list;
    }

    private void printList(List list) {
        System.out.println(">>>>>>>>>>print > list= " + list);
    }

    public Map<String, String> testMapList(List<String> list) {
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            map.put(str, str + str);
        }

        return map;
    }
}
