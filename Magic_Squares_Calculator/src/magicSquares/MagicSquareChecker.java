
package magicSquares ;

import java.util.Arrays ;
import java.util.Scanner ;

/**
 * Program can determine if a given matrix is a solution for a corresponding NxN normal magic square
 * Program can also determine all of the solutions for an NxN normal magic square
 * 
 * @author Jonny Cardosi
 *
 * @version 1.0.0 2022-11-12 Initial implementation
 */
public class MagicSquareChecker
    {

    /**
     * Prompts user for input and computes NxN magic square solution possibilities accordingly
     *
     * @param args 
     *      main method
     */
    public static void main( String[] args )
        {
        boolean badInput = false ;
        String userInputS = null ;
        int userInputI = 0 ;
        
        try ( Scanner inputReader = new Scanner ( System.in ) ) 
            {
            System.out.printf( "Hello! What would you like to do?%n" ) ;
            System.out.printf( "(1) Determine if a given matrix is a solution for an NxN normal magic square%n" ) ;
            System.out.printf( "(2) Determine all of the solutions for an NxN normal magic square" ) ;
                
            // reads in and checks if given input is valid
            do
                {
                if ( !badInput ) 
                    {
                    System.out.printf( "%n%nEnter '1' for the first option, or '2' for the second option: " ) ;
                    userInputS = inputReader.next() ;
                    }
                else 
                    {
                    System.out.printf( "%n%n%nRe-enter your request here: " ) ;
                    userInputS = inputReader.next() ;
                    }
                
                try
                    {
                    userInputI = Integer.parseInt( userInputS ) ;

                    }

                catch ( Exception e1 )
                    {
                    System.out.printf( "Error - Please Enter A Numerical Integer Only (1 or 2)" ) ;
                    badInput = true ;
                    continue ;

                    }
                
                if ( ( userInputI != 1 ) && ( userInputI != 2 ) )
                    {
                    System.out.printf( "Error - Please Enter Either 1 or 2" ) ;
                    badInput = true ;
                    continue ;
                    }
                
                badInput = false ;

                } while ( badInput ) ;
            
            
            
            // first option chosen
            if ( userInputI == 1 )
                {
                // reads in and checks if input is valid
                do
                    {
                    if ( !badInput ) 
                        {
                        System.out.printf( "Enter the dimension of your matrix (e.g. enter 3 for a 3x3 matrix): " ) ;
                        userInputS = inputReader.next() ;
                        }
                    else 
                        {
                        System.out.printf( "%n%n%nRe-enter the dimension here: " ) ;
                        userInputS = inputReader.next() ;
                        }
                    
                    try
                        {
                        userInputI = Integer.parseInt( userInputS ) ;
                        if ( userInputI <= 0 )
                            {
                            System.out.printf( "Error - Cannot Create A %dx%d Matrix, Please Enter Positive Integers Only", userInputI, userInputI ) ;
                            badInput = true ;
                            continue ;
                            }
                        }

                    catch ( Exception e2 )
                        {
                        System.out.printf( "Error - Please Enter A Numerical Integer Only" ) ;
                        badInput = true ;
                        continue ;

                        }
                    
                    badInput = false ;

                    } while ( badInput ) ;
                
                // magic squares of 1x1 and 2x2 dimensions are simple/special, no further input needed
                if ( userInputI == 1 )
                    {
                    System.out.printf( "A 1x1 normal magic square has only 1 solution: [1]" ) ;
                    inputReader.close() ;
                    return ;
                    }
                
                if ( userInputI == 2 )
                    {
                    System.out.printf( "A 2x2 normal magic square has no solutions." ) ;
                    inputReader.close() ;
                    return ;
                    }

                // if chosen dimensions are 3x3 or higher, actual input is needed 
                int numOfVars = ( int ) ( Math.pow( userInputI, 2 ) ) ;
                int [][] matrix = new int[userInputI][userInputI] ;
                int [] nums = new int[ numOfVars ] ;
                // reads in, stores, and checks validity of given matrix
                do
                    {
                    if ( !badInput ) 
                        {
                        System.out.printf("v-- Please use the following format --v%n") ;
                        System.out.printf("[1  2  3]%n") ;
                        System.out.printf("[4  5  6] ---> Input As: [1,2,3;4,5,6;7,8,9] - No Spaces %n") ;
                        System.out.printf("[7  8  9]") ; 
                        System.out.printf("%nEnter your matrix here: ") ;
                        inputReader.nextLine() ;
                        userInputS = inputReader.nextLine() ;
                        }
                    else 
                        {
                        System.out.printf("%nv-- Please use the following format --v%n") ;
                        System.out.printf("[1  2  3]%n") ;
                        System.out.printf("[4  5  6] ---> Input As: [1,2,3;4,5,6;7,8,9] - No Spaces %n") ;
                        System.out.printf("[7  8  9]") ; 
                        System.out.printf("%n%n%nRe-enter your matrix here: ") ;
                        userInputS = inputReader.nextLine() ;
                        }
                    
                    badInput = false ;

                    // checks if input is null or blank
                    if ( userInputS.length() == 0 )
                        {
                        System.out.printf( "Error - Invalid Format or Non %dx%d Matrix Given", userInputI, userInputI ) ;
                        badInput = true ;
                        continue ;
                        }

                    // checks if given input has numbers and symbols in expected amounts and order
                    int numCommas = 0 ;
                    int numColons = 0 ;
                    int numOpenBrackets = 0 ;
                    int numClosedBrackets = 0 ;
                    for ( int j = 0 ; j < userInputS.length() ; j++ )
                        {
                        char currentCharacter = userInputS.charAt( j ) ;
                        
                        if ( currentCharacter == ',' )
                            {
                            numCommas++ ;
                            continue ;
                            }
                        
                        if ( currentCharacter == ';' )
                            {
                            numColons++ ;
                            continue ;
                            }
                        
                        if ( currentCharacter == '[')
                            {
                            numOpenBrackets++ ;
                            continue ;
                            }
                        
                        if ( currentCharacter == ']')
                            {
                            numClosedBrackets++ ;
                            continue ;
                            }
                        
                        if ( !Character.isDigit( currentCharacter ) )
                            {
                            badInput = true ;
                            break ;
                            }
                        
                        }
                    // unexpected number of certain symbols or invalid symbols given
                    if ( ( badInput ) || ( numCommas != ( numOfVars - 1 - ( userInputI - 1 ) ) ) || ( numOpenBrackets != 1 ) || ( numClosedBrackets != 1 ) || ( numColons != ( userInputI - 1 ) ) )
                        {
                        System.out.printf( "Error - Invalid Format or Non %dx%d Matrix Given", userInputI, userInputI ) ;
                        badInput = true ;
                        continue ;
                        }
                    
                    // unexpected ordering of brackets
                    char currentCharacter = userInputS.charAt( 0 );
                    if ( currentCharacter != '[')
                        {
                        System.out.printf( "Error - Invalid Format or Non %dx%d Matrix Given", userInputI, userInputI ) ;
                        badInput = true ;
                        continue ;
                        }
                    currentCharacter = userInputS.charAt( userInputS.length() - 1 );
                    if ( currentCharacter != ']')
                        {
                        System.out.printf( "Error - Invalid Format or Non %dx%d Matrix Given", userInputI, userInputI ) ;
                        badInput = true ;
                        continue ;
                        }
                    // confirms given matrix isn't empty
                    currentCharacter = userInputS.charAt( 1 );
                    if ( !Character.isDigit( currentCharacter ) )
                        {
                        System.out.printf( "Error - Invalid Format or Non %dx%d Matrix Given", userInputI, userInputI ) ;
                        badInput = true ;
                        continue ;
                        }
                    // reads in and stores values of given matrix for later use, also checks for ordering and number errors
                    int nextFreeSpot = 0 ;
                    boolean lastCharIsComma = false ;
                    boolean lastCharIsColon = false ;
                    int numOfCommas = 0 ;
                    boolean validMatrix = true ;
                    for ( int i = 1 ; i < userInputS.length() - 1 ; i++ )
                        {
                        currentCharacter = userInputS.charAt( i ) ;
                        
                        if ( ( ( currentCharacter == ',' ) || ( currentCharacter == ';' ) ) && ( ( lastCharIsComma ) || ( lastCharIsColon ) ) )
                            {
                            nums = new int[ numOfVars ] ;
                            badInput = true ;
                            validMatrix = false ;
                            break ;
                            }
                        
                        if ( currentCharacter == ',' )
                            {
                            if ( numOfCommas == ( userInputI - 1 ) ) 
                                {
                                nums = new int[ numOfVars ] ;
                                badInput = true ; 
                                validMatrix = false ;
                                break ;
                                }
                            numOfCommas++ ;
                            lastCharIsComma = true ;
                            continue ;
                            }
                        
                        if ( currentCharacter == ';' )
                            {
                            numOfCommas = 0 ;
                            lastCharIsColon = true ;
                            }
                        // enables recognition of multi-digit numbers in string 
                        if ( Character.isDigit( currentCharacter ) )
                            {
                            int fullOperand = 0 ;
                            while ( Character.isDigit( currentCharacter ) )
                                {
                                fullOperand = ( fullOperand * 10 ) + ( currentCharacter - '0' ) ;
                                i++ ;
                                if ( i < userInputS.length() - 1 )
                                    {
                                    currentCharacter = userInputS.charAt( i ) ;
                                    continue ;

                                    }
                                
                                break ;
                                
                                }
                            // checks if matrix numbers are valid
                            if ( ( fullOperand < 1 ) || ( fullOperand > numOfVars ) )
                                {
                                System.out.printf( "Error - A Normal Magic Square Can Only Have Values From 1 - N^2 (N being its dimension)" ) ;
                                nums = new int[ numOfVars ] ;
                                badInput = true ;
                                validMatrix = false ;
                                break ;
                                }
                            boolean duplicates = false ;
                            for ( int o = 0 ; o < nums.length ; o++ )
                                {
                                if ( nums[o] == fullOperand )
                                    {
                                    duplicates = true ;
                                    break ;
                                    }
                                }
                            if ( duplicates )
                                {
                                System.out.printf( "Error - A Normal Magic Square Cannot Have Duplicate Values" ) ;
                                nums = new int[ numOfVars ] ;
                                badInput = true ;
                                validMatrix = false ;
                                break ;
                                }
                            i-- ;
                            nums[nextFreeSpot] = fullOperand ;
                            nextFreeSpot++ ;
                            lastCharIsComma = false ; 
                            lastCharIsColon = false ;
                            
                            }
                        
                        }
                    
                    if ( !validMatrix )
                        {
                        badInput = true ;
                        }

                    } while ( badInput ) ;
                
                // inserts given values into matrix array
                int counter = 0 ;
                for ( int k = 0 ; k < userInputI ; k++ )
                    {
                    for ( int l = 0 ; l < userInputI ; l++ )
                        {
                        matrix[ k ][ l ] = nums[ counter ] ;
                        counter++ ;
                        }
                    }

                // calls function from helper classes to check if given matrix is a solution
                System.out.printf( "%n" ) ;
                if ( MagicSquareComputer.solutionChecker( matrix ) )
                    {
                    for ( int m = 0 ; m < userInputI ; m++ )
                        {
                        System.out.printf( "%s%n", Arrays.toString( matrix[ m ] ) ) ;
                        }
                    System.out.printf( "%n(True) This is a solution for a %dx%d normal magic square%n", userInputI, userInputI );
                    }
                else 
                    {
                    for ( int n = 0 ; n < userInputI ; n++ )
                        {
                        System.out.printf( "%s%n", Arrays.toString( matrix[ n ] ) ) ;
                        }
                    System.out.printf( "%n(False) This is not a solution for a %dx%d normal magic square%n", userInputI, userInputI );
                    }
                
                }
           
            
            
            // second option chosen
            if ( userInputI == 2 )
                {
                // reads in and error-checks given input
                do
                    {
                    if ( !badInput ) 
                        {
                        System.out.printf( "Enter the dimension of your matrix (e.g. enter 3 for a 3x3 matrix): " ) ;
                        userInputS = inputReader.next() ;
                        }
                    else 
                        {
                        System.out.printf( "%n%n%nRe-enter the dimension here: " ) ;
                        userInputS = inputReader.next() ;
                        }
                    
                    try
                        {
                        userInputI = Integer.parseInt( userInputS ) ;
                        if ( userInputI <= 0 )
                            {
                            System.out.printf( "Error - Cannot Create A %dx%d Matrix, Please Enter Positive Integers Only", userInputI, userInputI ) ;
                            badInput = true ;
                            continue ;
                            }
                        }

                    catch ( Exception e3 )
                        {
                        System.out.printf( "Error - Please Enter A Numerical Integer Only" ) ;
                        badInput = true ;
                        continue ;

                        }
                    
                    badInput = false ;

                    } while ( badInput ) ;
                
                // magic squares of dimensions 1x1 and 2x2 are simple/special, no further calculations needed
                if ( userInputI == 1 )
                    {
                    System.out.printf( "A 1x1 normal magic square has only one solution: [1]" ) ;
                    }
                
                else if ( userInputI == 2 )
                    {
                    System.out.printf( "There are no solutions for a 2x2 normal magic square" ) ;
                    }

                // calls helper classes to calculate solutions, keeping track of time elapsed before finishing
                else 
                    {
                    long startTime ;
                    long endTime ;
                    long elapsedTime ;
                    final double NANOSECONDS_PER_SECOND = 1_000_000_000.0 ;
                    System.out.printf("The solutions for a %dx%d normal magic square are:%n%n", userInputI, userInputI ) ;
                    System.out.printf("processing. . . .%n%n") ;
                    startTime = System.nanoTime() ;
                    MagicSquareComputer.solutionComputer( userInputI ) ;
                    
                    endTime = System.nanoTime() ;
                    elapsedTime = endTime - startTime ;
                    if ( elapsedTime < 0 )
                        {
                        elapsedTime = 0 ;
                        }
                    System.out.printf( "%nElapsed Time: %,.7f seconds%n", elapsedTime / NANOSECONDS_PER_SECOND ) ;
                    
                    }
                
                }
            
            }
        
        }

    }
// end class Magic_Square_Checker
