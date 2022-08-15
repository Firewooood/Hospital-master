package com.wxz.hospital.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wxz.hospital.cmn.mapper.DictMapper;
import com.wxz.hospital.model.cmn.Dict;
import com.wxz.hospital.vo.cmn.DictEeVo;
import org.apache.poi.hssf.record.DConRefRecord;
import org.springframework.beans.BeanUtils;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/15
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {
    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper){
        this.dictMapper = dictMapper;
    }

    // 一行行读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        // 调用方法添加数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
