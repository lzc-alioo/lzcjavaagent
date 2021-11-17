package objpool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author: 悟心
 * @time: 2021/11/9 14:17
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        StudentFactory studentFactory = new StudentFactory();
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(5);
        poolConfig.setLifo(false);

        CommonObjectPool pool = new CommonObjectPool(studentFactory, poolConfig);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("pool:::"+pool);
            }
        },1000);


        Student student = null;
        try {
            for (int i=0;i<10;i++) {
                student = pool.borrowObject();
                System.out.println(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(student != null) {
                pool.returnObject(student);
            }
        }
    }
}
