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
        for (int var1 = 0; var1 < 6; ++var1) {
            System.out.println(">>>>>>>>>>我是常量");
        }

    }

    public List<String> testList() {
        ArrayList var1 = new ArrayList();
        var1.add("Tome");
        var1.add("Jack");
        var1.add("Lily");
        System.out.println(">>>>>>>>>>testList > list.size = " + var1.size());
        return var1;
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
