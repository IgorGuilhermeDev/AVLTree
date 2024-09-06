package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                System.out.println("Escolha uma ação: (1) Inserir (2) Deletar (3) Sair");
                int escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        System.out.print("Digite um número para inserir: ");
                        int valorInserir = scanner.nextInt();
                        tree.insert(new Element(valorInserir));
                        System.out.println("Árvore após inserção:");
                        tree.printTree();
                        break;
                    case 2:
                        System.out.print("Digite um número para deletar: ");
                        int valorDeletar = scanner.nextInt();
                        tree.delete(new Element(valorDeletar));
                        System.out.println("Árvore após deleção:");
                        tree.printTree();
                        break;
                    case 3:
                        running = false;
                        System.out.println("Saindo.");
                        break;
                    default:
                        System.out.println("Escolha inválida. Por favor, selecione 1, 2 ou 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número válido.");
                scanner.next();
            }
        }

        scanner.close();
    }
}
