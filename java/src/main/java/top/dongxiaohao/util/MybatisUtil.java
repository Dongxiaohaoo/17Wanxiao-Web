package top.dongxiaohao.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class MybatisUtil {
    private static SqlSessionFactory ssf = null;
    private static ThreadLocal<SqlSession> tl = new ThreadLocal<SqlSession>();

    static {
        try {
            ssf = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("工厂创建失败...");
        }
    }

    public static SqlSession getConn() {
        SqlSession sqlSession = tl.get();
        if (sqlSession == null) {
            sqlSession = ssf.openSession();
            tl.set(sqlSession);
        }
        return sqlSession;
    }

    public static void closeConn(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
            tl.remove();
        }
    }

    public static void main(String[] args) {
        SqlSession conn = getConn();
        System.out.println(conn);
    }
}
