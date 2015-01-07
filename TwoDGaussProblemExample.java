import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;


/**
 * This is the test program for two dimensional Gaussian function fitting.
 * @author araiyoshiyuki
 * @date 07/01/2015
 */
public class TwoDGaussProblemExample {

	public static void main(String[] args) {						
        //entry the data (5x5)
		double[] inputdata = {
				0  ,12 ,25 ,12 ,0  ,
				12 ,89 ,153,89 ,12 ,
				25 ,153,255,153,25 ,
				12 ,89 ,153,89 ,12 ,
				0  ,12 ,25 ,12 ,0  ,
		};
		
		int x_width = 5; // width of x
		
		//construct two-dimensional Gaussian function
		TwoDGaussianFunction tdgf = new TwoDGaussianFunction(x_width,inputdata.length);
		
		//prepare construction of LeastSquresProblem by builder
		LeastSquaresBuilder lsb = new LeastSquaresBuilder();

		//set model function and its jacobian
		lsb.model(tdgf.retMVF(), tdgf.retMMF());
		
		//set target data
		lsb.target(inputdata);
		
		//set initial parameters
		double[] newStart = {
				255,
				1,
				1,
				1,
				1,
				1
		};
		
		lsb.start(newStart);
		//set upper limit of evaluation time
		lsb.maxEvaluations(1000);
		//set upper limit of iteration time
		lsb.maxIterations(100);
		
		LevenbergMarquardtOptimizer lmo = new LevenbergMarquardtOptimizer();
		try{
			//do LevenbergMarquardt optimization
			LeastSquaresOptimizer.Optimum lsoo = lmo.optimize(lsb.build());
			
			//get optimized parameters
			final double[] optimalValues = lsoo.getPoint().toArray();			
			//output data
			System.out.println("v0: " + optimalValues[0]);
			System.out.println("v1: " + optimalValues[1]);
			System.out.println("v2: " + optimalValues[2]);
			System.out.println("v3: " + optimalValues[3]);
			System.out.println("v4: " + optimalValues[4]);
			System.out.println("v5: " + optimalValues[5]);
			System.out.println("Iteration number: "+lsoo.getIterations());
			System.out.println("Evaluation number: "+lsoo.getEvaluations());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
