/**
 * Created by Byungcharn Lee on 4/18/2017.
 */
import java.util.*;
public class PathFindingOnSquaredGrid
{
    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top

    // determine set of open/blocked cells using depth first search
    // does the system percolate?

    // does the system percolate vertically in a direct way?

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N-i-1, .5);
                else StdDraw.filledSquare(j, N-i-1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle




    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    // test client
    public static void main(String[] args)  {
        // boolean[][] open = StdArrayIO.readBoolean2D();

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(100, 0.6);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        //getting random amount of blocked cells into an array
        int blockedCells = 0;
        for (int i = 0; i < randomlyGenMatrix.length; i++) {
            for (int j = 0; j < randomlyGenMatrix.length; j++) {
                if (randomlyGenMatrix[i][j] == false) {
                    blockedCells++;
                }
            }
        }
        int noOfBlocks = 0;
        int[][] blocked = new int[blockedCells][2];
        //assigning the x and y coordinates for a specific blocked cell
        for (int i = 0; i < randomlyGenMatrix.length; i++) {
            for (int j = 0; j < randomlyGenMatrix.length; j++) {
                if (randomlyGenMatrix[i][j] == false) {
                    blocked[noOfBlocks][0] = i;
                    blocked[noOfBlocks][1] = j;
                    noOfBlocks++;
                }
            }

        }
        //calling the A star algorithm to help in pathfinding
        AStar a = new AStar();

        // Reading the coordinates for points A and B on the input squared grid.


        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();

        //  System.out.println("Enter method type as manhattan/euclidean/chebyshev");
        // String method = in.next();

        //loop through all 3 methods
        for (int methodNo = 0; methodNo < 3; methodNo++) {
            Stopwatch timerFlow = new Stopwatch();
            String method = "";
            if (methodNo==0){
                method = "manhattan";
            }else if(methodNo==1){
                method = "chebyshev";
            }else{
                method = "euclidian";
            }
            a.run(method, randomlyGenMatrix.length, randomlyGenMatrix.length, Ai, Aj, Bi, Bj, blocked);
            StdOut.println("Elapsed time = " + timerFlow.elapsedTime());


            if(methodNo==0){
                System.out.println("Proceed to Chebyshev : Y/N?");
            }else if(methodNo==1){
                System.out.println("Proceed to Euclidean : Y/N?");
            }
            String answer = in.next();
            if(answer.toLowerCase().equals("y")){

            }else{
                return;
            }
        }



        System.out.println("Manhattan : YELLOW. \n Chebyshev is GREEN. \n Euclidian is RED \n ");


    }

}
