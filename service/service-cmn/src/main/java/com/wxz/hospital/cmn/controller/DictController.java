package com.wxz.hospital.cmn.controller;

import com.wxz.hospital.cmn.service.DictService;
import com.wxz.hospital.common.result.Result;
import com.wxz.hospital.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/15
 */

@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据id获取查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> childData = dictService.findChildData(id);

        return Result.ok(childData);
    }

    @ApiOperation(value = "导入数据字典")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }

    @ApiOperation(value = "导出数据字典")
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response){
        dictService.exportDictData(response);
    }

    // 根据dictcode和value查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    // 根据value查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("",value);
        return dictName;
    }

    //根据dictCode获取下级节点
    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}
