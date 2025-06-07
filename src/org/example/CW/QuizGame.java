package org.example.CW;

import java.io.*;
import java.util.*;

public class QuizGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ваше имя: ");
        String playerName = scanner.nextLine();

        Questions questions = loadQuestions();//Загрузка вопросов
        if (questions == null) return;

        //Игровой процесс
        int score = 0;
        score += askQuestion(1, questions.question1, questions.response1, questions.goodResponseIndex1, scanner);
        score += askQuestion(2, questions.question2, questions.response2, questions.goodResponseIndex2, scanner);
        score += askQuestion(3, questions.question3, questions.response3, questions.goodResponseIndex3, scanner);

        //Результаты
        System.out.println("\n" + playerName + ", ваш результат: " + score + "/3");

        //Обновление рейтинга
        updateRating(playerName, score);

        //Вывод истории игр
        displayRatingHistory();
    }

    //Метод для вывода вопроса
    private static int askQuestion(int num, String question, String[] options,
                                   int correctIndex, Scanner scanner) {
        System.out.println("\nВопрос " + num + ": " + question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i+1) + ") " + options[i]);
        }

        while (true) {
            System.out.print("Ваш выбор (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  //Очистка буфера

            if (choice < 1 || choice > 3) {
                System.out.println("Ошибка! Введите число от 1 до 3");
                continue;
            }

            return (choice - 1 == correctIndex) ? 1 : 0;
        }
    }

    //Метод загрузки вопросов
    private static Questions loadQuestions() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("questions.dat"))) {
            return (Questions) ois.readObject();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки вопросов: " + e.getMessage());
            return null;
        }
    }

    //Метод обновления рейтинга
    private static void updateRating(String playerName, int score) {
        Raiting rating = loadRating();
        if (rating == null) rating = new Raiting();

        Game game = new Game();
        game.gamer = playerName;
        game.raiting = score;
        game.gameDate = new Date();

        rating.game.add(game);

        saveRating(rating);
    }

    //Метод загрузки рейтинга
    private static Raiting loadRating() {
        File file = new File("raiting.dat");
        if (!file.exists()) return new Raiting();

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (Raiting) ois.readObject();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки рейтинга: " + e.getMessage());
            return new Raiting();
        }
    }

    //Метод сохранения рейтинга
    private static void saveRating(Raiting rating) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("raiting.dat"))) {
            oos.writeObject(rating);
        } catch (Exception e) {
            System.err.println("Ошибка сохранения рейтинга: " + e.getMessage());
        }
    }

    //Метод вывода истории игр
    private static void displayRatingHistory() {
        Raiting rating = loadRating();
        if (rating == null || rating.game.isEmpty()) {
            System.out.println("\nИстория игр пуста!");
            return;
        }

        Collections.sort(rating.game);

        System.out.println("\nИстория игр (по рейтингу):");
        System.out.println("-----------------------------------------------");
        System.out.printf("%-15s %-7s %s\n", "Игрок", "Баллы", "Дата игры");
        System.out.println("-----------------------------------------------");

        for (Game game : rating.game) {
            System.out.printf("%-15s %-7d %tF %<tT\n",
                    game.gamer,
                    game.raiting,
                    game.gameDate);
        }
        System.out.println("-----------------------------------------------");
    }
}