import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.springframework.boot.loader.archive.Archive.Entry;
import org.springframework.boot.loader.archive.JarFileArchive;

public class ResourceTest {

  private static final String filename =
      "jar:file:/Users/zhangrong/Documents/workspace/ihomefnt/onesar/onesar/onesar-modulemanage"
          + "/lib/irayproxy.jar!/BOOT-INF/classes!/irayCloud.properties";
  private static URL url;

  public static void main(String[] args) {
    test();
  }

  public static String jarName(String fileName) {
    final List<String> pathSegList = Splitter.on("!").splitToList(fileName);
    String jarFileName = "";
    if (pathSegList != null && pathSegList.size() != 1) {
      jarFileName = pathSegList.get(0);
    }

    return jarFileName.replace("jar:", "").replace("file:", "");
  }

  public static String entryName(String fileName) {
    final List<String> pathSegList = Splitter.on("!/").splitToList(fileName);
    List<String> entryList = Lists.newArrayList();

    if (pathSegList != null && pathSegList.size() != 1) {
      int i = 0;
      for (; i < pathSegList.size(); i++) {
        if (i == 0 || i == pathSegList.size() - 1) {
          continue;
        } else {
          entryList.add(pathSegList.get(i));
        }
      }
    }

    return Joiner.on("!/").join(entryList);
  }

  public static String propertiesName(String fileName) {
    final List<String> pathSegList = Splitter.on("!/").splitToList(fileName);
    return pathSegList.get(pathSegList.size() - 1);
  }


  public static void test() {
    try {
      String jarName = jarName(filename);
      System.out.println(jarName + "---");
      String entryName = entryName(filename);
      System.out.println("---" + entryName);
      JarEntry entry = new JarEntry(entryName);
      JarFile file = new JarFile(new File(jarName));
      System.out.println("entry:" + entry.getName());
      JarEntry jarEntry = file.getJarEntry(entry.getName());
      System.out.println("###" + jarEntry);

      JarFileArchive entries = new JarFileArchive(new File(jarName));

      String propertiesName = propertiesName(filename);
      Iterator<Entry> iterator = entries.iterator();
      System.out.println("-----------------" + propertiesName);
      while (iterator.hasNext()) {
        Entry next = iterator.next();
        if (next.getName().contains(propertiesName)) {
          InputStream in = file.getInputStream(new JarEntry(next.getName()));
          getProperties(in);
        }
      }
      //
      //getProperties(in);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
  }

  private static void getProperties(InputStream in) throws IOException {
    Properties p = new Properties();
    p.load(in);
    Enumeration<?> enumeration = p.propertyNames();
    while (enumeration.hasMoreElements()) {
      String o = (String) enumeration.nextElement();
      System.out.println(o + ":" + p.getProperty(o));
    }
  }
}
