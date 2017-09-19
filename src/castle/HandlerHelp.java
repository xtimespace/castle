package castle;

public class HandlerHelp extends Handler {

  public HandlerHelp(Game game) {
    super(game);
  }

  @Override
  public void doCmd(String cmd) {
    System.out.print("迷路了嗎？你可以做的命令有: " + game.getCmds());
    System.out.println("\t如: go east");
  }
}
