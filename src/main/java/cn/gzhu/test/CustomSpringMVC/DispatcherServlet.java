package cn.gzhu.test.CustomSpringMVC;


import cn.gzhu.test.customSpring.ApplicationContext;
import cn.gzhu.test.customSpring.anno.MyController;
import cn.gzhu.test.myTomcat.MyRequest;
import cn.gzhu.test.myTomcat.MyResponse;
import cn.gzhu.test.myTomcat.MyServlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：前端控制器
 * 作用：定位uri到controller的某个方法上
 */
public class DispatcherServlet extends MyServlet {

    private Map<String, Method> uriMethodMap = new HashMap<String, Method>();
    //由于method的执行需要获取寄生的控制器
    private Map<Method, String> methodPClassNameMap = new HashMap<Method, String>();

    //两个父容器，需要从spring中获取
    private List<String> pClassNameList;
    private Map<String, Object> pClassNameContainer;

    public DispatcherServlet(ApplicationContext applicationContext) {
        pClassNameList = applicationContext.getPClassNameList();
        pClassNameContainer = applicationContext.getPClassNameContainer();
    }

    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) throws Exception {
        doPost(myRequest, myResponse);
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) throws Exception {
        String uri = myRequest.getUri();
        //通过uri获取method
        Method method = uriMethodMap.get(uri);
        //1.通过method获取类全名
        String pClassName = methodPClassNameMap.get(method);
        //2.再通过类全名获取控制器
        Object controller = pClassNameContainer.get(pClassName);
        method.setAccessible(true);
        method.invoke(controller);
    }

    public void init() throws Exception {
        //通过扫描所有基包下的控制器，获取uri-method的映射
        handlerUriMethodMap();
    }
    //获取uri-method的映射
    public void handlerUriMethodMap() throws Exception{
        if (pClassNameList.size() < 1) {
            return;
        }
        for (String pClassName: pClassNameList) {
            Class<?> clazz = Class.forName(pClassName);
            if (clazz.isAnnotationPresent(MyController.class)) {
                Method[] methods = clazz.getMethods();
                if (methods.length < 1) {
                    return;
                }
                StringBuffer uri = new StringBuffer();
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    String value = clazz.getAnnotation(RequestMapping.class).value();
                    uri.append(value);
                }
                for (Method m: methods) {
                    if (m.isAnnotationPresent(RequestMapping.class)) {
                        String value = m.getAnnotation(RequestMapping.class).value();
                        uri.append(value);
                        uriMethodMap.put(uri.toString(), m);
                        methodPClassNameMap.put(m, pClassName);
                    }
                }

            }
        }
    }
}
