# 完美校园自动打卡WEB版



## 食用方法:

#### Python端

​	1.cd到 ../17Wanxiao/django 目录下

​	2.执行 python manage.py runserver 0.0.0.0:4397 启动django服务 (重要,端口可以自定义,修改后请同步修改java\top\dongxiaohao\client下的所有Get端口)



#### Java端

​	1.在java\src\main\resources目录下找到mybatis-config.xml,配置数据库账号密码

```xml
	<property name="url" 
  value="jdbc:mysql://127.0.0.1:3306/${YourDbName}?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai"/>
		<!-- 数据库用户名 -->
		<property name="username" value=""/>
		<!-- 数据库密码 -->
        <property name="password" value=""/>
```



2.新建数据库,导入17wanxiao.sql



3.配置Java项目启动即可

------

此项目根据[ReaJason](https://github.com/ReaJason/17wanxiaoCheckin)大佬项目夏季霸改的,可以实现基本的打卡操作,需要部署在服务器上运行


​	

## 程序运行流程:
搭建本地Python打卡服务 -> Java请求我们Python写好的接口 -> Python完成打卡

请添加定时任务http://localhost/user?opr=clockIn 来给数据库所有用户打卡



