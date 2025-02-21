package problems;

import java.util.Arrays;
import java.util.Random;

public class TSP implements Problem <int[]>{



    public static void main(String[] args) {
        int n = 4;
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                arr[i][j] = i + j;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.printf(arr[i][j] + " ");
            System.out.println();
        }

    }



    public int generateNewState(int[] current){
        int column = nextInt(N);
        int currentRow = current[column];
        int newRow;
        do{
            newCity = nextInt(N);
        }while(newRow==currentRow);
        int[] newState = Arrays.copyOf(current, current.length);
        newState[column] = newRow;
        return newState;
    }

    public double cost(int[] state) {


    }

    public int[] getInitState(){
        Random rand = new Random();
        int[] state = new int[N];
        for(int i=0; i<N; i++){
            state[i] = rand.nextInt(N);
        }
        return state;
    }

    public void printState(int[] state){
        for(int row=0; row<N; row++){
            for(int col=0; col<N; col++){
                if(state[col] == row){
                    System.out.print(" Q ");
                }else{
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }
}
