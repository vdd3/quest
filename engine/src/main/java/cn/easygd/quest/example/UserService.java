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
            description = "根据用户ID获取用户详细信息"
    )
    public String getUserInfo(String userId) {

        return "User info for ID: " + userId;
    }

    /**
     * 创建新用户
     *
     * @param userData 用户数据
     * @return 创建结果
     */
    @QuestComponent(
            alis = "创建用户",
            description = "创建新的用户账户"
    )
    public boolean createUser(String userData) {

        return true;
    }

    /**
     * 更新用户信息
     *
     * @param userId  用户ID
     * @param updates 更新数据
     * @return 更新结果
     */
    @QuestComponent(
            alis = "更新用户",
            description = "更新指定用户的个人信息"
    )
    public boolean updateUser(String userId, String updates) {
        return true;
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @QuestComponent(
            alis = "删除用户",
            description = "删除指定用户账户"
    )
    public boolean deleteUser(String userId) {
        return true;
    }

    /**
     * 非组件方法 - 不会被扫描到
     */
    public void helperMethod() {
        // 这个方法没有@QuestComponent注解，不会被组件扫描器识别
    }
}