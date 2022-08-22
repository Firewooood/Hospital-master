package com.wxz.hospital.user.service;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/22
 */

public interface MsmService {
    boolean send(String email, String code);
}
