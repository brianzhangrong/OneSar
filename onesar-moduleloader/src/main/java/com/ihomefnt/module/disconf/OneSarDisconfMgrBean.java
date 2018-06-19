package com.ihomefnt.module.disconf;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.store.aspect.DisconfAspectJ;
import com.baidu.disconf.client.store.inner.DisconfCenterHostFilesStore;
import com.baidu.disconf.client.support.utils.StringUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

public class OneSarDisconfMgrBean extends DisconfMgrBean {

  ApplicationContext applicationContext;
  private String scanPackage = null;

  @Override
  public void setScanPackage(String scanPackage) {
    this.scanPackage = scanPackage;
    super.setScanPackage(scanPackage);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    super.setApplicationContext(applicationContext);
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {

    // 为了做兼容
    DisconfCenterHostFilesStore.getInstance().addJustHostFileSet(super.getFileList());

    List<String> scanPackList = StringUtil.parseStringToStringList(scanPackage,
        SCAN_SPLIT_TOKEN);
    // unique
    Set<String> hs = new HashSet<String>();
    hs.addAll(scanPackList);
    scanPackList.clear();
    scanPackList.addAll(hs);

    // 进行扫描
    OneSarDisconfMgr.getInstance().setApplicationContext(applicationContext);
    OneSarDisconfMgr.getInstance().start(scanPackList);

    // register java bean
    registerAspect(registry);
  }

  private void registerAspect(BeanDefinitionRegistry registry) {

    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(DisconfAspectJ.class);
    beanDefinition.setLazyInit(false);
    beanDefinition.setAbstract(false);
    beanDefinition.setAutowireCandidate(true);
    beanDefinition.setScope("singleton");

    registry.registerBeanDefinition("disconfAspectJ", beanDefinition);
  }

}
