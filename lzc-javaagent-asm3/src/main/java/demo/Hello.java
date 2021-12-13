package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello {
    public static final String FLAG = "我是alioo";

    public Hello() {
    }

    public void display() {

        for (int var1 = 0; var1 < 6; ++var1) {
            System.out.println(">>>>>>>>>>我是alioo");
        }

    }

    public List<String> testList() {
        ArrayList list = new ArrayList();
        list.add("lzc");
        list.add("alioo");
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
