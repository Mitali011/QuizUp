import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;


public class Teacher implements Menu {

    private static final String url = "jdbc:mysql://localhost:3306/quizup";
    private static final String username = "root";
    private static final String password = "mitaliG$2003";
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    BufferedReader bufferedReader;

    public Teacher(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    // METHOD TO ADD A NEW QUIZ
    public void addQuiz() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            viewQuizes();

            System.out.println("\nName of the quiz");
            String quizName = bufferedReader.readLine();

            int addmore = 1; // FLAG TO ADD MORE QUESTION IN THE QUIZ
            int i = 1; // COUNT THE NUMBER OF QUESTIONS

            String query = "insert into questions(ques, option_a, option_b, option_c, option_d, correct,quiz,ques_no) values (?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            while (addmore == 1) {

                // INPUT THE QUESTION
                System.out.println("\n" + i + " Write down the question");
                String ques = bufferedReader.readLine();
                preparedStatement.setString(1, ques);
                i++;

                // INPUT THE OPTIONS
                System.out.println("\nEnter options");
                for (int n = 0; n < 4; n++) {
                    String option = bufferedReader.readLine();
                    preparedStatement.setString(n + 2, option);
                }

                // INPUT THE CORRECT OPTION
                boolean validInput;
                do {
                    System.out.println("\nCorrect option - 1,2,3,4");
                    int optionInput = Integer.parseInt(bufferedReader.readLine());

                    if (optionInput == 1 || optionInput == 2 || optionInput == 3 || optionInput == 4) {
                        preparedStatement.setInt(6, optionInput);
                        validInput = true;
                    } else {
                        System.out.println("\nWrong input");
                        validInput = false;
                    }
                } while (!validInput);

                // add quizname
                preparedStatement.setString(7, quizName);

                // add qes_no
                preparedStatement.setInt(8, i);

                // add this question to batch
                preparedStatement.addBatch();

                // ASK TO ADD MORE QUESTIONS
                System.out.println("\n1 to add next ques or any other digit to not add next ques");
                addmore = Integer.parseInt(bufferedReader.readLine());
            }

            // CONFIRMATION OF ADDING QUIZ
            System.out.println("\n1 to add quiz or any other digit to not add the quiz");
            int confirmQuizAdd = Integer.parseInt(bufferedReader.readLine());

            if (confirmQuizAdd == 1) {
                // quizNameMap.put(quizName, quesList);
                // solutionMap.put(quizName, correctOptions);

                // add question in questions table
                int[] row = preparedStatement.executeBatch();
                boolean prblm = false;
                for (int j = 0; j < row.length; j++) {
                    if (row[j] != 1) {
                        System.out.println("Failed to add question " + (j + 1));
                        prblm = true;
                    }
                }

                if (!prblm) {
                    // try to add quiz in quizes
                    try {
                        Statement statement = connection.createStatement();
                        String query3 = String.format("delete from quizes where quiz_name = '%s'", quizName);
                        String query2 = String.format("insert into quizes(quiz_name) values ('%s')", quizName);
                        statement.executeUpdate(query3);
                        int rows2 = statement.executeUpdate(query2);

                        if (rows2 > 0) {
                            System.out.println("\nQuiz added");
                        } else {
                            System.out.println("\nQuiz not added");
                        }

                    } catch (SQLException e) {
                        System.out.println("\nQuiz not added");
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("\nQuiz not added");
                }
            } else {
                System.out.println("\nQuiz not added");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // METHOD TO SHOW SCORE OF ALL STUDENTS
    public void scoreboard() {

        while (true) {

            try {
                viewQuizes();
                System.out.println("\nEnter the quiz name to see the scoreboard \nelse Press 0");
                String quiz = bufferedReader.readLine();

                if (quiz.equals("0")) {
                    break;
                }

                String query = String.format(
                        "select username, sum(score) as s from score where quizname = '%s' group by username order by s desc",
                        quiz);
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {

                    String tableFormat = "| %-30s | %-7d |%n";
                    System.out.format("+--------------------------------+---------+%n");
                    System.out.format("|            Username            |  Score  |%n");
                    System.out.format("+--------------------------------+---------+%n");

                    do {
                        System.out.format(tableFormat, resultSet.getString(1), resultSet.getInt(2));
                    } while (resultSet.next());

                    System.out.format("+--------------------------------+---------+%n");

                } else {
                    System.out.println("Nothing to show");
                }

            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // VIEW THE LIST OF QUIZES AND RETURN FALSE IF NO QUIZ AVAILABLE
    public boolean viewQuizes() {

        try {
            Statement statement = connection.createStatement();

            String query = " select quiz_name from quizes";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                System.out.println("\nNo quiz available");
                return false;
            } else {
                System.out.println("\nAvailable quizes");
                // int id = resultSet.getInt("no");
                String name = resultSet.getString("quiz_name");
                System.out.println(name);

                while (resultSet.next()) {
                    // id = resultSet.getInt("no");
                    name = resultSet.getString("quiz_name");
                    System.out.println(name);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public String toString() {
        return "teacher";
    }
}
