package MLM.LevenbergMarquardt;

import org.ejml.data.DenseMatrix64F;

/**
 *
 * @author leandro
 */
public class FuncaoLM implements FuncaoLMInterface {

    /**
     * Computes the output for each value in matrix x given the set of
     * parameters.
     *
     * @param param The parameter for the function.
     * @param x the input points.
     * @param y the resulting output.
     */
    @Override
    public void compute(DenseMatrix64F param, DenseMatrix64F x, DenseMatrix64F y) {
        double a = param.data[0];
        double b = param.data[1];
        double c = param.data[2];

        double dataX[] = x.data;
        double dataY[] = y.data;

        int length = x.numRows;

        for (int i = 0; i < length; i++) {
            double v = dataX[i];

            dataY[i] = a + b * v + c * v * v;
        }
    }

    public void deriv(DenseMatrix64F x, DenseMatrix64F deriv) {
        double dataX[] = x.data;

        int length = x.numRows;

        for (int j = 0; j < length; j++) {
            double v = dataX[j];

            double dA = 1;
            double dB = v;
            double dC = v * v;

            deriv.set(0, j, dA);
            deriv.set(1, j, dB);
            deriv.set(2, j, dC);
        }

    }
}
