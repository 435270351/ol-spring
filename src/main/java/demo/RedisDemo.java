package demo;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-23
 * @since (版本)
 */
public class RedisDemo implements Comparable{

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("localhost");
//        System.out.println("连接成功");
//        // 查看服务是否运行
//        System.out.println("服务正在运行: " + jedis.ping());
//
//        Set<String> set = jedis.keys("*");
//        System.out.println(set);
//
//        System.out.println(jedis.get("name"));
//
//        System.out.println(jedis.lrange("l_m",0,-1));
//
//        System.out.println(jedis.setnx("names","df"));
//
//        System.out.println(jedis.ttl("name"));

        List<String> list = new ArrayList<>();

        list.add("1");
        list.add("3");
        list.add("2");

        Collections.sort(list);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });


        System.out.println(list);

        System.out.println("2".compareTo("1"));
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
