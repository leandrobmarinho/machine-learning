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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
