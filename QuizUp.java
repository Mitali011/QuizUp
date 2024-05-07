import java.io.BufferedReader;
import java.io.InputStreamReader;


public class QuizUp {

    public static void main(String[] args) {


        int logchoice;
        boolean backToLog=false;

        InputStreamReader inputStreamReader= new InputStreamReader(System.in);



        

        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            do{
                backToLog=false;  //FLAG TO COME BACK TO LOGIN MENU

                //LOGIN MENU
                System.out.println("\nQUIZ UP\n1 --> sign up\n2 --> log in as Teacher\n3 --> login as Student\n4 --> exit");
                logchoice = Integer.parseInt(bufferedReader.readLine());

                Login login;

                switch (logchoice) {

                    case 1:
                    //NEW STUDENT SIGN UP
                    String newUsername;
                    String newPasswrd;
                    String confirmPass;
                    
                    
                            System.out.println("\nEnter username and password");
                            newUsername =  bufferedReader.readLine();
                            newPasswrd =bufferedReader.readLine();

                            System.out.println("\nConfirm password");
                            confirmPass = bufferedReader.readLine();

                            System.out.println("\n1 to sign up");
                            int signUp = Integer.parseInt(bufferedReader.readLine());

                            //CHECK IF PASSWORD IS RIGHT AND SIGN UP
                            if(signUp==1){
                                if(!newPasswrd.equals(confirmPass)){
                                    
                                    System.out.println("\npassword doesn't match");
                                    backToLog = true;
                                    break;
                                }
                                else if(Login.studentPasses.containsKey(newUsername)){
                                    System.out.println("\nUsername already exist");
                                    backToLog = true;
                                    break;
                                }
                                else{
                                    Login.studentPasses.put(newUsername, newPasswrd);
                                    System.out.println("\nSign up suucessful!");
                                }
                                
                            }
                    
                    //BACK TO LOGIN PAGE
                    backToLog = true;
                    break;


                    case 2:
                    //TEACHER LOGGING IN

                        System.out.println("\nEnter your Username and Password");
                        String teacherName=  bufferedReader.readLine();
                        String teacherPass = bufferedReader.readLine();

                        Teacher teacher = new Teacher(); //OBJECT OF TEACHER CLASS

                        login = new Login(teacher, teacherName,teacherPass);  //OBJECT OF LOGIN CLASS

                        //IF LOGIN DETAILS ARE CORRECT ENTER IN TEACHER MENU
                        if(login.check()) {

                            boolean backToTeacher=false;  //FLAG TO COME BACK TO TEACHER MENU

                            do{
                                backToTeacher = false;

                                //TEACHER MENU
                                System.out.println("\nWelcome \n1 to view list of quizes\n2 to add new quiz\n3 to view scoreboard\n4 to go back to login page");
                                int teacherChoice = Integer.parseInt(bufferedReader.readLine());

                                switch (teacherChoice) {

                                    case 1:
                                    //VIEW LIST OF QUIZES
                                        teacher.viewQuizes();
                                        
                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToTeacher = true;
                                        
                                        break;
                                        
                                    case 2: 
                                    //ADD NEW QUIZ
                                        teacher.addQuiz(bufferedReader);

                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToTeacher = true;
                                        break;
        
                                    case 3:
                                    //DISPLAY SCOREBOARD
                                        teacher.scoreboard();

                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToTeacher = true;

                                        break;
        
                                    case 4:
                                    //EXIT THE TEACHER MENU
                                        backToLog=true;
                                        break;
                                
                                    default:
                                    //WRONG INPUT
                                        System.out.println("\nNo such choice available");
                                        backToTeacher=true;
                                        break;
                                }
                            }
                            while (backToTeacher); 
                        }

                        //WRONG LOGIN DETAILS
                        else{
                            System.out.println("\nWrong username or password");
                            backToLog=true;
                        }

                        break;
   
                    case 3:
                    //STUDENT LOGGING IN

                        System.out.println("\nLet's login\nEnter your username and password");
                        String studentName = bufferedReader.readLine();
                        String studentPass =bufferedReader.readLine();
                        
                        Student student = new Student(studentName);  //OBJECT OF STUDENT CLASS
                        
                        login = new Login(student, studentName,studentPass); //OBJJECT OF LOGIN CLASS


                        //IF LOGIN DETAILS ARE CORRECT ENTER THE STUDENT MENU
                        if(login.check()){

                            boolean backToStudent = false; //FLAG TO COME BACK TO STUDENT MENU

                            do{
                                backToStudent = false;

                                //STUENT MENU
                                System.out.println("\nWelcome "+studentName+"\n1 to view all quizes\n2 to solve quiz\n3 to view scores\n4 to view scoreboard\n5 to go back to login page");
                                int studentChoice = Integer.parseInt(bufferedReader.readLine());

                                switch (studentChoice) {

                                    case 1:
                                    //VIEW LIST OF QUIZES
                                        student.viewQuizes();

                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToStudent = true;
                                        
                                        break;
                                    
                                    case 2:
                                    //SOLVE THE QUIZ
                                        student.solveQuiz(bufferedReader);

                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToStudent = true;

                                        break;

                                    case 3:
                                    //VIEW THE SCORE OF THE STUDENT
                                        student.viewScore();

                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToStudent = true;

                                        break;

                                    case 4:
                                    //DISPLAY SCOREBOARD
                                        student.scoreboard();

                                        System.out.println("\nPress any key to go back");
                                        bufferedReader.readLine();
                                        backToStudent = true;

                                        break;

                                    case 5:
                                    //BACK TO LOGIN MENU
                                        backToLog=true;
                                        break;
                                
                                    default:
                                    //WRONG INPUT
                                        System.out.println("\nNo such choice available");
                                        backToStudent=true;
                                        break;
                                }
                            }
                            while (backToStudent); 
                        }

                        //WRONG LOGIN DETAILS
                        else{
                            System.out.println("\nWrong username or password");
                            backToLog=true;}

                        break;

                    case 4:
                    //EXIT THE QUIZ UP
                        break;
                
                    default:
                        System.out.println("\nNo such choice available");
                        backToLog=true;
                        break;
                }
             }
             while(backToLog);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        


    }
}