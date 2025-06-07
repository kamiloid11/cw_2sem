package org.example.CW;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class WriteQuestions {
    public static void main(String[] args) {
        Questions questions = new Questions();

        //Конфигурация вопросов
        questions.question1 = "Сколько планет в Солнечной системе?";
        questions.response1 = new String[]{"8", "9", "10"};
        questions.goodResponseIndex1 = 0;

        questions.question2 = "Столица Австралии?";
        questions.response2 = new String[]{"Сидней", "Канберра", "Мельбурн"};
        questions.goodResponseIndex2 = 1;

        questions.question3 = "Самый большой океан?";
        questions.response3 = new String[]{"Атлантический", "Индийский", "Тихий"};
        questions.goodResponseIndex3 = 2;

        //Сохранение в файл
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("questions.dat"))) {
            oos.writeObject(questions);
            System.out.println("Файл вопросов успешно создан!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}