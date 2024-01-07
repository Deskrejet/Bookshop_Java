import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomServletsContainer {

    private static final int PORT = 8080;
    private ConnectionContainer con;

    private Map<String, Schema> schemaMap;

    public CustomServletsContainer(ConnectionContainer con, Map<String, Schema> schemaMap) {
        this.con = con;
        this.schemaMap = schemaMap;
    }
    public void start(){
        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        for (String name: schemaMap.keySet()) {
            Schema schema = schemaMap.get(name);
            context.addServlet(
                    new ServletHolder(
                            new CustomServlet(
                                    con,
                                    schema
                            )
                    ),
                    "/"+name
            );
        }


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });
        server.setHandler(handlers);

        try {
            server.start();
            System.out.println("Listening port : " + PORT );

            server.join();
        } catch (Exception e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }


}