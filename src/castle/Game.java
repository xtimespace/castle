package castle;

import java.util.Scanner;
import java.util.HashMap;

public class Game {

  private Room currentRoom;
  private HashMap<String, Handler> handlers = new HashMap<String, Handler>();
  private HashMap<String, Room> rooms = new HashMap<String, Room>();

  public Game() {
    handlers.put("bye", new HandlerBye(this));
    handlers.put("go", new HandlerGo(this));
    handlers.put("help", new HandlerHelp(this));

    createRooms();
  }

  private void setExits(String roomStr, HashMap<String, String> exits) {
    Room room = rooms.get(roomStr);
    if (room != null) {
      for (String dir : exits.keySet()) {
        String exitStr = exits.get(dir);
        if (exitStr == null) continue;
        Room exit = rooms.get(exitStr);
        if (exit != null) {
          room.setExit(dir, exit);
        }
      }
    }
  }

  public void createRooms() {

    rooms.put("outside", new Room("城堡外"));
    rooms.put("lobby", new Room("大堂"));
    rooms.put("pub", new Room("小酒吧"));
    rooms.put("study", new Room("書房"));
    rooms.put("bedroom", new Room("臥室"));

    setExits("outside", new HashMap<String, String>() {
      {
        put("east", "lobby");
        put("south", "study");
        put("west", "pub");
      }

    });
    setExits("lobby", new HashMap<String, String>() {
      {
        put("west", "outside");
        put("up", "pub");
      }
    });
    setExits("pub", new HashMap<String, String>() {
      {
        put("east", "outside");
        put("down", "lobby");
      }
    });
    setExits("study", new HashMap<String, String>() {
      {
        put("north", "outside");
        put("east", "bedroom");
      }
    });
    setExits("bedroom", new HashMap<String, String>() {
      {
        put("west", "study");
      }
    });
    setExits("bedroom", new HashMap<String, String>() {
      {
        put("west", "study");
      }
    });

    currentRoom = rooms.get("outside");
  }

  public void printWelcome() {
    System.out.println();
    System.out.println("歡迎來到城堡!");
    System.out.println("這是一個超級無聊的遊戲。");
    System.out.println("如果需要幫助，請輸入 'help'");

    showPrompt();
  }

  public void showPrompt() {
    System.out.println();
    System.out.println("現在你在" + currentRoom);
    System.out.print("出口有: ");
    System.out.println(currentRoom.getExitDesc());
    System.out.println();
  }

  public void goRoom(String direction) {
    Room nextRoom = currentRoom.getExit(direction);

    if (nextRoom == null) {
      System.out.println("那裏沒有門");
    } else {
      currentRoom = nextRoom;
      showPrompt();
    }
  }

  public void play() {
    Scanner in = new Scanner(System.in);
    while (true) {
      String line = in.nextLine();
      String[] words = line.split(" ");
      Handler handler = handlers.get(words[0]);
      String value = "";
      if (words.length > 1) {
        value = words[1];
      }
      if (handler != null) {
        handler.doCmd(value);
        if (handler.isBye()) {
          break;
        }
      }
    }
    in.close();
  }

  public static void main(String[] args) {
    Game game = new Game();
    game.printWelcome();
    game.play();
    System.out.println("感謝您的光臨。再見!");
  }
}
