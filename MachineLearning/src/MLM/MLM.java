package MLM;

import MLM.LevenbergMarquardt.LevenbergMarquardt;
import MLM.LevenbergMarquardt.FuncaoLM;
import MLM.LevenbergMarquardt.FuncaoLMInterface;
import MLM.dados.Registro;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author leandro
 */
public class MLM {

    ArrayList<Registro> dados;
    int k;
    ArrayList<Registro> dadosSelecionados;
    SimpleMatrix B;

    public MLM(ArrayList<Registro> entrada, int k) {
        this.dados = entrada;
        this.k = k;

    }

    public void treinamento() {
        //Seleciona aleatoriamente k pontos de referencias        
        selecionaKpontos();

        SimpleMatrix Dx = computaDx();
        
        SimpleMatrix DeltaY = computaDy();
        
        B = ( (Dx.transpose().mult(Dx) ).invert() ).mult(Dx.transpose()).mult(DeltaY) ;
        B.print();
    }

    //https://code.google.com/p/efficient-java-matrix-library/wiki/SimpleMatrix
    public double teste(List<Double> ponto) {
        SimpleMatrix distXR = computaDistXR(ponto);        
        
        SimpleMatrix distYT = distXR.mult(B);
        
        FuncaoLMInterface funcao = new FuncaoLM();
        funcao.compute(null, null, null);
        LevenbergMarquardt LM = new LevenbergMarquardt(funcao);
        
        return 0.0;
    }
    
    private SimpleMatrix computaDistXR(List<Double> ponto){
        SimpleMatrix distXR = new SimpleMatrix(1, k);
        double distancia;
        for (int coluna = 0; coluna < k; coluna++) {
            distancia = calculaDistancia(ponto, dadosSelecionados.get(coluna).getTupla());
            
            distXR.set(0, coluna, distancia);
        }
        
        return distXR;
    }

    private double calculaDistancia(List<Double> x, List<Double> r) {
        double distancia = 0.0;

        for (int i = 0; i < x.size(); i++) {
            distancia +=  Math.pow( (r.get(i) - x.get(i)) ,2);
        }
        distancia = Math.sqrt(distancia);
        return distancia;
    }
    
    private SimpleMatrix computaDx(){
        double distancia;
        //Computa a distancia entre os dados de treinamento e seu subconjunto |k|
        SimpleMatrix Dx = new SimpleMatrix(new double[dados.size()][k]);
        for (int linha = 0; linha < dados.size(); linha++) {

            for (int coluna = 0; coluna < k; coluna++) {
                distancia = calculaDistancia(dados.get(linha).getTupla(),
                        dadosSelecionados.get(coluna).getTupla());

                //Dx.setColumn(coluna, linha, distancia);
                Dx.set(linha, coluna, distancia);
            }
        }
        
        return Dx;
    }

    private SimpleMatrix computaDy(){
        double distancia;
        List<Double> y, t;
        
        SimpleMatrix Dy = new SimpleMatrix(new double[dados.size()][k]);
        for (int linha = 0; linha < dados.size(); linha++) {

            t = new ArrayList<>();
            t.add(dados.get(linha).getSaida());
            
            for (int coluna = 0; coluna < k; coluna++) {
                
                y = new ArrayList<>();
                y.add(dadosSelecionados.get(coluna).getSaida());
                
                distancia = calculaDistancia(y,t);

                //Dy.setColumn(coluna, linha, distancia);
                Dy.set(linha, coluna, distancia);
            }
        }
        
        return Dy;
    }

    private void selecionaKpontos() {
        ArrayList<Integer> indicesAleatorios = new ArrayList<>();
        for (int i = 0; i < dados.size(); i++) {
            indicesAleatorios.add(i);
        }
        Collections.shuffle(indicesAleatorios);

        
        dadosSelecionados = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            dadosSelecionados.add(dados.get(indicesAleatorios.get(i)));
        }
    }
}
