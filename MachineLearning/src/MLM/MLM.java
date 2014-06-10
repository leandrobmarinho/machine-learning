package MLM;

import MLM.dados.Entrada;
import java.util.ArrayList;
import MLM.dados.Registro;
/**
 *
 * @author leandro
 */
public class MLM {
    
    ArrayList<Registro> dados;
    int k;
    
    public MLM(ArrayList<Registro> entrada, int k){
        this.dados = entrada;
        this.k = k;
        
        System.out.println(entrada.get(0).getTupla() + " " + entrada.get(0).getSaida());
    }
    
    public void treinamento(){
        
    }
    
    public void teste(){
        
    }
}
