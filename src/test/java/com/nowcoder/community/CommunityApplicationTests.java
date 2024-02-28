package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(classes = CommunityApplication.class)
@SpringBootTest
class CommunityApplicationTests implements ApplicationContextAware {
    // 容器扫描组件时，会检测到这个bean。初始化时，会调用ContextAware的set方法，将容器传进来
    private ApplicationContext applicationContext;
    @Test
    void contextLoads() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // applicationContext传入一个bean factory
        this.applicationContext = applicationContext; // 记录一下运行时初始化时传入的容器
    }

    @Test
    public void testApplicationContext(){
        System.out.println(applicationContext);

        AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
        System.out.println(alphaDao.select());
        // 另一种getbean方法，调用优先级较低的实体类
        alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
        System.out.println(alphaDao.select());
    }

    @Test
    public void testBeanManagement(){
        AlphaService alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);

        alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);
    }
    @Test
    public void testBeanConfig(){
        SimpleDateFormat simpleDateFormat =
                applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Autowired                      //不需要getBean，直接声明属性+注解即可
    @Qualifier("alphaHibernate")    //使用非默认优先的实现类
    private AlphaDao alphaDao;
    @Test
    public void testDI(){
        System.out.println(alphaDao);
    }
}
