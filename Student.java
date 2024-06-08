import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class Student implements Menu {

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

    private int ans = 0;
    private final String name;
    BufferedReader bufferedReader;

    // CONSTRUCTOR
    public Student(String name, BufferedReader bufferedReader) {
        this.name = name;
        this.bufferedReader = bufferedReader;
    }

    // METHOD TO SOLVE THE QUIZ
    public void solveQuiz() {

        int sumOfscores = 0;

        if (viewQuizes()) {

            try {
                // ASKING WHICH QUIZ USER WANTS TO SOLVE
                System.out.println("\nEnter quiz name you want to solve");
                String quizToSolve = bufferedReader.readLine();


                Statement statement = connection.createStatement();
                String query = String.format("select quiz_name from quizes where quiz_name = '%s' ", quizToSolve);
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String quizName = resultSet.getString("quiz_name");

                    System.out.println(quizName);

                    // int quesNo=1; //NUMBERING OF THE QUESTIONS

                    String query2 = String.format("select * from questions where quiz = '%s'", quizName);
                    resultSet = statement.executeQuery(query2);

                    String delQuery = String.format("delete from score where username = '%s' and quizname = '%s'",name,quizName);
                    String query3 = "insert into score(username,quizname,ques,score) values (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query3);

                    while (resultSet.next()) {

                        // DISPLAY THE QUESTION
                        int quesNo = resultSet.getInt("ques_no");
                        String ques = resultSet.getString("ques");
                        String a = resultSet.getString("option_a");
                        String b = resultSet.getString("option_b");
                        String c = resultSet.getString("option_c");
                        String d = resultSet.getString("option_d");

                        System.out.println("\n" + quesNo + " " + ques);
                        // quesNo++;

                        // DISPLAY THE OPTIONS

                        System.out.println(1 + " " + a);
                        System.out.println(2 + " " + b);
                        System.out.println(3 + " " + c);
                        System.out.println(4 + " " + d);

                        int correct = resultSet.getInt("correct");

                        // INPUT THE ANSWER
                        boolean validInput = false;
                        do {
                            System.out.println("Your answer");
                            int optionInput = Integer.parseInt(bufferedReader.readLine());

                            if (optionInput == 1 || optionInput == 2 || optionInput == 3 || optionInput == 4) {
                                ans = optionInput;
                                validInput = true;
                            } else {
                                System.out.println("\nWrong input");
                            }
                        } while (!validInput);

                        // CHECKING THE ANSWER

                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, quizName);
                        preparedStatement.setString(3, ques);
                        // preparedStatement.setString(2,);

                        if (correct == ans) {
                            preparedStatement.setInt(4, 2);
                            sumOfscores += 2;
                            System.out.println("Correct");
                        } else {
                            preparedStatement.setInt(4, 0);
                            System.out.println("Wrong, correct option is " + correct);
                        }

                        preparedStatement.addBatch();
                    }

                    statement.executeUpdate(delQuery);
                    preparedStatement.executeBatch();

                    // INCLUDING TOTAL SCORE
                    System.out.println("\nQuiz solved\nyour score is: " + sumOfscores);
                } else {
                    System.out.println("\nNo such index available");
                }
            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // METHOD TO VIEW THE SCORE
    public void viewScore() {

        try {

            Statement statement = connection.createStatement();
            String query = String
                    .format("select quizname, sum(score) from score where username = '%s' group by quizname", name);

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                String tableFormat = "| %-30s | %-7d |%n";
                System.out.format("+--------------------------------+---------+%n");
                System.out.format("|          Name of Quiz          |  Score  |%n");
                System.out.format("+--------------------------------+---------+%n");

                do {
                    System.out.format(tableFormat, resultSet.getString(1), resultSet.getInt(2));
                } while (resultSet.next());

                System.out.format("+--------------------------------+---------+%n");

                String quiz;

                while (true) {
                    System.out.println("\nEnter the name of the quiz to see detailed score \nelse Press 0");
                    quiz = bufferedReader.readLine();

                    if (quiz.equals("0")) {
                        break;
                    }

                    String query2 = String.format(
                            "select ques,score from score where username = '%s' and  quizname = '%s' ", name, quiz);

                    resultSet = statement.executeQuery(query2);

                    if (resultSet.next()) {

                        tableFormat = "| %-70s | %-7d |%n";
                        System.out.format(
                                "+------------------------------------------------------------------------+---------+%n");
                        System.out.format(
                                "|                                Question                                |  Score  |%n");
                        System.out.format(
                                "+------------------------------------------------------------------------+---------+%n");

                        do {
                            System.out.format(tableFormat, resultSet.getString(1).substring(0,Math.min(70,resultSet.getString(1).length())), resultSet.getInt(2));
                        } while (resultSet.next());

                        System.out.format(
                                "+------------------------------------------------------------------------+---------+%n");

                    } else {
                        System.out.println("\nNo such quiz solved by you");
                    }
                }
            }

            else {
                System.out.println("\nNothing to show");
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    // METHOD TO DISPLAY SCORE OF ALL STUDENTS IN QUIZES THEY SOLVED
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
        return "student";
    }

}
