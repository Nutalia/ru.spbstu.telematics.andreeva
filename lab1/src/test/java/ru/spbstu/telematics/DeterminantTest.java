package ru.spbstu.telematics;

import junit.framework.Test;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

/**
 * Unit test for simple App.
 */
public class DeterminantTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DeterminantTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DeterminantTest.class );
    }
	
	private void printData(int i, int[][] m, int n, int exp)
	{
		System.out.println("Test matrix " + i + ":");
		Determinant.print(m, n);
		int d = Determinant.det(m, n);
		System.out.println("det = " + d);
		Assert.assertEquals(exp, d);
	}

    /**
     * Rigourous Test :-)
     */
    public void testDeterminant1()
    {
        int[][] matrix = new int[5][5];
	for(int i = 0; i < 5; i++)
	{
			for(int j = 0; j < 5; j++)
			{
				matrix[i][j] = (j + 1)*(i + 1);
			}
	}
	printData(1, matrix, 5, 0);
    }

    public void testDeterminant2()
    {
        int[][] matrix = {{ 0,  1, 15,  9, 10},
			  {13,  6,  9, 14,  7},
 			  { 8, 19, 17,  3, 16},
 			  { 4,  8, 12,  3,  7},
			  { 9, 13, 17, 18,  0}};
	printData(2, matrix, 5, 156018);
    }

    public void testDeterminant3()
    {
        int[][] matrix = {{ 2, -3,  5},
			  { 1,  0, -4},
 			  { 2,  1, -1}};
	printData(3, matrix, 3, 34);
    }

    public void testDeterminant4()
    {
        int[][] matrix = {{ 2, -5,  1,  2},
			  {-3,  7, -1,  4},
 			  { 5, -9,  2,  7},
 			  { 4, -6,  1,  2}};
	printData(4, matrix, 4, -9);
    }

    public void testDeterminant5()
    {
        int[][] matrix = {{ -3,  0,  0,  0},
			  { 28,  1,  0,  0},
 			  {-67, 83,  2,  0},
 			  { 19, 47,-35, -4}};
	printData(5, matrix, 4, 24);
    }
}