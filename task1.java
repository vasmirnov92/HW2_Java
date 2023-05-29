import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Дана json строка [{
 * "фамилия":"Иванов","оценка":"5","предмет":"Математика"},{"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},{"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
 * Задача написать метод(ы), который распарсит строку и выдаст ответ вида:
 * Студент Иванов получил 5 по предмету Математика.
 * Студент Петрова получил 4 по предмету Информатика.
 * Студент Краснов получил 5 по предмету Физика.
 * 
 * Используйте StringBuilder для подготовки ответа
 * 
 * Исходная json строка это просто String !!! Для работы используйте методы
 * String, такие как replace, split, substring и т.д. по необходимости
 * 
 * Создать метод, который запишет результат работы в файл. Обработайте
 * исключения и запишите ошибки в лог файл.
 * 2. *Получить исходную json строку из файла, используя FileReader или Scanner
 * 3. *Реализуйте алгоритм сортировки пузырьком числового массива, результат
 * после каждой итерации запишите в лог-файл.
 */
public class task1 {

    public static void main(String[] args) {
        String input = readFile();
        //System.out.println(input);
        String deletedInput = deleteSymbols(input);
        System.out.println(deletedInput);
        saveToFile(deletedInput);

    }

    static String readFile() {

        String jsonString = new String();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("inputString.txt"));
            jsonString = reader.readLine();
            while (jsonString != null) {
                stringBuilder.append(jsonString);
                // System.out.println(jsonString);
                jsonString = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }

    static String deleteSymbols(String s) {
        String deleted = null;
        StringBuilder stringBuilder = new StringBuilder();
        deleted = (s.replaceAll(" ", "")
                .replaceAll("\\[\\{\"ф.+?\":\"", "Студент ")
                .replaceAll("\"\\},\\{\"ф.+?\":\"", ".\nСтудент ")
                .replaceAll("\",\"о.+?\":\"", " получил ")
                .replaceAll("\",\"п.+?\":\"", " по предмету ")
                .replaceAll("\"}]", "."));

        return deleted;
    }

    static void saveToFile(String s) {
        Logger logger = Logger.getAnonymousLogger();
        
        FileHandler fileHandler = null;
        try{

            fileHandler = new FileHandler("log.txt");
        } catch(IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fileHandler);
        String path = "result.txt";
        try (FileWriter fileWriter = new FileWriter(path, true)) {
            
            fileWriter.write(s);
            fileWriter.flush();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.log(Level.WARNING, "==||== BROKEN ==||==");
        }
        fileHandler.close();
    }




}