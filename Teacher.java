import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;


public class Teacher implements Menu {

    static Map <String, Map<String,String[]>> quizNameMap = new HashMap<>(); //MAP QUIZ NAME AND (QUESTIONS AND OPTIONS) IN IT
    static Map <String, Map<String, Integer>> solutionMap = new HashMap<>(); //MAP QUIZ NAME AND (QUESTIONS AND SOLUTIONS) IN IT IN IT

    

    //METHOD TO ADD A NEW QUIZ
    public void addQuiz(BufferedReader bufferedReader){ 

        Map<String, String[]> quesList = new HashMap<>(); //MAP QUESTIONS AND OPTIONS
        Map<String, Integer> correctOptions = new HashMap<>();  //MAP QUESTIONS AND SOLUTIONS 

        try {
            viewQuizes();

            System.out.println("\nName of the quiz");
            String quizName = bufferedReader.readLine();

            quesList.clear();

            int addmore=1; //FLAG TO ADD MORE QUESTION IN THE QUIZ
            int i=1;  //COUNT THE NUMBER OF QUESTIONS

            while(addmore==1){

                //INPUT THE QUESTION
                System.out.println("\n"+i+" Write down the question");
                String ques = bufferedReader.readLine();
                i++;


                //INPUT THE OPTIONS
                String[] options = new String[4];

                System.out.println("\nEnter options");
                for(int n=0;n<4;n++){
                    options[n] = bufferedReader.readLine();
                }


                //PUTTING IN LIST
                quesList.put(ques,options);



                //INPUT THE CORRECT OPTION
                boolean validInput=false;
                do{
                    System.out.println("\nCorrect option - 1,2,3,4");
                    int optionInput = Integer.parseInt(bufferedReader.readLine());
                    
                    if(optionInput==1 || optionInput==2 || optionInput==3 || optionInput==4){
                        correctOptions.put(ques, optionInput);
                        validInput = true;
                    }
                    else{
                        System.out.println("\nWrong input");
                        validInput=false;
                    }
                }
                while(!validInput);


                //ASK TO ADD MORE QUESTIONS
                System.out.println("\n1 to add next ques or any other digit to not add next ques");
                addmore = Integer.parseInt(bufferedReader.readLine());
            }


            //CONFIRMATION OF ADDING QUIZ
            System.out.println("\n1 to add quiz or any other digit to not add the quiz");
            int confirmQuizAdd = Integer.parseInt(bufferedReader.readLine());
         
            if(confirmQuizAdd==1){
                quizNameMap.put(quizName, quesList);
                solutionMap.put(quizName, correctOptions);

                System.out.println("\nQuiz added");
            }
            else{
                System.out.println("\nQuiz not added");
            }

            
        }
        catch (Exception e) {
            e.printStackTrace();
        }   
    }



    //METHOD TO SHOW SCORE OF ALL STUDENTS
    public void scoreboard(){

        //IF NO STUDENT SOLVED ANY QUIZ
        if (Student.scoreOfAllStudents.isEmpty()) {
            System.out.println("\nNothing to show");
       }

       //ELSE
       else{
            System.out.println("\nScore Board");

            for(String studentName: Student.scoreOfAllStudents.keySet()){
                System.out.println("\n"+studentName);

                if(Student.scoreOfAllStudents.get(studentName).isEmpty()){
                    System.out.println("Nothing to show");
                }
                else{
                    for(String quizName : Student.scoreOfAllStudents.get(studentName).keySet()){
                        System.out.println(quizName+": "+Student.scoreOfAllStudents.get(studentName).get(quizName).get(0));
                    
                    }
            }
            
        }
       }
    }



    //VIEW THE LIST OF QUIZES AND RETURN FALSE IF NO QUIZ AVAILABLE
    public boolean viewQuizes() {
        
        //IF NO QUIZ AVAILABLE
        if(quizNameMap.isEmpty()){
            System.out.println("\nNo quiz available");
            return false;
        }

        //ELSE
        else{
            System.out.println("\nAvailable quizes");

            int i=1;
            for(String keys: quizNameMap.keySet()){
                System.out.println(i+" "+keys);
                i++;
                    
            }
        }
        return true;
    }

    

    public String toString(){
        return "teacher";
    }
        
}
