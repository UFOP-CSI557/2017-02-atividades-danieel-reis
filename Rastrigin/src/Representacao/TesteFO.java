package Representacao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author daniel
 */
public class TesteFO {
    
    public static void main(String[] args) {
        calcularFuncaoObjetivo();
    }
    
    // Converte o ângulo de graus para radiano -> Função Math.cos exige como entrada o ângulo em radiano
    private static Double converterGrausParaRadiano(Double graus) {
        return (Math.PI / 180) * graus;
    }
    
    // Calcula a função objetivo -> Fórmula (Função de avaliação)
    public static void calcularFuncaoObjetivo() {
        int n = 100;

        Double somatorio = 0.0;
        for (int i = 0; i < n; i++) {
            Double xi = 5.12;
            somatorio += Math.pow(xi, 2) - 10 * Math.cos(converterGrausParaRadiano(2 * Math.PI * xi));
        }

        Double funcao = 10 * n + somatorio;
        System.out.println("FO  5.12 = " + funcao);
        
        
        somatorio = 0.0;
        for (int i = 0; i < n; i++) {
            Double xi = 0.0;
            somatorio += Math.pow(xi, 2) - 10 * Math.cos(converterGrausParaRadiano(2 * Math.PI * xi));
        }

        funcao = 10 * n + somatorio;
        System.out.println("FO  0.00 = " + funcao);
        
        
        somatorio = 0.0;
        for (int i = 0; i < n; i++) {
            Double xi = -5.12;
            somatorio += Math.pow(xi, 2) - 10 * Math.cos(converterGrausParaRadiano(2 * Math.PI * xi));
        }

        funcao = 10 * n + somatorio;
        System.out.println("FO -5.12 = " + funcao);
    }
}
