package cn.easygd.quest.example;

import cn.easygd.quest.api.annotation.QuestComponent;

/**
 * 示例服务组件
 * 演示如何使用@QuestComponent注解
 *
 * @author VD
 */
@QuestComponent(
        alis = "用户服务",
        bizGroup = "用户管理",
        description = "提供用户相关的业务功能"
)
public class UserService {

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @QuestComponent(
            alis = "获取用户",
            bizGroup = "用户管理",
            description = "根据用户ID获取用户详细信息"
    )
    public String getUserInfo(String userId) {

        return "User info for ID: " + userId;
    }

    /**
     * 非组件方法 - 不会被扫描到
     */
    public void helperMethod() {
        // 这个方法没有@QuestComponent注解，不会被组件扫描器识别
    }
}