package cn.vpclub.grpc.demo.provider.rpc;


import cn.vpclub.grpc.demo.provider.domain.User;
import cn.vpclub.grpc.demo.provider.service.UserService;
import cn.vpclub.grpc.demo.provider.domain.response.BaseResponse;
import cn.vpclub.grpc.demo.user.consumer.api.AdapterUserProto;
import cn.vpclub.grpc.demo.user.consumer.api.AdapterUserServiceGrpc;
import cn.vpclub.spring.boot.grpc.annotations.GRpcService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.fromGRpcMessage;
import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.toGRpcMessage;


/**
 * Created by WangRui on 2017/9/21.
 */
@Slf4j
@GRpcService
public class UserRpc extends AdapterUserServiceGrpc.AdapterUserServiceImplBase {

    private UserService userService;

    @Autowired
    public UserRpc(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void findUser(AdapterUserProto.User request, StreamObserver<AdapterUserProto.User> responseObserver) {
        User user = (User) fromGRpcMessage(request, User.class);
        try {
            // 调用真正的数据访问服务
            User userDTO = userService.findUserById(user);
            AdapterUserProto.User.Builder userBuild = AdapterUserProto.User.newBuilder();
            // 使用工具方法自动转换，减少代码量
            AdapterUserProto.User message = (AdapterUserProto.User) toGRpcMessage(userDTO, userBuild);
            // 以下两句代码是必须的
            responseObserver.onNext(message);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("findUser error: {}", e);
        }
    }

    /**
     * 创建
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void insertUser(AdapterUserProto.User request, StreamObserver<AdapterUserProto.UserResponse> responseObserver) {
        User user = (User) fromGRpcMessage(request, User.class);
        try {
            BaseResponse response = userService.insertUser(user);
            AdapterUserProto.UserResponse grpcMessage = (AdapterUserProto.UserResponse) toGRpcMessage(response, AdapterUserProto.UserResponse.newBuilder());
            responseObserver.onNext(grpcMessage);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("insertUser error: {}", e);
        }
    }

    /**
     * 更新
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void updateUser(AdapterUserProto.User request, StreamObserver<AdapterUserProto.UserResponse> responseObserver) {
        User user = (User) fromGRpcMessage(request, User.class);
        try {
            BaseResponse response = userService.updateUser(user);
            AdapterUserProto.UserResponse grpcMessage = (AdapterUserProto.UserResponse) toGRpcMessage(response, AdapterUserProto.UserResponse.newBuilder());
            responseObserver.onNext(grpcMessage);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("updateUser error: {}", e);
        }
    }
}
