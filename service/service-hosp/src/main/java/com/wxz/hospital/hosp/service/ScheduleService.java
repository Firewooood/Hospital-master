package com.wxz.hospital.hosp.service;

import com.wxz.hospital.model.hosp.Schedule;
import com.wxz.hospital.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: WuXiangZhong
 * @Description:
 * @Date: Create in 2022/8/18
 */
public interface ScheduleService {
    void save(Map<String, Object> paramMap);

    // 删除排班
    void remove(String hoscode, String hosScheduleId);

    // 分页查询排班
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
