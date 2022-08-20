package com.wxz.hospital.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxz.hospital.cmn.listener.DictListener;
import com.wxz.hospital.cmn.mapper.DictMapper;
import com.wxz.hospital.cmn.service.DictService;
import com.wxz.hospital.model.cmn.Dict;
import com.wxz.hospital.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/15
 */

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>implements DictService {

    // 1. 根据数据id查询子数据列表
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")    // 将当前查询的dictList 加入redis缓存中
    public List<Dict> findChildData(Long id) {

        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);         // select * from XXXX where parent id == id

        List<Dict> dictList = baseMapper.selectList(wrapper);
        // 向list集合每个dict 对象中设置hasChildren
        dictList.forEach(dict -> {dict.setHasChildren(this.isChildren(dict.getId()));});
        return dictList;
    }

    // 2. 导出数据字典接口
    @Override
    @CacheEvict(value = "dict", allEntries=true)        // 导入数据,会导致数据更新, 使用此注解清空缓存
    public void exportDictData(HttpServletResponse response) {
        // 设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition","attachment;filename=" + fileName + ".xlsx");
        // 查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        // Dict -- > DictVo
        List<DictEeVo> dictEeVoList = new ArrayList<>();
        for(Dict dict:dictList){
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        // 调用方法进行写操作
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 3. 导入数据字典
    @Override
    @CacheEvict(value = "dict", allEntries=true)        // 导入数据,会导致数据更新, 使用此注解清空缓存
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 4. 根据dictcode和value查询
    @Override
    public String getDictName(String dictCode, String value) {
            // 如果dictCode 为空,直接根据value查询
            if(StringUtils.isEmpty(dictCode)){
                QueryWrapper<Dict> wrapper = new QueryWrapper<>();
                wrapper.eq("value",value);
                Dict dict = baseMapper.selectOne(wrapper);
                return dict.getName();
            }else{//如果dictCode不为空，根据dictCode和value查询
                //根据dictcode查询dict对象，得到dict的id值
                Dict codeDict = this.getDictByDictCode(dictCode);
                Long parent_id = codeDict.getId();
                //根据parent_id和value进行查询
                Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                        .eq("parent_id", parent_id)
                        .eq("value", value));
                return finalDict.getName();
            }
        }


    // 5. 根据dictCode获取下级节点
    @Override
    public List<Dict> findByDictCode(String dictCode){
        //根据dictcode获取对应id
        Dict dict = this.getDictByDictCode(dictCode);
        //根据id获取子节点
        List<Dict> chlidData = this.findChildData(dict.getId());
        return chlidData;
    }

    // 判断当前id 下是否有子节点
    private boolean isChildren(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }

}
