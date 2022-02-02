import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	
    	int Difficulty; //Poziom trudno�ci gry

        try (var scanner = new Scanner(System.in)) {
        	//Napis powitalny oraz wyb�r poziomu trudno�ci
            System.out.println("Witaj w konsolowej wersji gry Snake");
            System.out.print("Podaj poziom trudno�ci[0-5]: ");
            //P�tla, kt�ra uniemo�liwia wybranie poziomu trudno�ci spoza zakresu
        	do {
        		Difficulty = scanner.nextInt(); //Przypisanie wprowadzonej warto�ci do zmiennej
        	} while(Difficulty < 0 || Difficulty > 5);
        	
            var SnakeConsole = new SnakeGame(34, 20, Difficulty); //U�ycie konstruktora
        	SnakeConsole.startGame(); //Wystartowanie gry
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}