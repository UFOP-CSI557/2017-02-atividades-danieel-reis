package Representacao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author daniel
 */
public class Problema {

    private String funcaoCalculo;
    private String nomeArquivo;
    private String nomeInstancia;
    private int dimensao;

    private Double[][] coordenadas;
    private Double[][] distancias;

    public Problema(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;

        readFile(this.nomeArquivo);
        calcularDistancias();
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeInstancia() {
        return nomeInstancia;
    }

    public void setNomeInstancia(String nomeInstancia) {
        this.nomeInstancia = nomeInstancia;
    }

    public int getDimensao() {
        return dimensao;
    }

    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }

    public Double[][] getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Double[][] coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Double[][] getDistancias() {
        return distancias;
    }

    public void setDistancias(Double[][] distancias) {
        this.distancias = distancias;
    }

    public String getFuncaoCalculo() {
        return funcaoCalculo;
    }

    public void setFuncaoCalculo(String funcaoCalculo) {
        this.funcaoCalculo = funcaoCalculo;
    }

    public void readFile(String namefile) {
        System.out.println("Leitura do arquivo\n");
        // Recuperando os dados e importando
        try {
            BufferedReader br = new BufferedReader(new FileReader(namefile));

            String linha;
            String dados[];

            // Nome da instância
            linha = br.readLine();
            dados = linha.split(":");
            this.nomeInstancia = dados[1];

            // Comentário
            br.readLine();

            // Tipo
            br.readLine();

            // Dimensão
            linha = br.readLine();
            dados = linha.split(":");
            this.dimensao = Integer.parseInt(dados[1].trim());

            // Instânciação
            this.coordenadas = new Double[this.dimensao][2];
            this.distancias = new Double[this.dimensao][this.dimensao];

            // Função de Cálculo (ATT, EUC_2D, ...)
            linha = br.readLine();
            dados = linha.split(":");
            this.funcaoCalculo = dados[1].trim();

            // Cabeçalho
            br.readLine();

            // Percorre linha a linha
            while ((linha = br.readLine()) != null) {

                if (linha.equals("EOF")) {
                    break;
                }

                dados = linha.split(" ");

                // Identificador
                int id = Integer.parseInt(dados[0]);
                // X
                this.coordenadas[id - 1][0] = Double.parseDouble(dados[1]);
                // Y
                this.coordenadas[id - 1][1] = Double.parseDouble(dados[2]);

                System.out.println(linha);
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException - " + e.getMessage());
        }
    }

    public void calcularDistancias() {
        switch (this.funcaoCalculo) {
            case "ATT":
                calcularDistanciasATT();
                break;
            case "EUC_2D":
                calcularDistanciasEUC_2D();
                break;
            default:
                throw new UnsupportedOperationException("Função não implementada");
        }

        // Impressão
        System.out.println("\n\nMatriz de distâncias\n");
        for (int i = 0; i < this.dimensao; i++) {
            for (int j = 0; j < this.dimensao; j++) {
                String value = String.valueOf(this.distancias[i][j]);

                int pos = 0;
                while (value.charAt(pos) != '.' && pos < value.length() - 1) {
                    pos++;
                }
                if (pos > 0 && pos + 2 < value.length() - 1) {
                    value = value.substring(0, pos + 3);
                }

                System.out.print(value + "\t");
            }
            System.out.println("");
        }
    }

    private void calcularDistanciasATT() {
        // ATT - Pseudo Euclidiana
        Double dist;
        for (int i = 0; i < this.dimensao; i++) {
            for (int j = 0; j < this.dimensao; j++) {
                if (i == j) {
                    dist = 0.0;
                } else {
                    Double x1 = coordenadas[i][0];
                    Double x2 = coordenadas[j][0];
                    Double y1 = coordenadas[i][1];
                    Double y2 = coordenadas[j][1];

                    Double d = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
                    dist = Math.sqrt(d / 10.0);

                    double t = Math.round(dist);

                    if (t < dist) {
                        dist = t + 1.0;
                    } else {
                        dist = t;
                    }
                }

                this.distancias[i][j] = dist;
            }
        }

    }

    private void calcularDistanciasEUC_2D() {
        // EUC_2D
        Double dist;
        for (int i = 0; i < this.dimensao; i++) {
            for (int j = 0; j < this.dimensao; j++) {
                if (i == j) {
                    dist = 0.0;
                } else {
                    Double x1 = coordenadas[i][0];
                    Double x2 = coordenadas[j][0];
                    Double y1 = coordenadas[i][1];
                    Double y2 = coordenadas[j][1];

                    dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                }

                this.distancias[i][j] = dist;
            }
        }
    }

    // Calcula função objetivo
    public void calcularFuncaoObjetivo(Individuo individuo) {
        Double custo = 0.0;

        for (int i = 0; i < this.dimensao - 1; i++) {
            custo += this.distancias[individuo.getCromossomos().get(i) - 1][individuo.getCromossomos().get(i + 1) - 1];
        }

        individuo.setFuncaoObjetivo(custo);
    }

    @Override
    public String toString() {
        return "Problema{" + "funcaoCalculo=" + funcaoCalculo + ", nomeArquivo=" + nomeArquivo + ", nomeInstancia=" + nomeInstancia + ", dimensao=" + dimensao + ", coordenadas=" + coordenadas + ", distancias=" + distancias + '}';
    }

}
