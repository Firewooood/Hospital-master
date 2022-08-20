package com.wxz.client.cmn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: WuXiangZhong
 * @Description: 远程调用
 * @Date: Create in 2022/8/19
 */

@Repository
@FeignClient(value = "service-cmn",path = "/admin/cmn/dict/")
public interface DictFeignClient {

    // 根据dictcode和value查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable("dictCode") String dictCode,
                          @PathVariable("value") String value);

    // 根据value查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable("value") String value);
}
