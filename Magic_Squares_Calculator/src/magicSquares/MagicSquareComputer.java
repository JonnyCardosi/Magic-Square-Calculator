
package magicSquares ;

import java.util.ArrayList ;

/**
 * Intermediate support class that aids in checking or producing magic square solutions
 * 
 * @author Jonny Cardosi
 *
 * @version 1.0.0 2022-11-12 Initial implementation
 */
public class MagicSquareComputer
    {

    /**
     * Plugs in values of givenMatrix into appropriate equations to check if said
     * matrix is a solution for the corresponding NxN normal magic square
     * 
     * @param givenMatrix 
     *      matrix to be checked
     *
     * @return true if it's a solution, false if not
     */
    // assumes valid matrix passed - i.e. square matrix, no duplicate values, 1 <= values <= N^2
    public static boolean solutionChecker( int[][] givenMatrix )
        {
        int magicConstant = ( givenMatrix.length * ( (int) ( Math.pow( givenMatrix.length, 2 ) ) + 1 ) ) / 2 ;

        // checks if the diagonals of matrix are equal to the magic constant
        int mainDiagValue = 0 ;
        int wrongDiagValue = 0 ;
        int i ;
        for ( i = 0 ; i < givenMatrix.length ; i++ )
            {
            mainDiagValue += givenMatrix[ i ][ i ] ;
            wrongDiagValue += givenMatrix[ i ][ ( givenMatrix.length - 1 ) - i ] ;

            }

        if ( ( mainDiagValue != magicConstant ) || ( wrongDiagValue != magicConstant ) )
            {
            return false ;

            }

        // checks if the sum of each individual row/column of the matrix
        // is equal to the magic constant
        for ( i = 0 ; i < givenMatrix.length ; i++ )
            {
            int rowValue = 0 ;
            int columnValue = 0 ;
            int j ;
            for ( j = 0 ; j < givenMatrix.length ; j++ )
                {
                rowValue += givenMatrix[ i ][ j ] ;
                columnValue += givenMatrix[ j ][ i ] ;

                }

            if ( ( rowValue != magicConstant ) || ( columnValue != magicConstant ) )
                {
                return false ;

                }

            }

        // if all equation-checks pass, then the given matrix is a solution
        return true ;

        }


    /**
     * Computes all possible solutions for an NxN normal magic square and then
     * determines which of them are actual solutions
     *
     * @param matrixDimension
     *     the dimensions of the desired magic square (NxN)
     *
     */
    public static void solutionComputer( int matrixDimension )
        {
        double[][] magicMatrix = MatrixManipulator.createAugmentedMatrix( matrixDimension ) ;
        magicMatrix = MatrixManipulator.RREF( magicMatrix ) ;
        magicMatrix = MatrixManipulator.VPF( magicMatrix ) ;
        
        int rowCount = magicMatrix.length ;
        int colCount = magicMatrix[ 0 ].length ; 
        int numOfVars = rowCount ;
        
        int[][] intMagicMatrix = new int[ rowCount ][ colCount ] ;
        for ( int a1 = 0 ; a1 < rowCount ; a1++ )
            {
            for ( int a2 = 0 ; a2 < colCount ; a2++ )
                {
                intMagicMatrix[ a1 ][ a2 ] = ( int )( Math.round( magicMatrix[ a1 ][ a2 ] ) ) ;
                }
            }
        
        // finds and stores row index of free variables
        ArrayList<Integer> freeVarRows = new ArrayList<>() ;
        for ( int i = 0 ; i < rowCount ; i++ )
            {
            if ( intMagicMatrix[ i ][ i ] == 1 ) 
                {
                freeVarRows.add(i) ;
                }
            }
        int numFreeVars = freeVarRows.size() ;
           
        // permutations will start with all ones in every spot
        int[] perms = new int[ numFreeVars ] ;
        for ( int k = 0 ; k < numFreeVars ; k++ )
            {
            perms[ k ] = 1 ;
            }
        
        /*
         * For every possible permutation of the free variables, the code goes 
         * through the VPF matrix and checks if the possible solution contains
         * distinct numbers that are all within the range 1 - matrixDimension^2.
         */
        int r = 0 ;
        int[] spotsTaken = new int[ rowCount ] ;
        int rowValue = 0 ;
        boolean isSolution = true ;    
        boolean isSpotTaken = false ;
        int falseCounter = 0 ;
        int trueCounter = 0 ;
        // goes until all iterations of perms have been checked
        while ( true ) 
            {
            spotsTaken = new int[ rowCount ] ;
            isSolution = true ;
            isSpotTaken = false ;
            // goes through each row of VPF matrix for each iteration of perms
            for ( int m = 0 ; m < rowCount ; m++ ) 
                {
                rowValue = 0 ;
                // calculates value of current row
                for ( int n = 0 ; n < numFreeVars ; n++ )
                    { 
                    rowValue = rowValue + ( ( perms[ n ] ) * ( intMagicMatrix[ m ][ freeVarRows.get( n ) ] ) ) ;
                    }
                rowValue = rowValue + intMagicMatrix[ m ][ numOfVars ] ;
                
                // checks if row value is within range (1 - matrixDimension^2)
                if ( ( rowValue < 1 ) || ( rowValue > numOfVars ) )
                    {
                    isSolution = false ;
                    break ;
                    }
                
                // checks if row value is a duplicate of any previous row values
                for ( int o = 0 ; o < rowCount ; o++ )
                    {
                    if ( spotsTaken[ o ] == rowValue )
                        {
                        isSpotTaken = true ;
                        break ;
                        }
                    }
                if ( !isSpotTaken )
                    {
                    spotsTaken[ m ] = rowValue ;
                    }
                else 
                    {
                    isSolution = false ;
                    break ;
                    }
                
                }
            
            if ( isSolution ) // prints solutions
                {
                trueCounter++ ;
                for ( int t = 0 ; t < matrixDimension ; t++ )
                    {
                    System.out.printf( "[" ) ;
                    for ( int u = ( t * matrixDimension ) ; u < ( t * matrixDimension ) + matrixDimension ; u++ )
                        {
                        if ( u != t * (  matrixDimension ) + matrixDimension - 1 )
                            {
                            System.out.printf( "%d, ", spotsTaken[ u ] ) ;
                            }
                        else 
                            {
                            System.out.printf( "%d", spotsTaken[ u ] ) ;
                            }
                        }
                    System.out.printf( "]%n" ) ;
                    }
                System.out.printf( "%n" ) ;
                }
            else 
                {
                falseCounter++ ;
                }
                      
            // increments first column of perms
            perms[r]++ ;
            if ( perms[r] > numOfVars ) // if goes over number limit, need to carry over to next column 
                {
                do 
                    {
                    r++ ;
                    if ( r == numFreeVars ) // stops after all combinations done
                        {
                        System.out.printf( "%nFalseCount: %d", falseCounter ) ;
                        System.out.printf( "%nTrueCount: %d", trueCounter ) ;
                        return ;
                        }
                    perms[r]++ ;
                    } while ( perms[r] > numOfVars ) ; // accounts for further carrying
                while ( r > 0 ) // resets non-head columns
                    {
                    r-- ;
                    perms[r] = 1 ;
                    }
                }
            }

        }
    
    }
// end class MagicSquareComputer