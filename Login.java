import java.util.HashMap;
import java.util.Map;

public class Login {

    String password;
    String name;

    Menu menu; //TO KNOW THE PERSON LOGGININ IS TEACHER OR STUDENT

    static Map<String, String> teacherPasses= new HashMap<>();
    static Map<String, String> studentPasses= new HashMap<>();


    //id and password of all the teachers
    static{
        teacherPasses.put("SonalChawla", "oops@mca");
        teacherPasses.put("InduChhabra", "ai@mca");
        teacherPasses.put("SupreetKaur", "network@mca");
        teacherPasses.put("JasleenKaur", "python@mca");
        teacherPasses.put("RohiniSharma", "algos@mca");
    }


    //CONSTRUCTOR
    public Login(Menu menu, String name, String password){

        //System.out.println("logged in as "+name+" "+ menu);
        this.name = name;
        this.password = password;
        this.menu = menu;
        
    }


    //CHECK IF LOGIN ID AND PASSWORD ARE CORRECT
    public boolean check(){

        //check for teachers
        if(menu.toString().equals("teacher")){
            return(password.equals(teacherPasses.get(name)));
        }

        //check for students
        if(menu.toString().equals("student")){
            return(password.equals(studentPasses.get(name)));
        }

        return false;
    }
}
