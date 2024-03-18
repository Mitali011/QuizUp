import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Student implements Menu{

    private int ans = 0;
    private String name;
    
    private HashMap<String,ArrayList<Integer>> allQuizScores = new HashMap<>();
    
    static HashMap<String, HashMap<String,ArrayList<Integer>>> scoreOfAllStudents = new HashMap<>();



    //CONSTRUCTOR
    public Student(String name) {
        this.name = name;
    }



    //METHOD TO SOLVE THE QUIZ
    public void solveQuiz(BufferedReader bufferedReader){

        ArrayList<Integer> score = new ArrayList<>();
        int sumOfscores=0;

        if(viewQuizes()){

            try {
                //ASKING WHICH QUIZ USER WANTS TO SOLVE
                System.out.println("\nEnter quiz number you want to solve");
                int quizNo = Integer.parseInt(bufferedReader.readLine());


                int i=1;  //QUIZES INDEX NUMBER

                for(String quizName : Teacher.quizNameMap.keySet()){
                    
                    //LOOPING THROUGH ALL THE QUIZ NAMES
                    if(quizNo!=i){
                        i++; 
                    }

                    //QUIZ FOUND
                    else{
                        System.out.println(i+" "+quizName);

                        int quesNo=1; //NUMBERING OF THE QUESTIONS

                        for(String ques: Teacher.quizNameMap.get(quizName).keySet()){


                            //DISPLAY THE QUESTION
                            System.out.println("\n"+quesNo+" "+ques);
                            quesNo++;


                            //DISPLAY THE OPTIONS
                            int j=1;  //INDEX NUMBER OF OPTIONS
                            for(String option: Teacher.quizNameMap.get(quizName).get(ques)){
                                System.out.println(j+" "+option);
                                j++;
                            }


                            //INPUT THE ANSWER
                            boolean validInput=false;
                            do{
                                System.out.println("Your answer");
                                int optionInput = Integer.parseInt(bufferedReader.readLine());
                                if(optionInput==1 || optionInput==2 || optionInput==3 || optionInput==4){
                                    ans=optionInput;
                                    validInput = true;
                                }
                                else{
                                    System.out.println("\nWrong input");
                                    validInput=false;
                                }
                            }
                            while(!validInput);


                            //CHECKING THE ANSWER
                            if(Teacher.solutionMap.get(quizName).get(ques)==ans){
                                score.add(2);
                                sumOfscores+=2;
                                System.out.println("Correct");
                            }
                            else{
                                score.add(0);
                                System.out.println("Wrong, correct option is "+Teacher.solutionMap.get(quizName).get(ques));
                            }

                        }

                        //INCLUDING TOTAL SCORE IN THE ARRAY OF SCORES
                        score.add(0, sumOfscores);


                        allQuizScores.put(quizName, score);
                        scoreOfAllStudents.put(name, allQuizScores);


                        System.out.println("\nQuiz solved\nyour score is: "+sumOfscores);

                        break;
                    }
                }


                //IF WRONG INDEX ENTERED
                if (i-1 == Teacher.quizNameMap.size() || i==0) {
                    System.out.println("\nNo such index available");
                }

                
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    //METHOD TO VIEW THE SCORE
    public void viewScore(){

        //IF NO QUIZ SOLVED BY THE USER
        if(!scoreOfAllStudents.containsKey(name)){//if(scoreOfAllStudents.get(name).isEmpty()){
            System.out.println("\nNothing to show");
        }

        //ELSE
        else{

            System.out.println("\nYour scores are");

            for(String quizName : scoreOfAllStudents.get(name).keySet()){
                System.out.println("\n"+quizName);

                //GETTING ALL THE QUESTIONS OF THE QUIZ IN AN ARRAY
                ArrayList<String> questions = new ArrayList<>();
                for (String question : Teacher.quizNameMap.get(quizName).keySet()) {
                    questions.add(question);
                }

                int i =0; //VARIABLE TO IDENTIFY TOTAL SCORE AND QUESTION NUMBER

                for (int eachQuesScore : scoreOfAllStudents.get(name).get(quizName)) {
                    //TOTAL SCORE
                    if(i==0){
                        System.out.println("Total Score: "+eachQuesScore);
                        System.out.println("Score in each question");
                        
                    }
                    //SCORE IN EACH QUESTION
                    else{        
                        System.out.println(questions.get(i-1) +": "+eachQuesScore);
                       
                    }
                    i++;
                }
            }
        }
      
    }



    //METHOD TO DISPLAY SCORE OF ALL STUDENTS IN QUIZES THEY SOLVED
    public void scoreboard(){

        //IF NO STUDENT SOLVED ANY QUIZ
       if (scoreOfAllStudents.isEmpty()) {
            System.out.println("\nNothing to show");
       }

       //ELSE
       else{
        System.out.println("\nScore Board");
        
        for(String studentName: scoreOfAllStudents.keySet()){
            System.out.println("\n"+studentName);

            if(scoreOfAllStudents.get(studentName).isEmpty()){
                System.out.println("Nothing to show");
            }
            else{
                for(String quizName : scoreOfAllStudents.get(studentName).keySet()){
                    System.out.println(quizName+": "+scoreOfAllStudents.get(studentName).get(quizName).get(0));
                    
                }
            }
            
        }
       }
    }
    
    public boolean viewQuizes() {
        if(Teacher.quizNameMap.isEmpty()){
            System.out.println("\nNo quiz available");
            return false;
        }
        else{
            System.out.println("\nAvailable quizes");
            int i=1;
            for(String keys: Teacher.quizNameMap.keySet()){
                System.out.println(i+" "+keys);
                i++;
            }
        }
        return true;
    }
    
        public String toString(){
            return "student";
        }
    
    
}
