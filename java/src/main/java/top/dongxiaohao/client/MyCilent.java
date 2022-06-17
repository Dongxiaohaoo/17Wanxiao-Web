package top.dongxiaohao.client;

import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Post;

public interface MyCilent {

    /**
     * 默认body格式为 application/x-www-form-urlencoded，即以表单形式序列化数据
     */
    @Get(
            url = "http://127.0.0.1:4397/user/plogin?phone={0}&pwd={1}&dev={2}",
            headers = {"Accept:text/plain"}
    )
    String plogin(String phone,String pwd,String dev);

    @Get(
            url = "http://127.0.0.1:4397/user/slogin?phone={0}&sms={1}&dev={2}",
            headers = {"Accept:text/plain"}
    )
    String slogin(String phone,String sms,String dev);


    @Get(
            url = "http://127.0.0.1:4397/user/send?phone={0}&dev={1}",
            headers = {"Accept:text/plain"}
    )
    String send(String phone, String dev);

    @Get(
            url = "http://127.0.0.1:4397/user/auto_card?phone={0}&password={1}&device_id={2}&template={3}&email={4}",
            headers = {"Accept:text/plain"}
    )
    String testCard(String phone,String password,String dev,String temp,String email);
}
