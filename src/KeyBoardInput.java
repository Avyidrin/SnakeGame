import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
//Klasa umo¿liwiaj¹ca wprowadzenie danych z klawiatury
public class KeyBoardInput {
	
    private KeyIN keyBoardKey=KeyIN.RIGHT;

    public KeyBoardInput() {
    	
      new Thread(()-> {
          try(Terminal terminal= TerminalBuilder.terminal()) {
              while (true) {
                  setKeyBoardKey(getKeys(terminal.reader().read()));
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }).start();
    }
    private static KeyIN getKeys(int ch) {
        return switch (ch) {
            case 65 -> KeyIN.UP;
            case 66 -> KeyIN.DOWN;
            case 68 -> KeyIN.LEFT;
            case 67 -> KeyIN.RIGHT;
            default -> KeyIN.SPACE;
        };
    }

    public KeyIN getKeyBoardKey() {
        return keyBoardKey;
    }

    public void setKeyBoardKey(KeyIN keyBoardKey) {
        this.keyBoardKey = keyBoardKey;
    }
}