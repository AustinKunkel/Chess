package game;
import java.util.HashSet;
import java.util.Set;
public class CheckDiags {

    private static Set<Coordinate> canTarget;

    private static Piece breakPiece = null;

    private static Piece currPiece = null;
    public CheckDiags() {}

    /**
     * Iterates through each diagonal to check.
     * @param board
     * @param color
     * @param targeting
     * @param x
     * @param y
     * @return Set<Coordinate> of places the piece CAN target, but are not directly.
     */
    public static Set<Coordinate> checkDiags(Piece[][] board,
                                             PieceColor color,
                                             Set<Coordinate> targeting,
                                             int x,
                                             int y) {

        canTarget = new HashSet<>();

        bL(board, x, y, targeting, color,1);

        tL(board, x, y, targeting, color,1);

        tR(board, x, y, targeting, color,1);

        bR(board, x, y, targeting, color,1);

        return canTarget;
    }


    /**
     * Similar method to checkLines. starts from center and branches outward.
     * will then call again so that it can see if the next piece is a king.
     * helpful for determining moves in advance.
     * @param board
     * @param x
     * @param y
     * @param targeting
     * @param color
     * @param iteration
     */
    private static boolean bL(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
        int tempX = x - 1;
        int tempY = y - 1;

        while(tempX >= 0 && tempY >= 0) {
            currPiece = board[tempY][tempX];

            if(currPiece != null) {
                if(!sameColor(currPiece, color)) {
                    targeting.add(new Coordinate(tempX, tempY));

                    //if king is in check
                    if(currPiece.getType() == PieceType.KING) {
                        return true;
                    }

                    //so we can keep track of the piece that we need to update
                    if(iteration < 2) {
                        breakPiece = currPiece;
                    }
                } else if (iteration < 2) { // if it's the first iteration and the colors match
                    canTarget.add(new Coordinate(tempX, tempY)); // add the piece that is the same color to the canTarget set
                }
                break;
            }

            targeting.add(new Coordinate(tempX, tempY));
            tempX--;
            tempY--;
        }

        if(iteration < 2) {
            iteration++;
            if(bL(board, tempX, tempY, canTarget, color, iteration)) {// if king is in check
                if(breakPiece != null) {// and there was a piece before it
                    // update that piece's targeting
                    updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
                }
            }
        }

        breakPiece = null;

        return false;
    }

    /**
     * Similar method to checkLines. starts from center and branches outward.
     * will then call again so that it can see if the next piece is a king.
     * helpful for determining moves in advance.
     * @param board
     * @param x
     * @param y
     * @param targeting
     * @param color
     * @param iteration
     */
    private static boolean tL(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
        int tempX = x - 1;
        int tempY = y + 1;

        while(tempX >= 0 && tempY < board.length) {
            currPiece = board[tempY][tempX];

            if(currPiece != null) {
                if(!sameColor(currPiece, color)) {
                    targeting.add(new Coordinate(tempX, tempY));

                    // if king is in check
                    if(currPiece.getType() == PieceType.KING) {
                        return true;
                    }

                    // so we can keep track of the piece that we need to update
                    if(iteration < 2) {
                        breakPiece = currPiece;
                    }
                } else if (iteration < 2) { // if it's the first iteration and the colors match
                    canTarget.add(new Coordinate(tempX, tempY)); // add the piece that is the same color to the canTarget set
                }
                break;
            }

            targeting.add(new Coordinate(tempX, tempY));
            tempX--;
            tempY++;
        }

        if(iteration < 2) {
            iteration++;
            if(tL(board, tempX, tempY, canTarget, color, iteration)) {// if king is in check
                if(breakPiece != null) {// and there was a piece before it
                    // update that piece's targeting
                    updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
                }
            }
        }

        breakPiece = null;

        return false;
    }

    /**
     * Similar method to checkLines. starts from center and branches outward.
     * will then call again so that it can see if the next piece is a king.
     * helpful for determining moves in advance.
     * @param board
     * @param x
     * @param y
     * @param targeting
     * @param color
     * @param iteration
     */
    private static boolean tR(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
        int tempX = x + 1;
        int tempY = y + 1;

        while(tempX < board[0].length && tempY < board.length) {
            currPiece = board[tempY][tempX];

            if(currPiece != null) {
                if(!sameColor(currPiece, color)) {
                    targeting.add(new Coordinate(tempX, tempY));

                    //if king is in check
                    if(currPiece.getType() == PieceType.KING) {
                        return true;
                    }

                    //so we can keep track of the piece that we need to update
                    if(iteration < 2) {
                        breakPiece = currPiece;
                    }
                } else if (iteration < 2) { // if it's the first iteration and the colors match
                    canTarget.add(new Coordinate(tempX, tempY)); // add the piece that is the same color to the canTarget set
                }
                break;
            }

            targeting.add(new Coordinate(tempX, tempY));
            tempX++;
            tempY++;
        }

        if(iteration < 2) {
            iteration++;
            if(tR(board, tempX, tempY, canTarget, color, iteration)) {// if king is in check
                if(breakPiece != null) {// and there was a piece before it
                    // update that piece's targeting
                    updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
                }
            }
        }

        breakPiece = null;

        return false;
    }

    /**
     * Similar method to checkLines. starts from center and branches outward.
     * will then call again so that it can see if the next piece is a king.
     * helpful for determining moves in advance.
     * @param board
     * @param x
     * @param y
     * @param targeting
     * @param color
     * @param iteration
     * @return true if king is in check
     */
    private static boolean bR(Piece[][] board, int x, int y,  Set<Coordinate> targeting, PieceColor color, int iteration) {
        int tempX = x + 1;
        int tempY = y - 1;

        while(tempX < board[0].length && tempY >= 0) {
            currPiece = board[tempY][tempX];

            if(currPiece != null) {
                if(!sameColor(currPiece, color)) {
                    targeting.add(new Coordinate(tempX, tempY));

                    //if king is in check
                    if(currPiece.getType() == PieceType.KING) {
                        return true;
                    }

                    //so we can keep track of the piece that we need to change
                    if(iteration < 2) {
                        breakPiece = currPiece;
                    }
                } else if (iteration < 2) { // if it's the first iteration and the colors match
                    canTarget.add(new Coordinate(tempX, tempY)); // add the piece that is the same color to the canTarget set
                }
                break;
            }

            targeting.add(new Coordinate(tempX, tempY));
            tempX++;
            tempY--;
        }

        if(iteration < 2) {
            iteration++;
            if(bR(board, tempX, tempY, canTarget, color, iteration)) {// if king is in check
                if(breakPiece != null) {// and there was a piece before it
                    // update that piece's targeting
                    updatePieceTargetingIfCheck(breakPiece, currPiece.getTargeting());
                }
            }
        }

        breakPiece = null;

        return false;
    }
    public static boolean sameColor(Piece piece, PieceColor color) {
        return color == piece.getColor();
    }

    /**
     * keeps intersection of piece and targeting of piece
     * that can check king<br>
     * Only keeps the "pinning" portion of the piece that is being pinned<br>
     * that is, if there is a bishop blocking a king, it will only keep the bishops
     * direct line of sight
     * @param piece
     * @param targeting => Set of the piece that is targeting the king
     */
    private static void updatePieceTargetingIfCheck(Piece piece, Set<Coordinate> targeting) {
        Set<Coordinate> pieceTargeting = piece.getTargeting();
        Set<Coordinate> intersection = new HashSet<>();

        for(Coordinate coord : targeting) {
            if(pieceTargeting.contains(coord)) {
                intersection.add(coord);
            }
        }

        piece.setTargeting(intersection);
    }
}