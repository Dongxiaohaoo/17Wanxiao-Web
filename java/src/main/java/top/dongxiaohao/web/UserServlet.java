package top.dongxiaohao.web;

import com.alibaba.fastjson.JSON;
import com.dtflys.forest.Forest;
import com.dtflys.forest.config.ForestConfiguration;
import top.dongxiaohao.client.MyCilent;
import top.dongxiaohao.entity.UserInfo;
import top.dongxiaohao.service.UserService;
import top.dongxiaohao.util.ServiceProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService us = ServiceProxyFactory.getProxy(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String opr = req.getParameter("opr");
        System.out.println(opr);
        if (opr.equals("send_msg")){
            send_msg(req,resp);
        }else if (opr.equals("slogin")){
            slogin(req,resp);
        }else if(opr.equals("test")){
            test(req,resp);
        }else if(opr.equals("submit")){
            submit(req,resp);
        }else if(opr.equals("clockIn")){
            clockIn(req,resp);
        }
    }

    //访问此地址遍历数据库打卡
    private void clockIn(HttpServletRequest req, HttpServletResponse resp) {
        us.clockAllUser();
    }

    private void submit(HttpServletRequest req, HttpServletResponse resp) {
        String phone = req.getParameter("phone");
        String pwd = req.getParameter("pwd");
        String dev = req.getParameter("dev");
        String email = req.getParameter("email");
        String temp = req.getParameter("temp");
        UserInfo info = new UserInfo();
        info.setPhone(phone);
        info.setPwd(pwd);
        info.setDev(dev);
        info.setEmail(email);
        info.setTemp(temp);
        us.saveUser(info);
    }

    //测试打卡
    private void test(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phone = req.getParameter("phone");
        String pwd = req.getParameter("pwd");
        String dev = req.getParameter("dev");
        String email = req.getParameter("email");
        String temp = req.getParameter("temp");
        ForestConfiguration configuration = ForestConfiguration.configuration();
        configuration.setTimeout(10000);
        configuration.setReadTimeout(10000);
        configuration.setRetryCount(1);
        MyCilent client = Forest.client(MyCilent.class);
        String res = client.testCard(phone, pwd, dev, temp, email);
        String resPar = res.substring(res.indexOf('[')+1,res.lastIndexOf(']'));
        PrintWriter writer = resp.getWriter();
        if (JSON.parseObject(resPar).get("res") == null){
            writer.print(JSON.parseObject(resPar));
        }else {
            writer.print(JSON.parseObject(resPar).get("res"));
        }
    }

    private void slogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phone = req.getParameter("phone");
        String dev = req.getParameter("dev");
        String sms = req.getParameter("sms");
        MyCilent client = Forest.client(MyCilent.class);
        String slogin = client.slogin(phone, sms, dev);
        resp.getWriter().print(JSON.parse(slogin));
    }

    private void send_msg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phone = req.getParameter("phone");
        String dev = req.getParameter("dev");
        MyCilent client = Forest.client(MyCilent.class);
        String send = client.send(phone, dev);
        PrintWriter writer = resp.getWriter();
        writer.print(JSON.parse(send));
    }
}
