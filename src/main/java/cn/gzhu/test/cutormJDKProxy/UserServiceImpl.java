package cn.gzhu.test.cutormJDKProxy;

public class UserServiceImpl implements UserService {

    @Override
    public Integer findUser(Integer a) {
        System.out.println("**---find user successful");
        return 1;
    }
}
