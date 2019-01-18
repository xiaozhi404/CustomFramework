package cn.gzhu.test.cutormJDKProxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * 描述：代理类,（**该代理只为只有一个参数的方法代理**）
 * 作用：
 * 1.根据接口，通过字符串构造代理类
 * 2.获取所有方法，得到method调用invacation
 * 3.写java文件，编译成class,用加载器加载进内存
 * 4.通过反射进行有参构造器进行生成代理对象，参数为invocation
 */
public class MyProxy {
    //换行
    private static final String ln = "\r";

    public static Object newProxyInstace(MyClassLoader loader, Class<?> interfaces, MyInvocationHandler h) {
        if (null == h) {
            throw new NullPointerException();
        }
        Method[] methods = interfaces.getMethods();
        String proxyClassSimpleName = "$MyProxy$"+interfaces.getSimpleName();
        StringBuffer proxyClassStr = new StringBuffer();
        proxyClassStr.append("package ")
                .append(loader.getProxyClassPackage()).append(";").append(ln)
                .append("import java.lang.reflect.Method;").append(ln)
                .append("import cn.gzhu.test.cutormJDKProxy.MyInvocationHandler;").append(ln)
                .append("public class ")
                .append(proxyClassSimpleName + " implements ")
                .append(interfaces.getName()).append("{").append(ln)
                .append("private MyInvocationHandler h;").append(ln)
                .append("public "+proxyClassSimpleName+"(MyInvocationHandler h) { this.h = h;}").append(ln)
                .append(getMethodString(methods, interfaces))
                .append("}");
        //写入java文件进行编译
        String fileName = loader.getDir() + File.separator + proxyClassSimpleName+".java";
        File proxyClassFile = new File(fileName);
        if (proxyClassFile.exists()) {
            proxyClassFile.delete();
        }
        try {
            compile(proxyClassStr, proxyClassFile);
            //加载字节码
            Class<?> clazz = loader.findClass(proxyClassSimpleName);
            //构造
            Constructor<?> constructor = clazz.getConstructor(MyInvocationHandler.class);
            Object o = constructor.newInstance(h);
            return o;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void compile(StringBuffer proxyClassString, File proxyClassFile) throws Exception {
        //将代理类代码写进文件中
        FileOutputStream fos = new FileOutputStream(proxyClassFile);
        fos.write(proxyClassString.toString().getBytes());
        //编译
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(proxyClassFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        task.call();
        standardFileManager.close();
    }

    private static String getMethodString(Method[] methods, Class<?> interfaces) {
        StringBuffer methodStr = new StringBuffer();
        for (Method method: methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Parameter parameter = method.getParameters()[0];
            methodStr.append(Modifier.toString(method.getModifiers()).replace("abstract", ""))
                    .append(method.getReturnType().getName()+" ")
                    .append(method.getName())
                    .append("("+parameter.getType().getName()+" "+parameter.getName()+")").append("{").append(ln)
                    .append("try {").append(ln)
                    .append("Method met = ")
                    .append(interfaces.getName()).append(".class.getMethod(")
                    .append("\"" + method.getName() + "\"")
                    .append(", new Class[]{"+parameter.getType().getName()+".class});").append(ln)
                    .append("return "+"("+method.getReturnType().getName()+")this.h.invoke(this, met, new Object[]{"+parameter.getName()+"});")
                    .append("} catch (Throwable e) { throw new RuntimeException(e.getMessage()); }")
                    .append("}")
                    .append(ln);
        }
        return methodStr.toString();
    }
}
