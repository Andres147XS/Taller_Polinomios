public class Polinomio {
    private Nodo cabeza;

    public Polinomio() {
        this.cabeza = null;
    }

    public void agregar(Nodo nuevo) {
        if (cabeza == null || nuevo.getExponente() > cabeza.getExponente()) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null && actual.siguiente.getExponente() >= nuevo.getExponente()) {
                if (actual.siguiente.getExponente() == nuevo.getExponente()) {
                    actual.siguiente = null;
                    break;
                }
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
    }

    public Polinomio derivar() {
        Polinomio derivado = new Polinomio();
        Nodo actual = cabeza;

        while (actual != null) {
            if (actual.getExponente() != 0) {
                derivado.agregar(new Nodo(actual.getCoeficiente() * actual.getExponente(), actual.getExponente() - 1));
            }
            actual = actual.siguiente;
        }

        return derivado;
    }

    public static Polinomio sumar(Polinomio p1, Polinomio p2) {
        Polinomio resultado = new Polinomio();
        Nodo actual1 = p1.cabeza;
        Nodo actual2 = p2.cabeza;

        while (actual1 != null || actual2 != null) {
            if (actual1 == null) {
                resultado.agregar(new Nodo(actual2.getCoeficiente(), actual2.getExponente()));
                actual2 = actual2.siguiente;
            } else if (actual2 == null) {
                resultado.agregar(new Nodo(actual1.getCoeficiente(), actual1.getExponente()));
                actual1 = actual1.siguiente;
            } else if (actual1.getExponente() > actual2.getExponente()) {
                resultado.agregar(new Nodo(actual1.getCoeficiente(), actual1.getExponente()));
                actual1 = actual1.siguiente;
            } else if (actual1.getExponente() < actual2.getExponente()) {
                resultado.agregar(new Nodo(actual2.getCoeficiente(), actual2.getExponente()));
                actual2 = actual2.siguiente;
            } else {
                double coef = actual1.getCoeficiente() + actual2.getCoeficiente();
                if (coef != 0) {
                    resultado.agregar(new Nodo(coef, actual1.getExponente()));
                }
                actual1 = actual1.siguiente;
                actual2 = actual2.siguiente;
            }
        }

        return resultado;
    }

    public static Polinomio restar(Polinomio p1, Polinomio p2) {
        Polinomio p2Negado = new Polinomio();
        Nodo actual = p2.cabeza;

        while (actual != null) {
            p2Negado.agregar(new Nodo(-actual.getCoeficiente(), actual.getExponente()));
            actual = actual.siguiente;
        }

        return sumar(p1, p2Negado);
    }

    public static Polinomio multiplicar(Polinomio p1, Polinomio p2) {
        Polinomio resultado = new Polinomio();
        Nodo actual1 = p1.cabeza;

        while (actual1 != null) {
            Nodo actual2 = p2.cabeza;
            while (actual2 != null) {
                double coef = actual1.getCoeficiente() * actual2.getCoeficiente();
                int expo = actual1.getExponente() + actual2.getExponente();
                resultado.agregar(new Nodo(coef, expo));
                actual2 = actual2.siguiente;
            }
            actual1 = actual1.siguiente;
        }

        return resultado;
    }

    public static Polinomio[] dividir(Polinomio p1, Polinomio p2) {
        if (p2.cabeza == null) {
            return null; // División entre cero
        }

        Polinomio cociente = new Polinomio();
        Polinomio residuo = new Polinomio();
        Nodo actual1 = p1.cabeza;

        while (actual1 != null) {
            residuo.agregar(new Nodo(actual1.getCoeficiente(), actual1.getExponente()));
            actual1 = actual1.siguiente;
        }

        while (residuo.cabeza != null && residuo.cabeza.getExponente() >= p2.cabeza.getExponente()) {
            double coef = residuo.cabeza.getCoeficiente() / p2.cabeza.getCoeficiente();
            int expo = residuo.cabeza.getExponente() - p2.cabeza.getExponente();
            Nodo termino = new Nodo(coef, expo);
            cociente.agregar(termino);

            Polinomio temp = new Polinomio();
            temp.agregar(termino);
            temp = multiplicar(temp, p2);

            residuo = restar(residuo, temp);
        }

        return new Polinomio[]{cociente, residuo};
    }

    @Override
    public String toString() {
        if (cabeza == null) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        Nodo actual = cabeza;

        while (actual != null) {
            if (actual.getCoeficiente() > 0 && sb.length() > 0) {
                sb.append(" + ");
            } else if (actual.getCoeficiente() < 0) {
                sb.append(" - ");
            }

            sb.append(Math.abs(actual.getCoeficiente()));
            if (actual.getExponente() > 0) {
                sb.append("x^").append(actual.getExponente());
            }

            actual = actual.siguiente;
        }

        return sb.toString();
    }
}