package fourwins.game;

/**
 * This enum can be used to express the result of a game.
 */
public enum GameResult {
  /**
   * If the player has won.
   */
  WON("""

    WWWWWWWW                           WWWWWWWW     OOOOOOOOO     NNNNNNNN        NNNNNNNN
    W::::::W                           W::::::W   OO:::::::::OO   N:::::::N       N::::::N
    W::::::W                           W::::::W OO:::::::::::::OO N::::::::N      N::::::N
    W::::::W                           W::::::WO:::::::OOO:::::::ON:::::::::N     N::::::N
     W:::::W           WWWWW           W:::::W O::::::O   O::::::ON::::::::::N    N::::::N
      W:::::W         W:::::W         W:::::W  O:::::O     O:::::ON:::::::::::N   N::::::N
       W:::::W       W:::::::W       W:::::W   O:::::O     O:::::ON:::::::N::::N  N::::::N
        W:::::W     W:::::::::W     W:::::W    O:::::O     O:::::ON::::::N N::::N N::::::N
         W:::::W   W:::::W:::::W   W:::::W     O:::::O     O:::::ON::::::N  N::::N:::::::N
          W:::::W W:::::W W:::::W W:::::W      O:::::O     O:::::ON::::::N   N:::::::::::N
           W:::::W:::::W   W:::::W:::::W       O:::::O     O:::::ON::::::N    N::::::::::N
            W:::::::::W     W:::::::::W        O::::::O   O::::::ON::::::N     N:::::::::N
             W:::::::W       W:::::::W         O:::::::OOO:::::::ON::::::N      N::::::::N
              W:::::W         W:::::W           OO:::::::::::::OO N::::::N       N:::::::N
               W:::W           W:::W              OO:::::::::OO   N::::::N        N::::::N
                WWW             WWW                 OOOOOOOOO     NNNNNNNN         NNNNNNN

    """),

  /**
   * If the player has lost.
   */
  LOST("""

    LLLLLLLLLLL                                                        tttt
    L:::::::::L                                                     ttt:::t
    L:::::::::L                                                     t:::::t
    LL:::::::LL                                                     t:::::t
      L:::::L                  ooooooooooo       ssssssssss   ttttttt:::::ttttttt
      L:::::L                oo:::::::::::oo   ss::::::::::s  t:::::::::::::::::t
      L:::::L               o:::::::::::::::oss:::::::::::::s t:::::::::::::::::t
      L:::::L               o:::::ooooo:::::os::::::ssss:::::stttttt:::::::tttttt
      L:::::L               o::::o     o::::o s:::::s  ssssss       t:::::t
      L:::::L               o::::o     o::::o   s::::::s            t:::::t
      L:::::L               o::::o     o::::o      s::::::s         t:::::t
      L:::::L         LLLLLLo::::o     o::::ossssss   s:::::s       t:::::t    tttttt
    LL:::::::LLLLLLLLL:::::Lo:::::ooooo:::::os:::::ssss::::::s      t::::::tttt:::::t
    L::::::::::::::::::::::Lo:::::::::::::::os::::::::::::::s       tt::::::::::::::t
    L::::::::::::::::::::::L oo:::::::::::oo  s:::::::::::ss          tt:::::::::::tt
    LLLLLLLLLLLLLLLLLLLLLLLL   ooooooooooo     sssssssssss              ttttttttttt

    """),

  /**
   * If it is a draw.
   */
  DRAW("""

    DDDDDDDDDDDDD
    D::::::::::::DDD
    D:::::::::::::::DD
    DDD:::::DDDDD:::::D
      D:::::D    D:::::D rrrrr   rrrrrrrrr   aaaaaaaaaaaaawwwwwww           wwwww           wwwwwww
      D:::::D     D:::::Dr::::rrr:::::::::r  a::::::::::::aw:::::w         w:::::w         w:::::w
      D:::::D     D:::::Dr:::::::::::::::::r aaaaaaaaa:::::aw:::::w       w:::::::w       w:::::w
      D:::::D     D:::::Drr::::::rrrrr::::::r         a::::a w:::::w     w:::::::::w     w:::::w
      D:::::D     D:::::D r:::::r     r:::::r  aaaaaaa:::::a  w:::::w   w:::::w:::::w   w:::::w
      D:::::D     D:::::D r:::::r     rrrrrrraa::::::::::::a   w:::::w w:::::w w:::::w w:::::w
      D:::::D     D:::::D r:::::r           a::::aaaa::::::a    w:::::w:::::w   w:::::w:::::w
      D:::::D    D:::::D  r:::::r          a::::a    a:::::a     w:::::::::w     w:::::::::w
    DDD:::::DDDDD:::::D   r:::::r          a::::a    a:::::a      w:::::::w       w:::::::w
    D:::::::::::::::DD    r:::::r          a:::::aaaa::::::a       w:::::w         w:::::w
    D::::::::::::DDD      r:::::r           a::::::::::aa:::a       w:::w           w:::w
    DDDDDDDDDDDDD         rrrrrrr            aaaaaaaaaa  aaaa        www             www

    """);

  /**
   * This variable holds the string seen above.
   * These can then be obtained through the method {@link GameResult#message()}.
   */
  private final String message;

  /**
   * Fills the enum values with messages.
   *
   * @param message which is to be set for the respective value.
   */
  GameResult(final String message) {
    this.message = message;
  }

  /**
   * Returns the stored value of the variable {@link GameResult#message}.
   *
   * @return message of the type as a {@link String}.
   */
  public String message() {
    return this.message;
  }
}