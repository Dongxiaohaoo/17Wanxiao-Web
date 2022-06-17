package top.dongxiaohao.service;

import com.dtflys.forest.Forest;
import com.dtflys.forest.config.ForestConfiguration;
import org.apache.ibatis.cursor.Cursor;
import top.dongxiaohao.client.MyCilent;
import top.dongxiaohao.dao.UserDao;
import top.dongxiaohao.entity.UserInfo;
import top.dongxiaohao.util.MybatisUtil;
import top.dongxiaohao.util.ServiceProxyFactory;

public class UserService {
    public void saveUser(UserInfo info) {
        UserDao mapper = MybatisUtil.getConn().getMapper(UserDao.class);
        //查询有没有这个用户,有就修改,没有就添加
        UserInfo queryByPhone = mapper.queryUserByPhone(info.getPhone());
        if (queryByPhone != null){
            queryByPhone.setEmail(info.getEmail());
            queryByPhone.setTemp(info.getTemp());
            queryByPhone.setDev(info.getDev());
            queryByPhone.setPwd(info.getPwd());
            mapper.updateUser(queryByPhone);
            return;
        }
        mapper.addUser(info);
    }

    //给所有人签到
    public void clockAllUser() {
        UserService proxy = ServiceProxyFactory.getProxy(UserService.class);
        UserDao mapper = MybatisUtil.getConn().getMapper(UserDao.class);
        Cursor<UserInfo> allUser = mapper.findAllUser();
        System.out.println(allUser.isConsumed());
        allUser.forEach(user ->{
            String phone = user.getPhone();
            String pwd = user.getPwd();
            String dev = user.getDev();
            String email = user.getEmail();
            String temp = user.getTemp();
            //System.out.println(user.getPhone());
            ForestConfiguration configuration = ForestConfiguration.configuration();
            configuration.setTimeout(10000);
            configuration.setReadTimeout(10000);
            configuration.setRetryCount(1);
            MyCilent client = Forest.client(MyCilent.class);
            String res = client.testCard(phone, pwd, dev, temp, email);
            try {
	//此处休眠五秒避免发件频繁
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
