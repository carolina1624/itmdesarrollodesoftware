package com.cajero;

import java.util.Scanner;


class ManejadorBilletes {
    private int valorBillete;
    private ManejadorBilletes siguienteManejador;

   
    public ManejadorBilletes(int valorBillete) {
        this.valorBillete = valorBillete;
    }

   
    public void asignarSiguiente(ManejadorBilletes siguienteManejador) {
        this.siguienteManejador = siguienteManejador;
    }

    
    public void gestionar(int monto) {
        if (monto >= valorBillete) {
            int cantidadBilletes = monto / valorBillete;
            int montoRestante = monto % valorBillete;
            System.out.println("Entregando " + cantidadBilletes + " billetes de $" + valorBillete);
            if (montoRestante > 0 && siguienteManejador != null) {
                siguienteManejador.gestionar(montoRestante);
            }
        } else if (siguienteManejador != null) {
            siguienteManejador.gestionar(monto);
        }
    }
}


class DispensadorATM {
    private ManejadorBilletes primerManejador;

    // Constructor para configurar los manejadores
    public DispensadorATM() {
        primerManejador = configurarManejadores();
    }


    private ManejadorBilletes configurarManejadores() {
        int[] valoresBilletes = {100000, 50000, 20000, 10000, 5000};
        ManejadorBilletes manejadorInicial = new ManejadorBilletes(valoresBilletes[0]);
        ManejadorBilletes manejadorActual = manejadorInicial;

      
        for (int i = 1; i < valoresBilletes.length; i++) {
            ManejadorBilletes siguienteManejador = new ManejadorBilletes(valoresBilletes[i]);
            manejadorActual.asignarSiguiente(siguienteManejador);
            manejadorActual = siguienteManejador;
        }

        return manejadorInicial;
    }

    // Proceso de retiro de dinero
    public void procesarRetiro(int monto) {
        if (monto % 5000 != 0) {
            System.out.println("Error: El monto debe ser múltiplo de 5.000");
        } else {
            primerManejador.gestionar(monto);
        }
    }
}


public class Cajero {
    public static void main(String[] args) {
        DispensadorATM cajeroATM = new DispensadorATM(); 
        Scanner leer = new Scanner(System.in); 
        System.out.print("Ingrese el monto a retirar: ");
        int montoRetirar = leer.nextInt(); 

        leer.close();
        cajeroATM.procesarRetiro(montoRetirar); 
    }
}
