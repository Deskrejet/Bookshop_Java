import java.io.IOException;
import java.util.List;

public class JsonWriteMain {
    public static void main(String[] args) throws IOException {
        List<BookStore> stores = new Generator(10).gen();
        System.out.println(stores);
        new Srlzr("file.json").execute(stores);

    }
}
