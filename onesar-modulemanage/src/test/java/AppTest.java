import com.ihomefnt.module.lauther.FatJarLaucher;
import com.ihomefnt.module.loader.DirClassLoader;
import java.io.File;
import java.net.URL;
import org.springframework.boot.loader.archive.JarFileArchive;

/**
 * Unit test for simple App.
 */
public class AppTest {

  public static void main(String[] args) throws Exception {
    DirClassLoader loader = new DirClassLoader("onesar-modulemanage/lib");
    URL[] urls = loader.getUrls();

    // URLClassLoader urlClassLoader = new URLClassLoader(urls,
    // Thread.currentThread().getContextClassLoader());
    // Method add = urlClassLoader.getClass().getDeclaredMethod("addURL",
    // URL.class);
    // add.setAccessible(true);
    // add.invoke(urlClassLoader, urls[0]);
    System.out.println(urls[0].toString().replace("file:", ""));
    JarFileArchive jarFileArchive = new JarFileArchive(
        new File(urls[0].toString().replace("file:", "")));
    new FatJarLaucher(jarFileArchive).laucher(new String[]{"server.port=7777"});
    // Class<?> loadClass = urlClassLoader.loadClass("com.ihomefnt.irayproxy.App");
    // Object newInstance = loadClass.newInstance();
    // Method method = newInstance.getClass().getMethod("app");
    // System.out.println(method.invoke(newInstance));
  }

  public static void test() {

    String ds = "name";
  }
}
