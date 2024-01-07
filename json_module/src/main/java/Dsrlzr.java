import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Dsrlzr {
    private String filename;
    public Dsrlzr(String filename) {
        this.filename = filename;
    }
    public List<BookStore> execute() throws IOException {
        return new ObjectMapper().readValue(new FileInputStream(filename),
                new TypeReference<List<BookStore>>() {});
    }
}
