package castle;

public class HandlerReset extends Handler {

  public HandlerReset(Game game) {
    super(game);
  }

  @Override
  public void doCmd(String word) {
    game.reset();
  }

}
