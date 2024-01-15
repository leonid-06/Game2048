public class Tile {

    public int score = 0;

    public Tile() {
        int col1 = (int) (Math.random()*(dimension));
        int row1 = (int) (Math.random()*(dimension));
        int col2 = (int) (Math.random()*(dimension));
        int row2 = (int) (Math.random()*(dimension));
        while (col1==col2 && row2==row1){
            col2 = (int) (Math.random()*(dimension));
        }
        tiles[col1][row1] = generateValue();
        tiles[col2][row2] = generateValue();
    }

    public void reset(){
        score = 0;
        tiles = new int[dimension][dimension];
        int col1 = (int) (Math.random()*(dimension));
        int row1 = (int) (Math.random()*(dimension));
        int col2 = (int) (Math.random()*(dimension));
        int row2 = (int) (Math.random()*(dimension));
        while (col1==col2 && row2==row1){
            col2 = (int) (Math.random()*(dimension));
        }
        tiles[col1][row1] = generateValue();
        tiles[col2][row2] = generateValue();
    }

    private int[][] tiles = new int[4][4];

    private int generateValue(){
        return (Math.random()>0.9)?4:2;
    }

    public boolean generateNewTile(){
        int col = (int) (Math.random()*(dimension));
        int row = (int) (Math.random()*(dimension));

        int countOfZero = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j]==0) countOfZero++;
            }
        }

        do {
            if (tiles[col][row]==0) break;
            else {
                col = (int) (Math.random()*(dimension));
                row = (int) (Math.random()*(dimension));
            }
        } while (countOfZero>0);

        if (countOfZero!=0){
            tiles[col][row] = generateValue();
            return true;
        }
        return false;

    }

    public int dimension = 4;

    public int getTile(int row, int col){
        return tiles[row][col];
    }

    private void moveLeft(){
        for (int i = 0; i < dimension; i++) {
            int count = 0;
            int[] buffer = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j]!=0) buffer[count++]=tiles[i][j];
            }

            for (int j = 0; j < 4; j++) {
                if (tiles[i][j]==0) buffer[count++]=0;
                tiles[i][j] = buffer[j];
            }
        }
    }

    private void moveRight() {

        for (int i = 0; i < dimension; i++) {
            int countOfNonZero = 0;
            for (int j = 0; j < dimension; j++) if (tiles[i][j] != 0) countOfNonZero++;

            int[] result = new int[dimension];
            int startIndexFromNonZero = dimension - countOfNonZero;


            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0) {

                    result[startIndexFromNonZero++] = tiles[i][j];
                }
            }
            System.arraycopy(result, 0, tiles[i], 0, dimension);
        }

    }

    private void moveUp() {
        for (int col = 0; col < dimension; col++) {
            int[] result = new int[dimension];
            int count = 0;

            for (int row = 0; row < dimension; row++) {
                if (tiles[row][col] != 0) {
                    result[count++] = tiles[row][col];
                }
            }
            for (int row = 0; row < dimension; row++) {
                tiles[row][col] = result[row];
            }
        }
    }

    private void moveDown() {
        for (int col = 0; col < dimension; col++) {
            int[] result = new int[dimension];

            int countOfNonZero = 0;

            for (int row = 0; row < dimension; row++) {
                if (tiles[row][col]!=0) countOfNonZero++;
            }

            int startIndexFromNonZero = dimension - countOfNonZero;

            for (int row = 0; row < dimension; row++) {
                if (tiles[row][col] != 0) {
                    result[startIndexFromNonZero++]=tiles[row][col];
                }
            }
            for (int row = 0; row < dimension; row++) {
                tiles[row][col] = result[row];
            }
        }
    }

    private void mergeLeft(){
        for (int col = 0; col < dimension; col++) {
            for (int i = 0; i < dimension-1; i++) {
                if (tiles[col][i]==tiles[col][i+1] && tiles[col][i]!=0){
                    tiles[col][i] *= 2;
                    score += tiles[col][i];
                    tiles[col][i+1] = 0;
                }
            }
        }
    }
    private void mergeUp(){
        for (int col = 0; col < dimension; col++) {
            for (int row = 0; row < dimension-1; row++) {
                if (tiles[row][col]==tiles[row+1][col] && tiles[row][col]!=0){
                    tiles[row][col] *=2;
                    score += tiles[row][col];
                    tiles[row+1][col] = 0;
                }
            }
        }
    }

    private void mergeRight(){
        for (int col = 0; col < dimension; col++) {
            for (int i = dimension-1; i > 0; i--) {
                if (tiles[col][i]==tiles[col][i-1] && tiles[col][i]!=0){
                    tiles[col][i] *= 2;
                    score += tiles[col][i];
                    tiles[col][i-1] = 0;
                }
            }
        }
    }

    private void mergeDown(){
        for (int col = 0; col < dimension; col++) {
            for (int row = dimension-1; row > 0; row--) {
                if (tiles[row][col]==tiles[row-1][col] && tiles[row][col]!=0){
                    tiles[row][col] *=2;
                    score += tiles[row][col];
                    tiles[row-1][col] = 0;
                }
            }
        }
    }

    public void shiftLeft(){
        moveLeft();
        mergeLeft();
        moveLeft();
    }
    public void shiftRight(){
        moveRight();
        mergeRight();
        moveRight();
    }

    public void shiftUp(){
        moveUp();
        mergeUp();
        moveUp();
    }

    public void shiftDown(){
        moveDown();
        mergeDown();
        moveDown();
    }

    public boolean isNeedToMerge(){
        //left
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (tiles[i][j]==tiles[i][j+1] && tiles[i][j]!=0) return true;
            }
        }

        //right
        for (int i = 0; i < dimension; i++) {
            for (int j = dimension-1; j > 0; j--) {
                if (tiles[i][j]==tiles[i][j-1] && tiles[i][j]!=0) return true;
            }
        }

        // top
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (tiles[j][i]==tiles[j+1][i] && tiles[j][i]!=0) return true;
            }
        }

        // down
        for (int i = 0; i < dimension; i++) {
            for (int j = dimension-1; j > 0; j--) {
                if (tiles[j][i]==tiles[j-1][i] && tiles[j][i]!=0) return true;
            }
        }

        return false;
    }

}
