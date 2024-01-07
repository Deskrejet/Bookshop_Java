import java.util.*;

public class ConsoleExecutor {
    private ConnectionContainer con;
    private Map<String, Schema> schemas;
    private Set<String> commands;

    public ConsoleExecutor(ConnectionContainer con, Map<String, Schema> schemas) {
        this.con = con;
        this.schemas = schemas;
        initCommands();
    }

    private void initCommands(){
        commands = Set.of(
                "ins",
                "upd",
                "fnd",
                "all",
                "dlt"
        );
    }

    public void start() throws Exception {
        boolean flag = true;
        while (flag){
            flag = nextCommand();
        }
    }
    private boolean nextCommand() throws Exception {
        System.out.print("command... ");
        String command = new Scanner(System.in).nextLine().trim();
        if (command.equals("ext")) {
            return false;
        } else if (command.equals("hlp")) {
            System.out.println("commands: " + commands.toString());
            System.out.println("tables: " + schemas.keySet());

        } else if (!commands.contains(command)) {
            System.out.println("error");
            System.out.println("commands: " + commands.toString());
        } else {
            System.out.print("table... ");
            String table = new Scanner(System.in).nextLine().trim();
            if (!schemas.keySet().contains(table)) {
                System.out.println("error");
                System.out.println("tables: " + schemas.keySet());
            } else {
                Schema schema = schemas.get(table);
                if (command.equals("all")){
                    System.out.println(
                            Support.rsToStr(
                                    con.getStatement().executeQuery(new QueryConstructor(schema).selectAll()),
                                    schema
                            )
                    );
                }

                System.out.println("obj... ");
                Object o = Support.readObject(schema);

                if (command.equals("fnd")){
                    System.out.println(
                            Support.rsToStr(
                                    con.getStatement().executeQuery(new QueryConstructor(schema).select(o)),
                                    schema
                            )
                    );
                }

                if (command.equals("ins")){
                    con.getStatement().execute(new QueryConstructor(schema).insert(o));
                }

                if (command.equals("dlt")){
                    con.getStatement().execute(new QueryConstructor(schema).delete(o));
                }

                if (command.equals("upd")){
                    System.out.println("to... ");
                    Object to = Support.readObject(schema);
                    con.getStatement().execute(new QueryConstructor(schema).update(o, to));
                }


            }
        }
        return true;
    }






}
