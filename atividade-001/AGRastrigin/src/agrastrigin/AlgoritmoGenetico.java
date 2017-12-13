/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class AlgoritmoGenetico {

    private ArrayList<Double> funcaoObjetivo;

    private int tamPop;
    private double pCrossover;
    private double pMutacao;
    private int geracoes;
    private Problema problema;

    private Individuo melhorSolucao;
    private Individuo piorSolucao;

    private Populacao populacao;
    private Populacao novaPopulacao;

    private Double elitismo;

    private int crossover;

    public AlgoritmoGenetico(int tamPop, double pCrossover, double pMutacao, int geracoes, Problema problema, Double elitismo, int crossover) {
        this.tamPop = tamPop;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.elitismo = elitismo;
        this.crossover = crossover;
        this.funcaoObjetivo = new ArrayList<>();
    }

    public Double getMedia() {
        Double soma = 0.0;
        for (Double fo : funcaoObjetivo) {
            soma += fo;
        }
        return (soma / funcaoObjetivo.size());
    }

    public Double getDesvioPadrao(Double media) {
        Double soma = 0.0;
        Double dif;
        for (Double fo : funcaoObjetivo) {
            // Subtrair a média de cada um dos valores
            dif = (fo - media);

            // Elevar ao quadrado dif e somar
            soma += Math.pow(dif, 2);
        }

        // Dividir a soma dos quadrados por (n-1)
        Double variancia = soma / (funcaoObjetivo.size() - 1);

        // Desvio padrão = raiz quadrada da variância
        return Math.sqrt(variancia);
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

    // Executa o algoritmo genético
    public void executar() {

        populacao = new Populacao(problema, tamPop);
        novaPopulacao = new Populacao(problema, tamPop);

        // Cria a população
        populacao.criarPopulacao();

        // Avalia a população inicial
        populacao.avaliar();

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

        // Imprime a solução inicial
        if (AGRastrigin.IMPRIMIR) {
            System.out.println("\nSolução inicial: " + melhorSolucao.getFuncaoObjetivo());
        }

        Random rnd = new Random();
        int pai1, pai2;

        // Será selecionado os x% melhores indivíduos da população
        int numPais = (int) (elitismo * tamPop);

        // Gerações
        for (int gen = 1; gen <= geracoes; gen++) {

            // Selecionando os x% melhores indivíduos da população e move para a nova população
            ArrayList<Individuo> pais = new ArrayList<>();

            for (int i = 0; i < numPais; i++) {
                pais.add(melhorSolucao);
                populacao.getIndividuos().remove(melhorSolucao);
                populacao.avaliar();
            }

            novaPopulacao.getIndividuos().addAll(pais);

            // Reproduz pra gerar os (tamPop - x) selecionados pelo elitismo
            for (int j = 0; j < (this.tamPop - numPais); ++j) {

                // Gera um número aleatório entre 0 e 1 -> Somente realiza o crossover se o valor gerado for menor que a taxa de crossover
                if (rnd.nextDouble() <= this.pCrossover) {

                    // Seleciona dois pais aleatórios -> diferentes
                    pai1 = rnd.nextInt(numPais);
                    do {
                        pai2 = rnd.nextInt(numPais);
                    } while (pai1 == pai2);

                    try {
                        // Copiar os pais nos descendentes:
                        Individuo descendente = (Individuo) novaPopulacao.getIndividuos().get(pai1).clone();

                        // Operar -> Crossover
                        if (crossover == AGRastrigin.CROSSOVER_UM_PONTO) {
                            crossoverUmPonto(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), descendente);
                        } else if (crossover == AGRastrigin.CROSSOVER_DOIS_PONTOS) {
                            crossoverDoisPontos(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), descendente);
                        }

                        // Operar -> Mutacao
                        mutacaoPorBit(descendente);

                        // Avalia o descendente criado
                        descendente.calcularFuncaoObjetivo();

                        // Adiciona a função objetivo do descendente gerado
                        funcaoObjetivo.add(descendente.getFuncaoObjetivo());

                        // Adiciona o novo indivíduo na população
                        novaPopulacao.getIndividuos().add(descendente);

                    } catch (CloneNotSupportedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            // Define os sobreviventes -> Primeiro: adiciona todos indivíduos da novaPop na pop
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            // Define os sobreviventes -> Segundo: ordena os indivíduos da população pela função objetivo
            Collections.sort(populacao.getIndividuos());

            // Define os sobreviventes -> Terceiro: seleciona apenas os x primeiros, mantendo o tamanho da população
            populacao.getIndividuos().subList(tamPop, populacao.getIndividuos().size()).clear();

            // Atualiza a variável que armazena o melhor indivíduo da população
            if (populacao.getMelhorIndividuo().getFuncaoObjetivo() < this.getMelhorSolucao().getFuncaoObjetivo()) {
                try {
                    this.setMelhorSolucao((Individuo) populacao.getMelhorIndividuo().clone());
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
            }

            // Atualiza a variável que armazena o pior indivíduo da população
            if (populacao.getPiorIndividuo().getFuncaoObjetivo() > this.getPiorSolucao().getFuncaoObjetivo()) {
                try {
                    this.setPiorSolucao((Individuo) populacao.getPiorIndividuo().clone());
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
            }

            if (AGRastrigin.IMPRIMIR) {
                if (gen < 10) {
                    System.out.println("Gen = " + gen + "\t\tFO = " + this.getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                } else {
                    System.out.println("Gen = " + gen + "\tFO = " + this.getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                }
            }
        }

    }

    // Cria um filho que tem parte do pai1 e parte do pai2
    private void crossoverUmPonto(Individuo pai1, Individuo pai2, Individuo filho) {
        Random rnd = new Random();

        // Escolhe uma posição de corte no pai1
        int corte = rnd.nextInt(pai1.getCromossomos().size());

        // Limpa todos cromossomos do filho
        filho.getCromossomos().clear();

        // Insere no filho a parte esquerda a posição de corte no pai1
        filho.getCromossomos().addAll(pai1.getCromossomos().subList(0, corte));

        // Insere no filho a parte direita a posição de corte no pai2
        filho.getCromossomos().addAll(pai2.getCromossomos().subList(corte, pai2.getCromossomos().size()));
    }

    // Cria um filho que tem parte do pai1 e parte do pai2
    private void crossoverDoisPontos(Individuo pai1, Individuo pai2, Individuo filho) {
        Random rnd = new Random();

        // Escolhe duas posições de corte
        int corte1 = rnd.nextInt(pai1.getCromossomos().size() / 2);
        int corte2;
        do {
            corte2 = rnd.nextInt(pai1.getCromossomos().size());
        } while (corte2 <= corte1);

        // Limpa todos cromossomos do filho
        filho.getCromossomos().clear();

        // Se o pai um tiver melhor função objetivo, usa mais parte dele
        if (pai1.getFuncaoObjetivo() < pai2.getFuncaoObjetivo()) {
            // Insere no filho a parte esquerda a posição de corte no pai1
            filho.getCromossomos().addAll(pai1.getCromossomos().subList(0, corte1));

            // Insere no filho a parte central a posição de corte no pai2
            filho.getCromossomos().addAll(pai2.getCromossomos().subList(corte1, corte2));

            // Insere no filho a parte direita a posição de corte no pai1
            filho.getCromossomos().addAll(pai1.getCromossomos().subList(corte2, pai1.getCromossomos().size()));
        } else {
            // Insere no filho a parte esquerda a posição de corte no pai2
            filho.getCromossomos().addAll(pai2.getCromossomos().subList(0, corte1));

            // Insere no filho a parte central a posição de corte no pai1
            filho.getCromossomos().addAll(pai1.getCromossomos().subList(corte1, corte2));

            // Insere no filho a parte direita a posição de corte no pai2
            filho.getCromossomos().addAll(pai2.getCromossomos().subList(corte2, pai2.getCromossomos().size()));
        }
    }

    private void mutacaoPorBit(Individuo filho) {
        Random rnd = new Random();

        double valor;
        double p;
        Boolean r;
        // Tenta realizar a mutação sob cada cromossomo do indivíduo
        for (int i = 0; i < filho.getCromossomos().size(); ++i) {

            // Gera um número aleatório entre 0 e 1 -> Somente realiza a mutação se o valor gerado for menor que a taxa de mutação
            p = rnd.nextDouble();
            if (p <= this.pMutacao) {

                // Gera um número aleatório entre 0 e 1 e multiplica pelo valor do cromossomo
                valor = filho.getCromossomos().get(i) * rnd.nextDouble();

                // Gera um valor aleatório (V/F) -> Possibilita a chance de criar um valor negativo ou não
                r = rnd.nextBoolean();
                if (r == false) {
                    valor = -valor;
                }

                // Soma o valor gerado ao cromossomo em questão
                valor += filho.getCromossomos().get(i);

                // Se o valor final estiver dentro do intervalo, realiza a mutação
                if (valor >= this.problema.getMin_intervalo() && valor <= this.problema.getMax_intervalo()) {
                    filho.getCromossomos().set(i, valor);
                }
            }
        }
    }
}
