package cn.vpclub.grpc.demo.provider.service.Impl;


import cn.vpclub.grpc.demo.provider.domain.User;
import cn.vpclub.grpc.demo.provider.enums.ReturnCodeEnum;
import cn.vpclub.grpc.demo.provider.repository.UserRepository;
import cn.vpclub.grpc.demo.provider.service.UserService;
import cn.vpclub.grpc.demo.provider.domain.response.BaseResponse;
import cn.vpclub.grpc.demo.provider.util.RandomUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by WangRui on 2017/9/21.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询用户
     *
     * @param user
     * @return
     */
    @Override
    public User findUserById(User user) {
        try {
            if (null == user || StringUtil.isEmpty(user.getId())) {
                return null;
            }
            User returnUser = userRepository.findById(user.getId());
            return returnUser;
        } catch (Exception e) {
            log.error("findUserById error: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 插入
     *
     * @param user
     * @return
     */
    @Override
    public BaseResponse insertUser(User user) {
        BaseResponse baseResponse;
        try {
            if (StringUtil.isEmpty(user.getUsername())) {
                return ReturnCodeEnum.CODE_1011.getBaseResponse("姓名");
            }
            //设置主键ID
            user.setId(RandomUtil.getSerialNumber("vpclub"));
            long timestamp = new Date().getTime();
            user.setCreateDate(timestamp);
            user.setUpdateDate(timestamp);
            User returnUser = userRepository.save(user);
            if (null != returnUser) {
                baseResponse = new BaseResponse();
                baseResponse.setResult(user);
            } else {
                baseResponse = ReturnCodeEnum.CODE_1005.getBaseResponse();
            }
        } catch (Exception e) {
            log.error("insertUser error: " + e.getMessage(), e);
            baseResponse = ReturnCodeEnum.CODE_1004.getBaseResponse();
        }
        return baseResponse;
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @Override
    public BaseResponse updateUser(User user) {
        try {
            if (StringUtil.isEmpty(user.getId())) {
                return ReturnCodeEnum.CODE_1011.getBaseResponse("用户ID");
            }
            User userExist = userRepository.findById(user.getId());
            if (null == userExist) {
                return ReturnCodeEnum.CODE_1002.getBaseResponse("用户");
            }
            //记录存在则更新
            userExist.setUpdateDate(null == user.getUpdateDate() ? new Date().getTime() : user.getUpdateDate());
            userExist.setUsername(StringUtil.isEmpty(user.getUsername()) ? userExist.getUsername() : user.getUsername());
            userExist.setAge(null == user.getAge() ? userExist.getAge() : user.getAge());
            userExist.setDelFlag(null == user.getDelFlag() ? userExist.getDelFlag() : user.getDelFlag());
            User returnUser = userRepository.save(userExist);
            if (null == returnUser) {
                return ReturnCodeEnum.CODE_1005.getBaseResponse();
            }
            return new BaseResponse(returnUser);
        } catch (Exception e) {
            log.error("updateUser error: {}" + e.getMessage(), e);
            return ReturnCodeEnum.CODE_1004.getBaseResponse();
        }
    }
}
