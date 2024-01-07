import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

@WebServlet("/srvlt")
public class CustomServlet extends HttpServlet {

    private ConnectionContainer con;

    private Schema schema;

    public CustomServlet(ConnectionContainer con, Schema schema) {
        this.con = con;
        this.schema = schema;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Object object = Support.readObject(schema, request);

        ResultSet resultSet;
        if(object == null)
            resultSet = con.getStatement().executeQuery(
                    new QueryConstructor(schema).selectAll()
            );
        else
            resultSet = con.getStatement().executeQuery(
                    new QueryConstructor(schema).select(object)
            );

        response.getWriter().println(
                Support.rsToStr(resultSet, schema)
        );
    }
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Support.readObject(schema, request);

        con.getStatement().execute(
                new QueryConstructor(schema).insert(object)
        );
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Support.readObject(schema, request);

        con.getStatement().execute(
                new QueryConstructor(schema).delete(object)
        );
    }
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Support.readObject(schema, request);
        Object set = Support.readObject(schema, request, "s");

        con.getStatement().execute(
                new QueryConstructor(schema).update(object, set)
        );
    }


}