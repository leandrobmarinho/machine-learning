package MLM.dados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leandro
 */
public class Registro {

    private List<Double> tupla;
    private double saida;

    public Registro(){
        this.tupla = new ArrayList<>();
    }
    
    public List<Double> getTupla() {
        return tupla;
    }

    public void setTupla(List<Double> tupla) {
        this.tupla = tupla;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }        
}
