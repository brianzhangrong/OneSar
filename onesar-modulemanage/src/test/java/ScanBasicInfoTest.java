import com.alibaba.fastjson.JSON;
import com.baidu.disconf.client.common.annotations.DisconfActiveBackupService;
import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.constants.Constants;
import com.baidu.disconf.client.common.update.IDisconfUpdatePipeline;
import com.baidu.disconf.client.scan.inner.statically.model.ScanStaticModel;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class ScanBasicInfoTest {

  //  private static final String filename =
//      "jar:file:/Users/zhangrong/Documents/workspace/ihomefnt/onesar/onesar/onesar-modulemanage"
//          + "/lib/irayproxy.jar!/BOOT-INF/classes!/";
  private static final String filename
      = "file:/Users/zhangrong/Documents/workspace/ihomefnt/irayproxy/irayproxy/target/classes/";

  public static void main(String[] args) {
    ScanStaticModel scanModel = new ScanStaticModel();

    //
    // 扫描对象
    //
    Reflections reflections = getReflection(Lists.newArrayList("com.ihomefnt"));
    scanModel.setReflections(reflections);

    //
    // 获取DisconfFile class
    //
    Set<Class<?>> classdata = reflections.getTypesAnnotatedWith(DisconfFile.class);
    scanModel.setDisconfFileClassSet(classdata);
    System.out.println("class-----------:" + classdata);

    //
    // 获取DisconfFileItem method
    //
    Set<Method> af1 = reflections.getMethodsAnnotatedWith(DisconfFileItem.class);
    scanModel.setDisconfFileItemMethodSet(af1);

    //
    // 获取DisconfItem method
    //
    af1 = reflections.getMethodsAnnotatedWith(DisconfItem.class);
    scanModel.setDisconfItemMethodSet(af1);

    //
    // 获取DisconfActiveBackupService
    //
    classdata = reflections.getTypesAnnotatedWith(DisconfActiveBackupService.class);
    scanModel.setDisconfActiveBackupServiceClassSet(classdata);

    //
    // 获取DisconfUpdateService
    //
    classdata = reflections.getTypesAnnotatedWith(DisconfUpdateService.class);
    scanModel.setDisconfUpdateService(classdata);

    // update pipeline
    Set<Class<? extends IDisconfUpdatePipeline>> iDisconfUpdatePipeline = reflections.getSubTypesOf
        (IDisconfUpdatePipeline
            .class);
    if (iDisconfUpdatePipeline != null && iDisconfUpdatePipeline.size() != 0) {
      scanModel.setiDisconfUpdatePipeline((Class<IDisconfUpdatePipeline>) iDisconfUpdatePipeline
          .toArray()[0]);
    }

  }

  private static Reflections getReflection(List<String> packNameList) {

    //
    // filter
    //
    FilterBuilder filterBuilder = new FilterBuilder().includePackage(Constants.DISCONF_PACK_NAME);

    for (String packName : packNameList) {
      filterBuilder = filterBuilder.includePackage(packName);
    }
    Predicate<String> filter = filterBuilder;

    //
    // urls
    //
    Collection<URL> urlTotals = new ArrayList<URL>();
    for (String packName : packNameList) {
      Set<URL> urls = ClasspathHelper.forPackage(packName);
      urlTotals.addAll(urls);
    }
    System.out.println("before add:" + JSON.toJSONString(urlTotals));
    try {
      urlTotals.add(new URL(filename));
      System.out.println("after add:" + JSON.toJSONString(urlTotals));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    //
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder().filterInputsBy(filter)
        .setScanners(new SubTypesScanner().filterResultsBy(filter),
            new TypeAnnotationsScanner()
                .filterResultsBy(filter),
            new FieldAnnotationsScanner()
                .filterResultsBy(filter),
            new MethodAnnotationsScanner()
                .filterResultsBy(filter),
            new MethodParameterScanner()).setUrls(urlTotals);
    Reflections reflections = new Reflections(configurationBuilder);

    return reflections;
  }
}
