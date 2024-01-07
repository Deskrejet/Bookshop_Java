import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Srlzr {
    private String fileName;
    public Srlzr(String fileName) {
        this.fileName = fileName;
    }
    public void execute(List<BookStore> projects) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(fileName), projects);
    }
}
