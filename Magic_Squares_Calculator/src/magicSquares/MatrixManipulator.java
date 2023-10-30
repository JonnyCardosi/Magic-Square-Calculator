
package magicSquares ;

/**
 * Provides function to create an MxN augmented matrix 
 * Provides function to convert given matrix to RREF 
 * Provides function to convert given RREF matrix to VPF
 *
 * @author Jonny Cardosi
 *
 * @version 1.0.1 2022-11-15 Initial implementation
 */
public class MatrixManipulator
    {

    /**
     * Creates the corresponding augmented matrix used to solve an NxN magic square
     *
     * @param matrixDimension
     *     the dimensions of the desired magic square (NxN)
     *
     * @return the solutions for an NxN normal magic square
     */
    public static double[][] createAugmentedMatrix( int matrixDimension )
        {
        int magicConstant = ( matrixDimension * ( (int) ( Math.pow( matrixDimension, 2 ) ) + 1 ) ) / 2 ;
        int numOfVars = (int) ( Math.pow( matrixDimension, 2 ) ) ;
        double[][] augmentedMatrix = new double[ ( 2 * matrixDimension ) + 2 ][ numOfVars + 1 ] ;

        // adds the row equations to the augmented matrix
        for ( int i = 0 ; i < matrixDimension ; i++ )
            {
            for ( int j = 0 ; j < matrixDimension ; j++ )
                {
                augmentedMatrix[ i ][ ( matrixDimension * i ) + j ] = 1 ;

                }

            }

        // adds the column equations to the augmented matrix
        for ( int i = 0 ; i < matrixDimension ; i++ )
            {
            for ( int j = 0 ; j < matrixDimension ; j++ )
                {
                augmentedMatrix[ i + matrixDimension ][ ( matrixDimension * j ) + i ] = 1 ;

                }

            }

        // adds the main-diagonal equation to the augmented matrix
        for ( int i = 0 ; i < matrixDimension ; i++ )
            {
            augmentedMatrix[ 2 * matrixDimension ][ i * ( matrixDimension + 1 ) ] = 1 ;

            }

        // adds the wrong-diagonal equation to the augmented matrix
        for ( int i = 1 ; i <= matrixDimension ; i++ )
            {
            augmentedMatrix[ ( 2 * matrixDimension ) + 1 ][ i * ( matrixDimension - 1 ) ] = 1 ;

            }

        // adds the magic-constant to the end of each equation in the augmented matrix
        for ( int i = 0 ; i < augmentedMatrix.length ; i++ )
            {
            augmentedMatrix[ i ][ numOfVars ] = magicConstant ;

            }

        return augmentedMatrix ;

        }


    /**
     * Converts given augmented matrix to RREF
     *
     * @param givenMatrix
     *     unsorted matrix
     * 
     * @return RREF of given matrix
     */
    // assumes valid augmented matrix passed
    public static double[][] RREF( double[][] givenMatrix )
        {
        int lead = 0 ;
        int rowCount = givenMatrix.length ;
        int columnCount = givenMatrix[ 0 ].length ;
        
        /*
         * Code moves roughly along the main diagonal of matrix, making each column
         * all 0s besides the pivot, which is made to be 1. The RREF matrix is rounded
         * after its fully reduced in case of floating-point imperfections, which cannot 
         * be done in general, but works for the purposes of this program.
         */
        for ( int i = 0 ; i < rowCount ; i++ )
            {
            if ( columnCount <= lead )
                {
                for ( int m = 0 ; m < rowCount ; m++ )
                    {
                    for ( int n = 0 ; n < columnCount ; n++ )
                        {
                        givenMatrix[ m ][ n ] = Math.round( givenMatrix[ m ][ n ] ) ;

                        }

                    }

                return givenMatrix ;

                }

            int j = i ;
            while ( givenMatrix[ j ][ lead ] == 0 )
                {
                j++ ;
                if ( rowCount == j )
                    {
                    j = i ;
                    lead++ ;
                    if ( columnCount == lead )
                        {
                        for ( int m = 0 ; m < rowCount ; m++ )
                            {
                            for ( int n = 0 ; n < columnCount ; n++ )
                                {
                                givenMatrix[ m ][ n ] = Math.round( givenMatrix[ m ][ n ] ) ;

                                }

                            }

                        return givenMatrix ;

                        }

                    }

                }

            if ( j != i )
                {
                givenMatrix = rowSwap( givenMatrix, j, i ) ;

                }

            givenMatrix = rowDivide( givenMatrix, i, givenMatrix[ i ][ lead ] ) ;

            for ( int k = 0 ; k < rowCount ; k++ )
                {
                if ( k != i )
                    {
                    givenMatrix = rowScaleSubtract( givenMatrix, i, k, givenMatrix[ k ][ lead ] ) ;

                    }

                }

            lead++ ;

            }

        for ( int m = 0 ; m < rowCount ; m++ )
            {
            for ( int n = 0 ; n < columnCount ; n++ )
                {
                givenMatrix[ m ][ n ] = Math.round( givenMatrix[ m ][ n ] ) ;

                }

            }

        return givenMatrix ;

        }


    /**
     * Swaps the rows in question for the given matrix
     *
     * @param givenMatrix
     *     given matrix
     * @param row1Index
     *     row to be swapped
     * @param row2Index
     *     compliment row to be swapped
     * 
     * @return row-swapped givenMatrix
     */
    // assumes valid matrix passed
    public static double[][] rowSwap( double[][] givenMatrix,
                                      int row1Index,
                                      int row2Index )
        {
        // temporary value to remember number before it's overwritten
        double tempValue = 0 ;

        for ( int i = 0 ; i < givenMatrix[ 0 ].length ; i++ )
            {
            tempValue = givenMatrix[ row1Index ][ i ] ;
            givenMatrix[ row1Index ][ i ] = givenMatrix[ row2Index ][ i ] ;
            givenMatrix[ row2Index ][ i ] = tempValue ;

            }

        return givenMatrix ;

        }


    /**
     * Divides the row in question by the given scalar
     *
     * @param givenMatrix
     *     given matrix
     * @param rowIndex
     *     index of row
     * @param scalar
     *     value to divide row by
     * 
     * @return scaled givenMatrix
     */
    // assumes valid matrix passed
    public static double[][] rowDivide( double[][] givenMatrix,
                                        int rowIndex,
                                        double scalar )
        {

        if ( scalar == 1 )
            {
            return givenMatrix ;

            }

        for ( int i = 0 ; i < givenMatrix[ 0 ].length ; i++ )
            {
            givenMatrix[ rowIndex ][ i ] /= scalar ;

            }

        return givenMatrix ;

        }


    /**
     * Adds scaled version of one row to another row
     *
     * @param givenMatrix
     *     given matrix
     * @param rowSubtractorIndex
     *     index of row to subtract with
     * @param rowSubtractedIndex
     *     index of row to be subtracted from
     * @param scalar
     *     value to scale row by
     * 
     * @return givenMatrix with necessary row subtractions
     */
    // assumes valid matrix passed
    public static double[][] rowScaleSubtract( double[][] givenMatrix,
                                               int rowSubtractorIndex,
                                               int rowSubtractedIndex,
                                               double scalar )
        {
        int numOfColumns = givenMatrix[ 0 ].length ;

        for ( int i = 0 ; i < numOfColumns ; i++ )
            {
            givenMatrix[ rowSubtractedIndex ][ i ] -= ( scalar * givenMatrix[ rowSubtractorIndex ][ i ] ) ;

            }

        return givenMatrix ;

        }


    /**
     * Converts given RREF augmented matrix to VPF
     *
     * @param givenRREFMatrix
     *     RREF augmented matrix
     * 
     * @return VPF of given RREF matrix
     */
    // assumes valid RREF augmented matrix passed
    public static double[][] VPF( double[][] givenRREFMatrix )
        {
        int rowCount = givenRREFMatrix.length ;
        int columnCount = givenRREFMatrix[ 0 ].length ;
        int numOfVars = ( columnCount - 1 ) ;
        
        /*
         * Keeps track of all constants and free variables, initially assumes all 
         * variables are free variables, pivot columns are represented by all zeros
         */
        double[][] freeVarsAndConstants = new double[ numOfVars ][ columnCount ] ;
        for ( int i = 0 ; i < numOfVars ; i++ )
            {
            freeVarsAndConstants[ i ][ i ] = 1 ;

            }

        /*
         * Code goes through given matrix, decrementing the number of free variables
         * in light of any pivot variables found and adding values to the corresponding
         * spots in the free-variable columns in the freeVars array accordingly.
         */
        for ( int j = 0 ; j < rowCount ; j++ )
            {
            boolean pivFound = false ;
            int pivIndex = -1;
            for ( int k = 0 ; k < numOfVars ; k++ )
                {
                if ( givenRREFMatrix[ j ][ k ] != 0 )
                    {
                    if ( pivFound ) // if piv found in row, then all non-zero nums after are free var values
                        {
                        freeVarsAndConstants[ pivIndex ][ k ] -= givenRREFMatrix[ j ][ k ] ;
                        continue ;

                        }

                    freeVarsAndConstants[ k ][ k ] -= 1 ;
                    pivIndex = k ;
                    pivFound = true ;

                    }

                }
            
            if ( pivFound ) // adds constant from row if necessary
                {
                freeVarsAndConstants[ pivIndex ][ numOfVars ] = givenRREFMatrix[ j ][ numOfVars ] ;
                }

            }

        return freeVarsAndConstants ;

        }
    

    }
// end class MatrixConverter