package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static int n = 1000;
    public static void main(String[] args) {

        BlockingQueue<String> arrText1 = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> arrText2 = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> arrText3 = new ArrayBlockingQueue<>(100);

        new Thread(() -> {
            String text;
            for (int i = 0; i < n; i++) {
                text = generateText("abc", n);
                try {
                    arrText1.put(text);
                    arrText2.put(text);
                    arrText3.put(text);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            myThread(arrText1,'a');
        }).start();

        new Thread(() -> {
            myThread(arrText2,'b');
        }).start();

        new Thread(() -> {
            myThread(arrText3,'c');
        }).start();
    }

    public static void myThread(BlockingQueue<String> arrText,  char c){
        int maxCount = 0;
        for (int i = 0; i < n; i++) {
            try {
                maxCount = calcMax(arrText.take(), maxCount, c);
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("Максимальное количество повторений символа '" + c + "' в строке " + maxCount);
    }
    public static int calcMax(String text, int maxCount, char c) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                count++;
            }
        }
        if (maxCount > count)
            return maxCount;
        else
            return count;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}