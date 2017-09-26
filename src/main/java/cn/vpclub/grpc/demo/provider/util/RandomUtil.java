package cn.vpclub.grpc.demo.provider.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomUtil {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 多线程安全的获取一个唯一流水号
     *
     * @return
     */
    public synchronized static String getSerialNumber(String pre) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        DecimalFormat df = new DecimalFormat("000000");
        //获取一个0-999999的数
        int i = (atomicInteger.incrementAndGet() & Integer.MAX_VALUE) % 1000000;
        if (i == 0) {
            i = (atomicInteger.incrementAndGet() & Integer.MAX_VALUE) % 1000000;
        }
        return pre + sdf.format(System.currentTimeMillis()) + df.format(i);
    }

    /**
     * 生成一个10位的随机数 16进制
     *
     * @param length
     * @return
     */
    public static String randomHexString(int length) {
        try {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < length; i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}