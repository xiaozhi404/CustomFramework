package cn.gzhu.test.CustomDbPool;

/**
 * 描述：连接池工厂，通过静态内部类方法实现单例模式
 */
public class MyPoolFactory {

    private static class createPool{
        public static IMyPool myPool = new MyDefaultPool();
    }

    public static IMyPool getInstance() {
        return createPool.myPool;
    }
}
