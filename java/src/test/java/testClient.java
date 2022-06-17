import com.alibaba.fastjson.JSON;
import com.dtflys.forest.Forest;
import com.dtflys.forest.config.ForestConfiguration;
import org.apache.ibatis.cursor.Cursor;
import org.junit.Test;
import top.dongxiaohao.client.MyCilent;
import top.dongxiaohao.dao.UserDao;
import top.dongxiaohao.entity.UserInfo;
import top.dongxiaohao.service.UserService;
import top.dongxiaohao.util.MybatisUtil;
import top.dongxiaohao.util.RandomDev;
import top.dongxiaohao.util.ServiceProxyFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class testClient {

    @Test
    public void testC(){
        ForestConfiguration configuration = ForestConfiguration.configuration();
        configuration.setTimeout(10000);
        configuration.setReadTimeout(10000);
        configuration.setRetryCount(1);
        MyCilent client = Forest.client(MyCilent.class);
        String phone = "******";
        String pwd = "******";
        String dev = "******";
        String email = "******";
        String temp = "1";
        String res = client.testCard(phone, pwd, dev, temp, email);
        String resPar = res.substring(res.indexOf('[')+1,res.lastIndexOf(']'));
        System.out.println(res);
        System.out.println(resPar);
        if (JSON.parseObject(resPar).get("res") == null){
            System.out.println(JSON.parseObject(resPar).get("errmsg"));
        }else {
            System.out.println(JSON.parseObject(resPar).get("res"));
        }
        //System.out.println(JSON.parseObject(resPar).get("res"));
        //System.out.println(JSON.parseObject(resPar).get("status"));
    }

    @Test
    public void addTest() throws IOException {
        UserService proxy = ServiceProxyFactory.getProxy(UserService.class);
        proxy.clockAllUser();

        //UserDao mapper = MybatisUtil.getConn().getMapper(UserDao.class);
        //Cursor<UserInfo> allUser = mapper.findAllUser();
        //System.out.println(allUser.isConsumed());
        //allUser.forEach(user ->{
        //    try {
        //        Thread.sleep(1000);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    System.out.println(user.getPhone());
        //});
        //allUser.close();
        //System.out.println("####################################################");
        //ArrayList<UserInfo> userInfos = mapper.finallUsernormal();
        //userInfos.forEach(user -> {
        //    System.out.println(user);
        //});
        //UserInfo info = new UserInfo(null,"1","12","1","1","1");
        //proxy.saveUser(info);
    }

    @Test
    public void spendTime() throws UnsupportedEncodingException {
        Integer m = 0;
        long l = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < i; j++) {
                m++;
                System.out.println("第"+ m +"次");
            }
        }
        long s = System.currentTimeMillis();
        System.out.println("耗时" + (s - l) + "ms");
        System.out.println("###############################");
        long ll = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < i; j++) {
                m++;
            }
        }
        long ss = System.currentTimeMillis();
        System.out.println("耗时" + (ss - ll) + "ms");
    }
}
