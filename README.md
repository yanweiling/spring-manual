## 手写spring

   当程序运行的时候，idea日志中会打印“"D:\Program Files\Java\jdk1.8.0_05\bin\java.exe" -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53923,suspend=y,server=n -javaagent:C:\Users\yanwlb\.IntelliJIdea2018.2\system\captureAgent\debugger-agent.jar=file:/C:/Users/yanwlb/AppData/Local/Temp/capture237.props -Dfile.encoding=UTF-8 -classpath "D:\Program Files\Java\jdk1.8.0_05\jre\lib\charsets.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\deploy.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\access-bridge-64.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\cldrdata.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\dnsns.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\jaccess.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\jfxrt.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\localedata.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\nashorn.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\sunec.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\sunjce_provider.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\sunmscapi.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\sunpkcs11.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\ext\zipfs.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\javaws.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\jce.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\jfr.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\jfxswt.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\jsse.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\management-agent.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\plugin.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\resources.jar;D:\Program Files\Java\jdk1.8.0_05\jre\lib\rt.jar;E:\DCITS-YWL\spring-manual\target\classes;D:\Program Files\JetBrains\IntelliJ IDEA 2018.2.2\lib\idea_rt.jar" com.ywl.test.Test”
   
   会用java.exe执行一个命令，并指定classpath xxx
   
   
   1.启动类 的应用加载类，如何加载配置类中指定路径下的所有类
   2.如果扫描到的类，有注解@Compont，则说明是一个Bean
     但是单例bean和原型bean如何处理
     如何做到原型bean，每次get的时候，都能得到不同的bean呢？
     
     对于单例bean 用map来保存 格式:Map<beanName,bean对象>  ,这个就是单例池
     
     当扫描到类是一个bean以后，如果是单例的话，什么时候加入到单例池中呢？
     
     因为多个地方需要对类进行解析，所以我们统一做解析，这里就需要用到BeanDefinition，这个表示：类的定义
     
     解析一个类，解析完成以后，就生成一个BeanDefinition
##
  study01 :实现单例bean，原型bean，并将bean初始化到spirng容器中
  study02：实现autowire注入
  study03: 实现BeanNameAware ---aware回调     
     
     