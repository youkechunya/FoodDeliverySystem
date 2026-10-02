package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.PersonalInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonalInfomationMapper {
    /// 选中所有个人信息
    List<PersonalInformation> selectAll();
    /// 通过账号 ID 选择
    PersonalInformation selectByAccountId(@Param("accountId") Integer accountId);
    /// 新增个人信息
    int insert(PersonalInformation info);
    /// 更新个人信息
    int update(PersonalInformation info);
    /// 通过 ID 删除个人信息
    int deleteById(@Param("id") Integer id);
}
