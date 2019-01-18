package cn.gzhu.test.cutormJDKProxy;

import lombok.Data;

import java.io.File;
import java.io.FileInputStream;

/**
 * 描述：自定义类加载器
 * 作用：为了在指定路径下加载指定的字节码文件。
 */
@Data
public class MyClassLoader extends ClassLoader {

    //生成代理类加载目录
    private File dir;

    private String proxyClassPackage;

    public MyClassLoader(String path, String proxyClassPackage) {
        dir = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(null != dir) {
            File classFile = new File(dir, name+".class");
            if (classFile.exists()) {
                //生成class文件的二进制流
                try {
                    FileInputStream ins = new FileInputStream(classFile);
                    byte[] classBytes = new byte[ins.available()];
                    ins.read(classBytes);
                    return defineClass(proxyClassPackage+"."+name, classBytes, 0, classBytes.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //如果上述自定义的类加载器没有加载到，则走默认的类加载器
        return super.findClass(name);
    }
}
