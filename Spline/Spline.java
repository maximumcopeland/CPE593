/*
	SolveTridiagonal.java
	Author: Max Copeland
	"I pledge my honor that I have abided by the Stevens Honor System"
	Assistance in understanding things from Brian Bazergui and Sam Yakovlev
	Honestly what the fuck
*/

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.text.DecimalFormat;

public class Spline {
	// Public objects
	public int n; // Number of inputs
	public int ns; // Number of segments
	public double[] xInputs, yInputs, xValues, yValues;
	public Spline(double[] X, double[] Y, int noSeg) {
		ns = noSeg;
		n = noSeg + 1;
		xInputs = X;
		yInputs = Y;
		solve(X, Y);
	}

	public void solve(double[] X, double[] Y) {
		// Apply Thomas 
		double[] a = new double[n];
		double[] b = new double[n];
		double[] c = new double[n];
		double[] dx = new double[n];
		double[] dy = new double[n];
		Arrays.fill(a, 1); // Initialize a
		Arrays.fill(b, 4); // Initialize b
		Arrays.fill(c, 1); // Initialize c
		for (int i = 1; i < n-3; i++) {
			// Initialize dx and dy
			dx[i] = 3*(X[i] - X[i-1]);
			dy[i] = 3*(Y[i] - Y[i-1]);
		}
		// Cleanup
		a[0] = 0; // First
		c[n-1] = 0; // Last
		dx[0] = 3*(X[1] - X[0]); // First
		dy[0] = 3*(Y[1] - Y[0]); // First
		dx[n-1] = 3*(X[n-1] - X[n-2]); // Last
		dy[n-1] = 3*(Y[n-1] - Y[n-2]); // Last

		// Create c_star
		double[] c_star = new double[n];
		c_star[0] = c[0]/b[0];
		for (int i = 1; i < n-1; i++) {
			// Initialize the rest of c_star
			c_star[i] = c[i]/(b[i]-(a[i] * c_star[i-1]));
		}

		// Create dx_star and dy_star
		double[] dx_star = new double[n];
		dx_star[0] = dx[0]/b[0];
		for (int i = 1; i < n-1; i++) {
			// Initialize the rest of d_star
			dx_star[i] = (dx[i] - (a[i] * dx_star[i-1])) / (b[i] - (a[i] * c_star[i-1]));
		}
		double[] dy_star = new double[n];
		dy_star[0] = dy[0]/b[0];
		for (int i = 1; i < n; i++) {
			// Initialize the rest of d_star
			dy_star[i] = (dy[i] - (a[i] * dy_star[i-1])) / (b[i] - (a[i] * c_star[i-1]));
		}

		// Find the values for X and Y
		yValues = new double[n];
		xValues = new double[n];
		xValues[n-1] = dx_star[n-1];
		yValues[n-1] = dy_star[n-1];
		for (int i = n-2; i >= 0; i--) {
			xValues[i] = dx_star[i] - (c_star[i] * xValues[i+1]);
			yValues[i] = dy_star[i] - (c_star[i] * yValues[i+1]);
		}
	}

	public void print() {
		DecimalFormat df = new DecimalFormat("#.##");
		double a, b, c, d, t;
		// Print for X values first
		for (int i = 0; i < ns; i++) {
			// Do for each segment
			// X Values
			System.out.print("Segment " + (i+1) + " x(t) values from 0.1 to 0.9: ");
			a = xInputs[i];
			b = xValues[i];
			c = (3 * (xInputs[i+1] - xInputs[i])) - ((2 * xValues[i]) - xValues[i+1]);
			d = (2 * (xInputs[i] - xInputs[i+1])) + xValues[i] + xValues[i+1];
			for (double j = 0.1; j < 1; j += 0.1) {
				// For each point on the segment
				t = a + (b * j) + (c * j * j) + (d * j * j * j);
				System.out.print(df.format(t) + " ");
			}
			System.out.println();
			
			// Y Values
			System.out.print("Segment " + (i+1) + " y(t) values from 0.1 to 0.9: ");
			a = yInputs[i];
			b = yValues[i];
			c = (3 * (yInputs[i+1] - yInputs[i])) - ((2 * yValues[i]) - yValues[i+1]);
			d = (2 * (yInputs[i] - yInputs[i+1])) + yValues[i] + yValues[i+1];
			for (double j = 0.1; j < 1; j += 0.1) {
				// For each point on the segment
				t = a + (b * j) + (c * j * j) + (d * j * j * j);
				System.out.print(df.format(t) + " ");
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(new File("./hw9.dat"));
		int noSeg = input.nextInt(); // Get number of segments from first line
		double[] xInputs = new double[noSeg+1];
		double[] yInputs = new double[noSeg+1];
		int it = 0; // Iterator 

		// Read in data points
		while (input.hasNextDouble()) {
			xInputs[it] = input.nextDouble();
			yInputs[it] = input.nextDouble();
			it++;
		}
		input.close();

		// Make the Spline
		Spline test = new Spline(xInputs, yInputs, noSeg);
		test.print();
	}
}