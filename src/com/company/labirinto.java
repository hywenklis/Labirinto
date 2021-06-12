package com.company;

public class labirinto {
    private static final char PAREDE_VERTICAL = '|';
    private static final char PAREDE_HORIZONTAL = '-';
    private static final char PAREDE_INTERNA = '@';
    private static final char VAZIO = ' ';
    private static final char TAMANHO = 10;
    private static final char INICIO = 'I';
    private static final char DESTINO = 'D';
    private static final char CAMINHO = '.';
    private static final char SEM_SAIDA = 'x';
    private static final double PROBABILIDADE = 0.7;
    private static int linhaInicio;
    private static int colunaInicio;
    private static char[][] tabuleiro;

    public static int gerarNumero(int maximo, int minimo){
        int valor = (int)Math.round(Math.random() * (maximo - minimo));
        return minimo + valor;
    }

    public static boolean procurarCaminho (int linhaAtual, int colunaAtual){
        int proxLinha;
        int proxColuna;
        boolean achou = false;

        // tenta subir
        proxLinha = linhaAtual -1;
        proxColuna = colunaAtual;
        achou = tentarCaminho(proxLinha, proxColuna);

        //tenta descer
        if (!achou){
            proxLinha = linhaAtual +1;
            proxColuna = colunaAtual;
            achou = tentarCaminho(proxLinha, proxColuna);
        }

        //tenta à esquerda
        if (!achou){
            proxLinha = linhaAtual;
            proxColuna = colunaAtual -1;
            achou = tentarCaminho(proxLinha, proxColuna);
        }

        //tenta à direita
        if (!achou){
            proxLinha = linhaAtual;
            proxColuna = colunaAtual +1;
            achou = tentarCaminho(proxLinha, proxColuna);
        }
        return achou;
    }

    private static boolean tentarCaminho(int proxLinha, int proxColuna) {
        boolean achou = false;
        if (tabuleiro[proxLinha][proxColuna] == DESTINO){
            achou = true;
        }else if (posicaoVazia(proxLinha, proxColuna)){
            //marcar
            tabuleiro[proxLinha][proxColuna] = CAMINHO;
            imprimir();
            achou = procurarCaminho(proxLinha, proxColuna);
            if (!achou){
                tabuleiro[proxLinha][proxColuna] = SEM_SAIDA;
                imprimir();
            }
        }
        return achou;
    }

    private static boolean posicaoVazia(int linha, int coluna) {
        boolean vazio = false;
        if (linha >= 0 && linha < TAMANHO && coluna < TAMANHO){
            vazio = (tabuleiro[linha][coluna] == VAZIO);
        }
        return vazio;
    }

    public static void inicializarMatriz() {
        int i, j;
        for (i = 0; i < TAMANHO; i++) {
            tabuleiro[i][0] = PAREDE_VERTICAL;
            tabuleiro[i][TAMANHO - 1] = PAREDE_VERTICAL;
            tabuleiro[0][i] = PAREDE_HORIZONTAL;
            tabuleiro[TAMANHO - 1][i] = PAREDE_HORIZONTAL;
        }
        for (i = 1; i < TAMANHO - 1; i++) {
            for (j = 1; j < TAMANHO - 1; j++) {
                if (Math.random() > PROBABILIDADE){
                    tabuleiro[i][j] = PAREDE_INTERNA;
                } else {
                    tabuleiro[i][j] = VAZIO;
                }
            }
        }
        linhaInicio = gerarNumero(1, TAMANHO /2-1);
        colunaInicio = gerarNumero(1, TAMANHO / 2-1);
        tabuleiro[linhaInicio][colunaInicio] = INICIO;

        int linhaDestino = gerarNumero(TAMANHO / 2, TAMANHO - 2);
        int colunaDestino = gerarNumero(TAMANHO / 2, TAMANHO - 2);
        tabuleiro[linhaDestino][colunaDestino] = DESTINO;
    }


    public static void imprimir() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(tabuleiro[i][j]);
            }
            System.out.println();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String Arg[]) {
        tabuleiro = new char[TAMANHO][TAMANHO];
        inicializarMatriz();
        imprimir();

        System.out.println("\n- Procurando solução -\n");
            boolean achou = procurarCaminho(linhaInicio, colunaInicio);
            if(achou){
                System.out.println("ACHOU CAMINHO");
            }else{
                System.out.println("NÂO ACHOU");
            }
    }
}