package com.wxz.hospital.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxz.hospital.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/15
 */
public interface DictService extends IService<Dict> {

    // 1. 根据数据id查询子数据列表
    List<Dict> findChildData(Long id);

    // 2. 导出数据字典接口
    void exportDictData(HttpServletResponse response);

    // 3. 导入数据字典
    void importDictData(MultipartFile file);

    // 4. 根据dictcode和value查询
    String getDictName(String dictCode, String value);

    // 5. 根据dictCode获取下级节点
    List<Dict> findByDictCode(String dictCode);

}
