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
	//System.out.println("Test matrix 1:");
	//Determinant.print(matrix, 5);
	//int d = Determinant.det(matrix, 5);
	//System.out.println("det = " + d);
	//Assert.assertEquals(0, d);
	printData(1, matrix, 5, 0);
    }

    public void testDeterminant2()
    {
        int[][] matrix = {{ 0,  1, 15,  9, 10},
			  {13,  6,  9, 14,  7},
 			  { 8, 19, 17,  3, 16},
 			  { 4,  8, 12,  3,  7},
			  { 9, 13, 17, 18,  0}};
	//System.out.println("Test matrix 2:");
	//Determinant.print(matrix, 5);
	//int d = Determinant.det(matrix, 5);
	//System.out.println("det = " + d);
	//Assert.assertEquals(156018, d);
	printData(2, matrix, 5, 156018);
    }
}