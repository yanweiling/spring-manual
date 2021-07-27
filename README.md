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
  study04： 初始化   
  study05：BeanPostProcessor ：需求场景，在bean对象实例化之前或者之后，要个性化做一些处理
           在spring容器扫描@Compont类的时候，要判断这个类，是否集实现了BeanPostProcessor
  study06： AOP实现：
           需求：想办法将userSerivce.test()实现以AOP的方式实现
               1.需要从容器中获取UserService的代理对象
               问题：代理对象在什么时候生成的呢？
                    在beanPostProcessor初始化后生成的     
      
      
      spring 容器创建对象bean的时候，默认走的是无参构造方法，如果类中没有定义无参构造方法，而定义了有参构造，则会验证
      是否可以从容器中获取该参数的bean，如果可以，则会调用该有参构造方法创建实例bean；
      
      
      如果类中没有定义任何构造函数，默认就调用其无参构造
                    
  spring生命周期
  
     class(UserService.class)-->实例化-->对象--->属性填充--->初始化afterPropertiesSet()--->AOP-->代理对象--->Bean对象
          
          
      1.找到项目中所有的切面---放到缓存里面【这样缓存里面保存所有的切面bean】
      2.然后遍历所有切面，找到userService的切点，如果存在，则返回userSerivce的代理对象
      3.如何创建代理对象
        cglib---
        public class OrderService{
        @Autowire
        UserService userService;
         public void test(){
           System.out.println(userService);
         }
        }
      
       cglib代理对象创建逻辑：
        自动创建一个OrderServiceProxy extend OrderService{
          public void test(){
           //doing
            super.test();
           //doing
          }
        }
        创建出来的cglib代理对象的userService属性是null，因为先填充OrderService的属性，然后
        生成OrderService的cglib代理对象，执行完成以后，就直接返回代理对象了
        
        所以从容器中获取到的oserSerivce代理对象.getUserService()返回null；
        但是方法test（）中则可以打印出userService对象，因为被代理的方法，内部调用的是原生对象的属性；
        
        这也就解释了，同一个类中，方法A调用方法B，方法A，和方法B上都有事务，结果只有方法A的@Trasanction生效的原因
        
        
                        
     1.实例化
     2.获取对象，对象属性填充
     3.初始化，一般开发人员将bean 实现InitializeBean，实现它的aterPropertiesSet()方法【其实就是属性设置完以后，开发人员可以加入个性化逻辑】
       例如bean userService中，存在一个普通的属性，因为该属性上没有任何注解，所以以上两步无法对该属性赋值，我们可以在初始化阶段
       对该属性设置
       


          @Compont("userService")
           public class UserServiceImpl implements UserService ,InitializingBean{
           
               @Autowire
               private OrderService orderService;
           
               private String otherName;//---------普通属性
           
               @Override
               public void test(){
                   System.out.println(orderService);
                   System.out.println(otherName);
           
               }
           
               //采用初始化的阶段中赋值
               @Override
               public void afterPropertiesSet() throws Exception {
                   System.out.println("这里赋值设置otherName");
                   this.otherName="哈哈";
               }
           } 
      
          
       
           在spring和mybatis的整合，或者spring和dubbo的整合，都会用到初始化机制
            
            初始化阶段使用场景：
           1. 第三方整合
           2. 验证属性是否为空，验证某个值是否不正确，验证不通过，则会抛出异常，这样就无法成功创建该Bean 了   
        4.判断是否需要进行代理，如果需要，则返回代理对象；
          如果不需要，则返回对象本身
          
        如果开启了@EnableAspectJAutoProxy，则自动注册类BeanPostProcessor，如果该bean上存在切点，则会对bean创建代理对象
        
        
## 官方Spring生命周期
  
    AbstractAutowireCapableBeanFactory
    
     protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args){
     
     //对象实例化
     BeanWrapper instanceWrapper = this.createBeanInstance(beanName, mbd, args);
     //对象属性填充
     this.populateBean(beanName, mbd, instanceWrapper);
     //对象实例化
      exposedObject = this.initializeBean(beanName, exposedObject, mbd);
     }
     
     而initializeBean方法内部是：
     protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd) {
      Object wrappedBean = bean;
       //初始化前置方法
       wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
       //调度初始化
        this.invokeInitMethods(beanName, wrappedBean, mbd);
        //初始化后置方法，
         wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
     
     }
     
     
     说明：applyBeanPostProcessorsAfterInitialization这个方法，如果开启aop，则返回代理对象，如果没有开启aop，则，返回对象本身
     
     如果没启代理对象，则该方法进入到AbstractAutoProxyCreator类中的
     
      public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
             if (bean != null) {
                 Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
                 if (this.earlyProxyReferences.remove(cacheKey) != bean) {
                     return this.wrapIfNecessary(bean, beanName, cacheKey);
                 }
             }
     
             return bean;
         }

      ---》
        protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
              if (StringUtils.hasLength(beanName) && this.targetSourcedBeans.contains(beanName)) {
                  return bean;
                  //当前对象不用被代理
              } else if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
                  return bean;
                  //判断当前bean是不是要进行aop，比如当前bean的类型Pointcut，Advice，Advisor等那就不需要进行aop
              } else if (!this.isInfrastructureClass(bean.getClass()) && !this.shouldSkip(bean.getClass(), beanName)) {
                  //创建proxy 如果拥有advise
                  //获取当前beanClass所匹配的切点和切面
                 
                  Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, (TargetSource)null);
                  if (specificInterceptors != DO_NOT_PROXY) {//如果存在切点，则返回proxy
                      this.advisedBeans.put(cacheKey, Boolean.TRUE);
                      Object proxy = this.createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
                      this.proxyTypes.put(cacheKey, proxy.getClass());
                      return proxy;
                  } else {
                      this.advisedBeans.put(cacheKey, Boolean.FALSE);
                      return bean;
                  }
              } else {
                  this.advisedBeans.put(cacheKey, Boolean.FALSE);
                  return bean;
              }
          }
    
        那如何判断该bean是否有切点呢？
        
## spring底层实现事务

    原理同上aop
    
    
    @Compont
    public class OrderService{
       
       @Transactional
       public void methodA(){
         methodB();  //调用的是原生orderService的methodB（），没有aop切入事务
       }
       
       @Transactional
       public void methodB(){
       
          jdbcTemplate.execute("xxxxxx");
          jdbcTemplate.execute("xxxxxx");
       }
    }   
    
    当外面调用
     @Autowire
     OrderService orderService;//从容器中获取到的是代理对象
      
     @Test  
     public void test(){
         orderService.methodA();
     }    
     
     会发现，methodB的事务没有起作用 
     解决办法：
     
            @Compont
            public class OrderService{
               
               @Autowire
               OrderService orderService;
               
               @Transactional
               public void methodA(){
                 orderService.methodB();//调用代理对象的methodB方法
               }
               
               @Transactional
               public void methodB(){
               
                  jdbcTemplate.execute("xxxxxx");
                  jdbcTemplate.execute("xxxxxx");
               }
            }   
                   