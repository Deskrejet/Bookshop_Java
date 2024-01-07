import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class SqlTypes {
    private Map<Class, String> types = Map.of(
            Integer.class, "INT",
            int.class, "INT",
            Long.class, "BIGINT",
            long.class, "BIGINT",
            String.class, "VARCHAR(200)"
    );


    public String getType(Class clz){
        return types.getOrDefault(clz, null);
    }
}
