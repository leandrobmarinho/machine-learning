package MLM.dados;

import MLM.MLM;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leandro
 */
public class Entrada {

    private List<Registro> dadosDeEntrada;
    private ArrayList<Integer> indicesAleatorios;
    private double porcentagemDados = 0.1;
    private ArrayList<Registro> dTeste;
    private ArrayList<Registro> dTreinamento;

    public Entrada() throws Exception {
        this.dadosDeEntrada = new ArrayList<>();
        this.indicesAleatorios = new ArrayList<>();

        long t1 = System.currentTimeMillis();
        this.lerArquivo();

        long t2 = System.currentTimeMillis();
        double deltaT = (t2 - t1);
        //System.out.println("intervalo de tempo: " + deltaT + " ms");
    }

    private void lerArquivo() throws IOException {

        String path = "iris.data";

        File arq = new File(path);
        InputStream is = new FileInputStream(arq);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        int cont = 0;
        while (linha != null) {
            this.carregarUnidadesDeEntrada(linha);
            linha = br.readLine();
            cont++;

        }

        //System.out.println(cont + " registros");

        //Separa conjunto de teste
        sorteiaDados();
        selecionaDadosTeste();
    }

    private void carregarUnidadesDeEntrada(String linha) {
        String[] split = linha.split(",");

        Registro novoRegistro = new Registro();
        for (int i = 0; i < (split.length - 1); i++) {
            novoRegistro.getTupla().add(Double.parseDouble(split[i]));
        }

        if ("Iris-versicolor".equals(split[split.length - 1])) {
            novoRegistro.setSaida(1.0);
        } else {
            novoRegistro.setSaida(-1.0);
        }

        this.dadosDeEntrada.add(novoRegistro);
    }

    public static void main(String[] args) {
        try {
            Entrada entrada = new Entrada();
            MLM mlm = new MLM(entrada.getDTreinamento(), 5);
            mlm.treinamento();
            mlm.teste(entrada.getDTeste().get(0).getTupla());

        } catch (Exception ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sorteiaDados() {
        for (int i = 0; i < dadosDeEntrada.size(); i++) {
            indicesAleatorios.add(i);
        }
        Collections.shuffle(indicesAleatorios);
    }

    public void selecionaDadosTeste() {
        dTreinamento = new ArrayList<>();
        for (int i = 0; i < ((int) (dadosDeEntrada.size() * porcentagemDados)); i++) {
            dTreinamento.add(dadosDeEntrada.get(indicesAleatorios.get(i)));
        }

        dTeste = new ArrayList<>();
        for (int i = ((int) (dadosDeEntrada.size() * porcentagemDados)) + 1; i < dadosDeEntrada.size(); i++) {
            dTeste.add(dadosDeEntrada.get(i));
        }
    }

    public ArrayList<Registro> getDTeste() {
        return dTeste;
    }    

    public ArrayList<Registro> getDTreinamento() {
        return dTreinamento;
    }    
}
