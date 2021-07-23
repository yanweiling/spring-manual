package com.spring;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义applicationcontext
 */
public class ManualApplicationContext {
    private Class configClass;

    //单例池,用来存储单例对象
    ConcurrentHashMap<String,Object> singleObjects=new ConcurrentHashMap<>();
    //所有扫描到的bean的定义
    ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>();

    public ManualApplicationContext(Class configClass) throws ClassNotFoundException {
        this.configClass=configClass;
        //解析配置类：1.找到CompontScan注解---->2.扫描路径--3.解析对象，生成BeanDefinition-4.将对象放在beanDefinitionMap中
        scan(configClass);
        //项目启动时就需要将单例bean全部创建好
        for (Map.Entry<String,BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition=entry.getValue();
            if(beanDefinition.getScope().equals("singleton")){
               Object bean=createBean(beanDefinition);
               singleObjects.put(entry.getKey(),bean);
            }
            
        }

    }

    //创建bean的方法
    private Object createBean(BeanDefinition beanDefinition){
        Class clazz=beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scan(Class configClass) throws ClassNotFoundException {

        CompontScan compontScanAnnotaion= (CompontScan) configClass.getDeclaredAnnotation(CompontScan.class);
        //获取扫描路径
        String path=compontScanAnnotaion.value();
        System.out.println(path);
        path=path.replace(".","/");
        //扫描
        //类加载器：三种：
        // Bootstrap--->对应jre/lib下面的
        // Ext--->jre/ext/lib
        // App classload（应用类加载器）--->classpath
        //每一种类加载器，都对应一个路径，路径下的类或者jar包都称为资源

        //该加载器的相对位置是：E:\DCITS-YWL\spring-manual\target\classes
        ClassLoader classLoader= ManualApplicationContext.class.getClassLoader();//获取app 应用类加载器
        URL resource=classLoader.getResource(path);// 这个资源可以是文件也可以是目录，这个我们是目录
        File file=new File(resource.getFile());
        if (file.isDirectory()) {//判断是否是目录  快捷键:file.isDirectory().if回车

            File[] files=file.listFiles();
            for(File f : files){
                System.out.println(f);
                //根据全路径名获得类的全限类名全程
                String fileName=f.getAbsolutePath();
                String className=fileName.substring(fileName.indexOf("com"),fileName.indexOf(".class"));
                className=className.replace("\\",".");
                System.out.println("开始加载类:"+className);
                Class<?> clazz = classLoader.loadClass(className); //快捷键 classLoader.loadClass("").var回车
                //然后判断这个类是否有注解
                if (clazz.isAnnotationPresent(Compont.class)) {
                    //表示当前这个类是一个Bean
                    //判断是单例bean还是原型bean ，@1.需要对类进行解析
                    Compont compontAnnotation = clazz.getDeclaredAnnotation(Compont.class);
                    String beanName=compontAnnotation.value();
                    BeanDefinition beanDefinition=new BeanDefinition();
                    beanDefinition.setClazz(clazz);
                    if(clazz.isAnnotationPresent(Scope.class)){
                        Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                        beanDefinition.setScope(scopeAnnotation.value());
                    }else{
                        //如果没有这个注解，则表示它是单例的
                        beanDefinition.setScope("singleton");

                    }
                    beanDefinitionMap.put(beanName,beanDefinition);
                }
            }
        }
    }

    /**
     *
      1.如何根据名称，找到类
      2.找到类需要对该类进行解析
        在@1的位置也需要对类进行解析，所以解析类，我们可以抽象出来
     */
    public Object getBean(String beanName){
        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                return singleObjects.get(beanName);
            }else{
                //创建这个对象
                return createBean(beanDefinition);

            }

        }else {
            //不存在对应的bean
            throw new NullPointerException();
        }
    }
}
