package edu.cn.demo.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class StopWatchAspect {
    /**
     * 时间统计结果表，key时方法的signature，value是方法的总用时
     */
    final Map<String,Long> metric = Collections.synchronizedMap(new HashMap<>());

    /**
     * 所有标注了@Service的类的方法都会被统计，统计结果写入结果表
     */
    @Around("@within(org.springframework.stereotype.Service)")
    public Object calculateTime(ProceedingJoinPoint joinPoint) throws  Throwable{
        long t1 = Calendar.getInstance().getTimeInMillis();
        Object retValue = joinPoint.proceed();
        long t2 = Calendar.getInstance().getTimeInMillis();
        String methodSig = joinPoint.getSignature().toString();
        long base = metric.containsKey(methodSig)?metric.get(methodSig):0;
        metric.put(methodSig,base+t2-t1);
        return retValue;

    }

    public Map<String,Long> getMetric(){
        return metric;
    }


}
