package cn.vpclub.grpc.demo.provider.repository;

import cn.vpclub.grpc.demo.provider.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by WangRui on 2017/9/21.
 */
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'id': ?0}")
    User findById(String id);
}
