import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private static final String url = "jdbc:mysql://localhost:3306/quizup";
    private static final String username = "root";
    private static final String p = "mitaliG$2003";

    String password;
    String name;

    Menu menu; // TO KNOW THE PERSON LOGGININ IS TEACHER OR STUDENT

    static Map<String, String> teacherPasses = new HashMap<>();

    // id and password of all the teachers
    static {
        teacherPasses.put("SonalChawla", "oops@mca");
        teacherPasses.put("InduChhabra", "ai@mca");
        teacherPasses.put("SupreetKaur", "network@mca");
        teacherPasses.put("JasleenKaur", "python@mca");
        teacherPasses.put("RohiniSharma", "algos@mca");
    }

    // CONSTRUCTOR
    public Login(Menu menu, String name, String password) {

        // System.out.println("logged in as "+name+" "+ menu);
        this.name = name;
        this.password = password;
        this.menu = menu;

    }

    // CHECK IF LOGIN ID AND PASSWORD ARE CORRECT
    public boolean check() {

        // check for teachers
        if (menu.toString().equals("teacher")) {
            return (password.equals(teacherPasses.get(name)));
        }

        // check for students
        if (menu.toString().equals("student")) {

            try {
                Connection connection = DriverManager.getConnection(url, username, p);
                Statement statement = connection.createStatement();

                String query = String.format("select password from students where username = '%s'", name);

                ResultSet resultSet = statement.executeQuery(query);

                // next is a boolean method which tells if there ia more data
                if(resultSet.next()) {
                    return (password.equals(resultSet.getString("password")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        return false;
    }
}
