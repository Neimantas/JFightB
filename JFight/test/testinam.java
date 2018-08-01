import java.io.File;

public class testinam {
    public static void main(String[] args) {
        Bybis bybis = new Bybis();
        bybis.loader();
    }
}

class Bybis {
    public void loader() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("main/External/Logger.txt").getFile());
        System.out.println(file.getAbsolutePath());
    }
}
