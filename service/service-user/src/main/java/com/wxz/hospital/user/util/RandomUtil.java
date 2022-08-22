package com.wxz.hospital.user.util;

import java.util.Random;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/22
 */
public class RandomUtil {
    public static String getSixBitRandom(){
        String code = "";
        Random random = new Random();
        for(int i = 0; i < 6; i++){
            int r = random.nextInt(10);
            code = code+r;
        }
        return code;

    }
}
