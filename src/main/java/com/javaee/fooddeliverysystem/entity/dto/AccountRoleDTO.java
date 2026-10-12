package com.javaee.fooddeliverysystem.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AccountRoleDTO {
    private Integer userId;
    private List<Integer> roleIds = new ArrayList<>();

}