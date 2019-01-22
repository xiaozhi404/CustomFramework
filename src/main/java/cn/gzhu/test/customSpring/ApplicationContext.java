package cn.gzhu.test.customSpring;

import cn.gzhu.test.customSpring.anno.MyAutoWired;
import cn.gzhu.test.customSpring.anno.MyController;
import cn.gzhu.test.customSpring.anno.MyRepository;
import cn.gzhu.test.customSpring.anno.MyService;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * 描述：模拟spring 容器工厂
 * 作用：
 * 1.初始化对象，放进IOC容器中
 * 2.扫描IOC容器，进行依赖注入
 */
@Data
public class ApplicationContext {

    private Map<String, Object> idContainer = new HashMap<>();

    //维护全类名与实例的关系
    //作用：1.依赖注入 2.前端控制器通过控制器全类名获取实例
    private Map<String, Object> pClassNameContainer = new HashMap<>();

    private List<String> pClassNameList = new ArrayList<>();

    private String basePacket = SpringConfig.basePacket;

    public void init() {
        try {
            //1.加入配置文件，用springConfig代替
            //2.得到基包下的所有全限定类名pClass
            scanBasePacket(basePacket);
            //3.扫描获取到pClassList, 把@controller等注解修饰的类加入IOC容器
            initIOC(pClassNameList);
            //4.为IOC容器完成依赖注入
            DIForIOC();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scanBasePacket(String basePacket) {
        //通过类加载器的方式，获取基包的相对路径    将 . 替换成 /
        URL url = this.getClass().getClassLoader().getResource(basePacket.replaceAll("\\.", "/"));
        File basePacketDir = new File(url.getPath());
        System.out.println("scan " + basePacketDir);
        File[] files = basePacketDir.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                scanBasePacket(basePacket + "." + file.getName());
            } else {
                //去掉文件后缀 .class
                pClassNameList.add(basePacket + "." + file.getName().split("\\.")[0]);
            }
        }
    }

    public void initIOC(List<String> pClassNameList) throws Exception {
        if (pClassNameList.size() < 1) {
            return;
        }
        for (String pClassName: pClassNameList) {
            Class clazz = Class.forName(pClassName);
            if (clazz.isAnnotationPresent(MyController.class)) {
                MyController myController = (MyController) clazz.getAnnotation(MyController.class);
                Object o = clazz.newInstance();
                idContainer.put(myController.value(), o);
                pClassNameContainer.put(pClassName, o);
                System.out.println("初始化controller" + pClassName + "; id为" + myController.value());
            } else if (clazz.isAnnotationPresent(MyService.class)) {
                MyService myService = (MyService) clazz.getAnnotation(MyService.class);
                Object o = clazz.newInstance();
                idContainer.put(myService.value(), o);
                pClassNameContainer.put(pClassName, o);
                System.out.println("初始化service" + pClassName + "; id为" + myService.value());
            } else if (clazz.isAnnotationPresent(MyRepository.class)) {
                MyRepository myRepository = (MyRepository) clazz.getAnnotation(MyRepository.class);
                Object o = clazz.newInstance();
                idContainer.put(myRepository.value(), o);
                pClassNameContainer.put(pClassName, o);
                System.out.println("初始化repository" + pClassName + "; id为" + myRepository.value());
            }
        }
    }

    public void DIForIOC() throws Exception {
        if (idContainer.size() < 1) {
            return;
        }
        for (Map.Entry<String, Object> entry: idContainer.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            System.out.println("----DI for :" + entry.getValue().getClass().getName());
            for (Field field: fields) {
                if (field.isAnnotationPresent(MyAutoWired.class)) {
                    MyAutoWired myAutoWired = field.getAnnotation(MyAutoWired.class);
                    Object o = idContainer.get(myAutoWired.value());
                    field.setAccessible(true);
                    field.set(entry.getValue(), o);
                }
            }
        }
    }

}
