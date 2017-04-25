/**
 * Created by Byungcharn Lee on 4/18/2017.
 */
import java.util.*;
public class AStar {
    public  final int diagonal_Cost = 14;
    public  final int vertical_Horizontal_Cost = 10;

    class Cell{
        int heuristic = 0; //Heuristic cost
        int fValue = 0; //G+Heuristic
        int i, j;
        Cell parent;

        Cell(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }

    //Blocked cells are just null Cell values in grid
    Cell [][] grid = new Cell[5][5];

    PriorityQueue<Cell> open;

    boolean closed[][];
    int startI, startJ;
    int endI, endJ;

    public  void setBlocked(int i, int j){
        grid[i][j] = null;
    }

    public  void setStartPoint(int i, int j){
        startI = i;
        startJ = j;
    }

    public  void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }

    void calculateCost(Cell current, Cell t, int cost) {
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristic+cost;


        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.fValue){
            t.fValue = t_final_cost;
            t.parent = current;

            if(!inOpen)open.add(t);
        }
    }

    public  void AStar() {

        //add the start location to open list.
        open.add(grid[endI][endJ]);

        Cell current;

        while(true){
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true;

            if(current.equals(grid[startI][startJ])){
                return;
            }

            Cell t;
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                calculateCost(current, t, current.fValue+vertical_Horizontal_Cost);

                if(current.j-1>=0){
                    t = grid[current.i-1][current.j-1];
                    calculateCost(current, t, current.fValue+diagonal_Cost);
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    calculateCost(current, t, current.fValue+diagonal_Cost);
                }

            }

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                calculateCost(current, t, current.fValue+vertical_Horizontal_Cost);
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                calculateCost(current, t, current.fValue+vertical_Horizontal_Cost);
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                calculateCost(current, t, current.fValue+vertical_Horizontal_Cost);

                if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    calculateCost(current, t, current.fValue+diagonal_Cost);
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i+1][current.j+1];
                    calculateCost(current, t, current.fValue+diagonal_Cost);
                }

            }
        }
    }


    public  void run(String type, int x, int y, int si, int sj, int ei, int ej, int[][] blocked) {

        //Reset the Cell grid object
        grid = new Cell[x][y];
        //Reset the closed 2D array
        closed = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.fValue<c2.fValue?-1:c1.fValue>c2.fValue?1:0;
        });
        //Set start position
        setStartPoint(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part

        //Set End Location
        setEndCell(ei, ej);

        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){

                grid[i][j] = new Cell(i, j);

                if(type.equals("manhattan")){
                    grid[i][j].heuristic = Math.abs(i-startI)+Math.abs(j-startJ);//manhattan
                }else if(type.equals("chebyshev")){
                    grid[i][j].heuristic = Math.max(Math.abs(i - endI), Math.abs(j - endJ)); // chebyshev
                }else{
                    grid[i][j].heuristic = (int) Math.sqrt(((i - endI) ^ 2) + ((j - endJ) ^ 2)); // euclidean
                }
            }

//              System.out.println();

        }
        grid[si][sj].fValue = 0;

           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
           */
        for(int i=0;i<blocked.length;++i){
            setBlocked(blocked[i][0], blocked[i][1]);
        }

        //Display initial map

        System.out.println("Grid: ");

        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                if(i==si&&j==sj){
                    System.out.print("SO  "); //Source
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.square(j, x-i-1, .5);
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare(j, x-i-1, .5);
                }else if(i==ei && j==ej){
                    System.out.print("DE  ");  //Destination
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.square(j, x-i-1, .5);
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare(j, x-i-1, .5);
                }else if(grid[i][j]!=null){
                    System.out.printf("%-3d ", 0);
                    //StdDraw.setPenColor(StdDraw.BLACK);
                    //StdDraw.square(j, x-i-1, .5);

                }else{ System.out.print("BL  ");
                    //StdDraw.setPenColor(StdDraw.BLACK);
                    //StdDraw.filledSquare(j, x-i-1, .5);
                }
            }
            System.out.println();
        }
        System.out.println();
        AStar();
        System.out.println("\nScores for cells: ");



        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null){
                    System.out.printf("%-3d ", grid[i][j].fValue);

                }
                else{
                    System.out.print("BL  ");
                }
            }
            System.out.println();
        }
        System.out.println();
        int finalCost = 0;
        if(closed[endI][endJ]){

            //Stopwatch timerFlow = new Stopwatch();
            //Trace back the path
            Cell current = grid[startI][startJ];
            System.out.println("Type of pathfind: "+ type);

            System.out.println("Path: ");
            System.out.print(current);



            while(current.parent!=null){
                System.out.print(" -> "+current.parent);
                finalCost += current.fValue;
                current = current.parent;
                if(type.equals("manhattan")){
                    StdDraw.setPenColor(StdDraw.YELLOW);
                    //StdDraw.filledCircle(current.j,x-current.i-1, 0.5);
                    StdDraw.filledSquare(current.j,x-current.i-1,0.5);
                }else if(type.equals("chebyshev")){
                    StdDraw.setPenColor(StdDraw.GREEN);
                    StdDraw.filledCircle(current.j,x-current.i-1, 0.5);
                }else{
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledCircle(current.j,x-current.i-1, 0.2);
                }


            }
            System.out.println();

            //StdOut.println("Elapsed time = " + timerFlow.elapsedTime());

        }else System.out.println("No possible path");

        System.out.println("Final Cost: "+finalCost);

    }
}
