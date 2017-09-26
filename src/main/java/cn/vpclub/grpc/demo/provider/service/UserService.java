package cn.vpclub.grpc.demo.provider.service;

import cn.vpclub.grpc.demo.provider.domain.User;
import cn.vpclub.grpc.demo.provider.domain.response.BaseResponse;

/**
 * Created by WangRui on 2017/9/21.
 */
public interface UserService {

    /**
     * 查询用户
     *
     * @param user
     * @return
     */
    User findUserById(User user);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    BaseResponse insertUser(User user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    BaseResponse updateUser(User user);
}
