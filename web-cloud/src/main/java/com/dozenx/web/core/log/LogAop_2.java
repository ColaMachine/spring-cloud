package com.dozenx.web.core.log;

/**
 * Created by dozen.zhang on 2016/5/13.
 */

import com.dozenx.web.util.RedisUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.UUID;


@Aspect

@Component
public class LogAop_2 {
//
//    ThreadLocal<Long> time=new ThreadLocal<Long>();
//    ThreadLocal<String> tag=new ThreadLocal<String>();
//
//    @Pointcut("@annotation(core.log.Log)")
//    public void log(){
//        System.out.println("我是一个切入点");
//    }
//
//    /**
//     * 在所有标注@Log的地方切入
//     * @param joinPoint
//     */
//   // @Before("log()")
//    public void beforeExec(JoinPoint joinPoint){
//
//        time.set(System.currentTimeMillis());
//        tag.set(UUID.randomUUID().toString());
//
//        info(joinPoint);
//
//        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
//        Method method=ms.getMethod();
//        System.out.println(method.getAnnotation(Log.class).name()+"标记"+tag.get());
//    }
//
//   // @After("log()")
//    public void afterExec(JoinPoint joinPoint){
//        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
//        Method method=ms.getMethod();
//        System.out.println("标记为"+tag.get()+"的方法"+method.getName()+"运行消耗"+(System.currentTimeMillis()-time.get())+"ms");
//    }
//
//    @Around("log()")
//    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable{
//        Object object=null;
//        if (pjp.getTarget() instanceof JedisManageSupport) {
//            if (this.isDeclaredMethod(pjp.getTarget(),((MethodSignature) pjp.getSignature()).getMethod())) {
//                Jedis jedis = null;
//                try {
//                    JedisManageSupport support = (JedisManageSupport) pjp.getTarget();
//                    jedis = RedisUtil.getJedis();
//                    support.setJedis(jedis);
//                    System.out.println("我是Around，来打酱油的");
//                    object =  pjp.proceed();
//                    support.setJedis(null);
//                } catch (Exception e) {
//                    RedisUtil.returnBrokenResource(jedis);
//                    e.printStackTrace();
//                } finally {
//                    if (jedis != null) {
//                        RedisUtil.returnResource(jedis);
//                    }
//// logger.debug("调用之后归还jedis对象,method:" + method);
//                }
//
//            } else {
//                pjp.proceed();
//            }
//        }
//        else {
//            throw new Exception("使用该代理必须继承JedisManageSupport");
//        }
//        return object;
//    }
//    public static synchronized Jedis getResource() throws Exception {
//        //logger.info("jedis pool:" + (pool == null));
//        Jedis temp =  null;
//        for (int i = 0; i < 3; i ++) {
//            temp = RedisUtil.getResource();
//
//            if (temp != null) {
//                break;
//            }
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                //不处理
//            }
//
//        }
//
//        if (temp == null) {
//            throw new Exception("RedisUtil.getResource ,获取jedis 为null");
//        }
//
//        return temp;
//    }
//    private boolean isDeclaredMethod(Object target, Method arg1) {
//        Method temp = null;
//        try {
//            temp = target.getClass().getDeclaredMethod(arg1.getName(), arg1.getParameterTypes());
//        }
//        catch (SecurityException e) {
//            e.printStackTrace();
//        }
//        catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
///**
// * 不为null，并且是非私有的，返回true
// */
//        if (temp != null) {
//
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    private void info(JoinPoint joinPoint){
//        System.out.println("--------------------------------------------------");
//        System.out.println("King:\t"+joinPoint.getKind());
//        System.out.println("Target:\t"+joinPoint.getTarget().toString());
//        Object[] os=joinPoint.getArgs();
//        System.out.println("Args:");
//        for(int i=0;i<os.length;i++){
//            System.out.println("\t==>参数["+i+"]:\t"+os[i].toString());
//        }
//        System.out.println("Signature:\t"+joinPoint.getSignature());
//        System.out.println("SourceLocation:\t"+joinPoint.getSourceLocation());
//        System.out.println("StaticPart:\t"+joinPoint.getStaticPart());
//        System.out.println("--------------------------------------------------");
//    }

}