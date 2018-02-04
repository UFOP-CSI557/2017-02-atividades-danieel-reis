package Algoritmo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Main.Main;
import Representacao.BuscaLocal;
import Representacao.Populacao;
import Representacao.Individuo;
import Representacao.Operacoes;
import Representacao.Problema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class AG {

    private int tamPop;
    private Double pCrossover;
    private Double pMutacao;
    private Double pBuscaLocal;
    private int geracoes;

    private ArrayList<Double> funcaoObjetivo;
    private Individuo melhorSolucao;
    private Individuo piorSolucao;
    
    private Populacao populacao;
    private Populacao novaPopulacao;

    private Problema problema;

    public AG(int tamPop, Double pCrossover, Double pMutacao, Double pBuscaLocal, int geracoes, Problema problema) {
        this.tamPop = tamPop;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.pBuscaLocal = pBuscaLocal;
        this.geracoes = geracoes;
        this.problema = problema;
        this.funcaoObjetivo = new ArrayList<>();
    }

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    private void setMelhorSolucao(Individuo melhorSolucao) {
        this.melhorSolucao = melhorSolucao;
    }

    public Individuo getPiorSolucao() {
        return piorSolucao;
    }

    private void setPiorSolucao(Individuo piorSolucao) {
        this.piorSolucao = piorSolucao;
    }

    public ArrayList<Double> getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(ArrayList<Double> funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    // Executar
    public void executar() {
        this.populacao = new Populacao(tamPop, problema);
        this.novaPopulacao = new Populacao(tamPop, problema);

        // Geração da população inicial
        this.populacao.criar();

        // Avaliar a população inicial
        this.populacao.avaliar();

        try {
            // Recupera o melhor individuo
            melhorSolucao = (Individuo) populacao.getMelhorIndividuo().clone();

            // Recupera o pior individuo
            piorSolucao = (Individuo) populacao.getPiorIndividuo().clone();

            // Guarda os valores das funções objetivo
            for (Individuo i : populacao.getIndividuos()) {
                funcaoObjetivo.add(i.getFuncaoObjetivo());
            }

        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }


        // Informação sobre a solução inicial
        if (Main.IMPRIMIR) {
            System.out.println("\nSolução inicial: " + getMelhorSolucao().getFuncaoObjetivo());
        }

        Random rnd = new Random();
        int pai1, pai2;

        // Número de Gerações - Critério de parada
        for (int gen = 1; gen <= geracoes; gen++) {

            // Reprodução
            this.novaPopulacao.getIndividuos().clear();

            // Percorre a população corrente e seleciona os pais
            for (int j = 0; j < tamPop; j++) {

                // Se o crossover deve ser aplicado
                if (rnd.nextDouble() <= pCrossover) {
                    // Seleciona os pais
                    pai1 = rnd.nextInt(tamPop);

                    do {
                        pai2 = rnd.nextInt(tamPop);
                    } while (pai1 == pai2);

                    Individuo filho1 = new Individuo(problema.getDimensao());
                    Individuo filho2 = new Individuo(problema.getDimensao());

                    Operacoes.crossoverOX(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), filho1, filho2, problema);

                    problema.calcularFuncaoObjetivo(filho1);
                    problema.calcularFuncaoObjetivo(filho2);

                    Operacoes.mutacaoSWAP(filho1, problema, pMutacao);
                    Operacoes.mutacaoSWAP(filho2, problema, pMutacao);

                    // Avalia descendentes
                    problema.calcularFuncaoObjetivo(filho1);
                    problema.calcularFuncaoObjetivo(filho2);

                    // Busca local
                    if (rnd.nextDouble() <= pBuscaLocal) {
                        BuscaLocal.buscaLocal(filho1, problema);
                    }
                    if (rnd.nextDouble() <= pBuscaLocal) {
                        BuscaLocal.buscaLocal(filho2, problema);
                    }

                    // Insere nova população
                    novaPopulacao.getIndividuos().add(filho1);
                    novaPopulacao.getIndividuos().add(filho2);
                }
            }

            // Define sobreviventes - selecionar entre a população corrente + descendentes
            // Combinar pop corrente + novapopulacao
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            // Ordenar indivíduos
            Collections.sort(populacao.getIndividuos());

            // Cortar a população
            populacao.getIndividuos().subList(tamPop, populacao.getIndividuos().size()).clear();

            try {
                // Atualiza a variável que armazena o melhor indivíduo da população
                if (populacao.getMelhorIndividuo().getFuncaoObjetivo() < getMelhorSolucao().getFuncaoObjetivo()) {
                    setMelhorSolucao((Individuo) populacao.getMelhorIndividuo().clone());
                }

                // Atualiza a variável que armazena o pior indivíduo da população
                if (populacao.getPiorIndividuo().getFuncaoObjetivo() > getPiorSolucao().getFuncaoObjetivo()) {
                    setPiorSolucao((Individuo) populacao.getPiorIndividuo().clone());
                }

            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }

            Double FO = getMelhorSolucao().getFuncaoObjetivo();
            if (Main.IMPRIMIR) {
                if (gen < 10) {
                    System.out.println("Gen = " + gen + "\t\tFO = " + FO + " "
                            + "\tPop = " + populacao.getIndividuos().size()
                            + "\tGAP = " + Operacoes.gap(Main.RL, FO));
                } else {
                    System.out.println("Gen = " + gen + "\tFO = " + FO
                            + " \tPop = " + populacao.getIndividuos().size()
                            + "\tGAP = " + Operacoes.gap(Main.RL, FO));
                }
            }
        }
    }

}