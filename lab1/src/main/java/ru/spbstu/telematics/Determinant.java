import java.util.*;

public class Determinant
{
	private static int Det(int[][] m, int n)
	{
		if (n == 2)
			return m[0][0]*m[1][1] - m[0][1]*m[1][0];
		else
		{
			int det = 0;
			for (int i = 0; i < n; i++)
			{
				int[][] matr = new int[n - 1][n - 1];
				for (int j = 0; j < n - 1; j++)
					for(int k = 1; k < n; k++)
						if (j < i)
							matr[j][k - 1] = m[j][k];
						else matr[j][k - 1] = m[j + 1][k];
				det += (i % 2 == 1) ? (-m[i][0] * Det(matr, n - 1)) : (m[i][0] * Det(matr, n - 1));
			}
			return det;
		}
	}

	public static void main(String[] args)
	{
		System.out.printf("Введите порядок матрицы:");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[][] matrix = new int[n][n];
		System.out.printf("Как заполнить матрицу?\nВручную - 1\nАвтоматически - 2\nВведите:");
		int answer = in.nextInt();
		while(answer != 1 && answer != 2)
		{
			System.out.println("Ввод неверен. Повторите:");
			answer = in.nextInt();
		}
		if(answer == 1)
		{
			System.out.printf("Введите матрицу %dx%d:", n, n);
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					matrix[i][j] = in.nextInt();
		}
		else 
		{
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					matrix[i][j] = 20 - (int) (Math.random() * 41); //диапозон значений [-20; 20]
		}
		System.out.println("\nИсходная матрица A:");
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
					System.out.printf("%3d", matrix[i][j]);
			System.out.printf("\n");
		}
		int det = Det(matrix, n);
		System.out.println("det A = " + det);
	}
}