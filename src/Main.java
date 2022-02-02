import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	
    	int Difficulty; //Poziom trudnoœci gry

        try (var scanner = new Scanner(System.in)) {
        	//Napis powitalny oraz wybór poziomu trudnoœci
            System.out.println("Witaj w konsolowej wersji gry Snake");
            System.out.print("Podaj poziom trudnoœci[0-5]: ");
            //Pêtla, która uniemo¿liwia wybranie poziomu trudnoœci spoza zakresu
        	do {
        		Difficulty = scanner.nextInt(); //Przypisanie wprowadzonej wartoœci do zmiennej
        	} while(Difficulty < 0 || Difficulty > 5);
        	
            var SnakeConsole = new SnakeGame(34, 20, Difficulty); //U¿ycie konstruktora
        	SnakeConsole.startGame(); //Wystartowanie gry
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}