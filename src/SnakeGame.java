import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class SnakeGame {
	
    private final int GAME_WIDTH; //Szeroko�� gry wyra�ona w jednostkach
    private final int GAME_HEIGHT; //Wysoko�� gry wyra�ona w jednostkach
    private final ArrayList<SnakeBody> SnakeB = new ArrayList<>(); //Przechowuje informacje o koordynatach segment�w w�a
    private final KeyBoardInput KeyBoardIN = new KeyBoardInput(); //Zmienna przechowuj�ca input klawiatury
    private int FoodX; //Po�o�enie wygenerowanego jedzenia w osi X
    private int FoodY; //Po�o�enie wygenerowanego jedzenia w osu Y
    private int DifficultyLevel; //Poziom trudno�ci
    private KeyIN Direction = KeyIN.RIGHT; //Zmienna odpowiedzialna za kierunek ruchu w�a, domy�lnie w prawo
    private int BodyParts = 3; //Liczba segment�w w�a
    private int Score; //Wynik gry
    private boolean Running = true; //Zmienna m�wi�ca czy gra trwa
    
    //Konstruktor
    public SnakeGame(int width, int height, int Diff) {
        this.GAME_WIDTH = width;
        this.GAME_HEIGHT = height;
        this.DifficultyLevel = Diff;
    }
    //Funkcja uruchamiaj�ca gr�
    void startGame() throws InterruptedException, IOException {
    	for(int i=0; i<BodyParts; i++)
    		SnakeB.add(new SnakeBody(1, 1));
    	
    	NewFood(); //Generowanie koordynat�w dla jedzenia
    	
        //P�tla steruj�ca gr�
        while(true){
            if(Running){
                move();
                System.out.println(message("Wynik : "+Score,GAME_WIDTH+1,Position.Right,"D�ugo�� w�a : "+BodyParts));
                System.out.println();
                checkFood();
                checkCollisions();
                draw();
            }
            else{
                System.out.println("Koniec gry");
                System.out.println("Tw�j wynik to: "+Score);
                System.out.println("Wci�nij spacj�, aby kontynuowa�");
                if (KeyBoardIN.getKeyBoardKey() == KeyIN.SPACE) {
                	Running = true;
                    resetGame();
                }
            }
            Thread.sleep(750-(DifficultyLevel*120)); //Szybko�� od�wie�ania si� gry
            cls();
        }
    }
    //Funkcja resetuj�ca gr� w przypadku przegrania
    private void resetGame() {
    	SnakeB.clear();
        BodyParts = 3;
    	Score = 0;
    	for(int i=0; i<BodyParts; i++)
    		SnakeB.add(new SnakeBody(1,1));
    	Direction = KeyIN.RIGHT;
    }
    //Funkcja odpowiedzialna za poruszanie w�em
    public void move(){
    	//Segment za ograniczenie ruchu w�owi o 180 stopni
        if(KeyBoardIN.getKeyBoardKey()==KeyIN.UP && Direction !=KeyIN.DOWN)
        	Direction=KeyIN.UP;
        if(KeyBoardIN.getKeyBoardKey()==KeyIN.DOWN && Direction !=KeyIN.UP)
        	Direction=KeyIN.DOWN;
        if(KeyBoardIN.getKeyBoardKey()==KeyIN.LEFT && Direction !=KeyIN.LEFT)
        	Direction=KeyIN.LEFT;
        if(KeyBoardIN.getKeyBoardKey()==KeyIN.RIGHT && Direction !=KeyIN.LEFT)
        	Direction=KeyIN.RIGHT;
        //Segment odpowiedzialny za przesuwanie si� segment�w w�a
        IntStream.iterate(SnakeB.size()-1,i->i>=1,i->i-1).forEachOrdered(i->{
        	SnakeB.get(i).x=SnakeB.get(i-1).x;
        	SnakeB.get(i).y=SnakeB.get(i-1).y;
        });
        //Segment odpowiedzialny za ruch w�a w danym kierunku
        switch (Direction){
            case UP -> {
            	SnakeB.get(0).y--;
                if(SnakeB.get(0).y==0)
                	SnakeB.get(0).y=GAME_HEIGHT;
            }
            case DOWN -> {
            	SnakeB.get(0).y++;
                if(SnakeB.get(0).y==GAME_HEIGHT)
                	SnakeB.get(0).y=0;
            }
            case LEFT -> {
            	SnakeB.get(0).x--;
                if(SnakeB.get(0).x==0)
                	SnakeB.get(0).x=GAME_WIDTH;
            }
            case RIGHT -> {
            	SnakeB.get(0).x++;
                if(SnakeB.get(0).x==GAME_WIDTH)
                	SnakeB.get(0).x=0;
            }
        }
    }
    //Funkcja sprawdzaj�ca czy w�� zjad� jedzenie
    private void checkFood() {
        if(SnakeB.get(0).x==FoodX && SnakeB.get(0).y==FoodY){
        	SnakeB.add(new SnakeBody(0,0));
            NewFood();
            Score+=(DifficultyLevel*2);
            BodyParts++;
        }
    }
    
    //Funkcja sprawdzaj�ca czy w�� uderzy� w swoje cia�o, je�li tak si� sta�o, zatrzymuje gr�
    private void checkCollisions() {
        if(IntStream.range(1,SnakeB.size()).anyMatch(i->SnakeB.get(0).x==SnakeB.get(i).x && SnakeB.get(0).y==SnakeB.get(i).y ))
        	Running = false;
    }
    //Funkcja generuj�ca nowe koordynaty dla jedzenia
    private void NewFood() {
    	FoodX=(int)(Math.random()*(GAME_WIDTH-3)+2); //Generowanie po�o�enia jedzenia w osi X
    	FoodY=(int)(Math.random()*(GAME_HEIGHT-3)+2);//Generowanie po�o�enia jedzenia w osi Y
    }
    //Funkcja uruchamiaj�ca komend� "cls"
    //pozwala na wy�wietlanie obszaru gry w jednym miejscu, 
    //zamiast ka�dej iteracji w osobnych wierszach
    private void cls() throws IOException, InterruptedException {
        new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
    }
    //Funkcja tworz�ca szablon dla napis�w
    private String message(String start,int length,Position Pos,String end){
        return switch (Pos){
            case Left,Right ->start+(" ".repeat(length-start.length()-end.length()))+end;
            case Center -> start+(" ".repeat(length-start.length()-end.length()/2))+end;
        };
    }
    //Funkcja rysuj�ca obszar gry
    private void draw() {
        var Field = new StringBuilder();
        
        for (int i=0; i<=GAME_HEIGHT; i++) {
            for (int j=0; j<=GAME_WIDTH; j++) {
                if (i==0 || i==GAME_HEIGHT || j==0 || j==GAME_WIDTH || (j==FoodX && i==FoodY)) {
                    char Food = '&';
                    char Wall = '#';
                    if (j == FoodX && i == FoodY)
                    	Field.append(Food);
                    else 
                    	Field.append(Wall);
                } else if (isSnakePart(i, j)) {
                    char Body = 'O';
                    Field.append(Body);
                } else Field.append(" ");
            }
            Field.append("\n");
        }
        System.out.println(Field);
    }
    //Funkcja sprawdzaj�ca czy w�� znajduje si� na danych koordynatach
    private boolean isSnakePart(int i, int j) {
        return SnakeB.stream().anyMatch(SnakeBody -> j == SnakeBody.x && i == SnakeBody.y);
    }
   
}