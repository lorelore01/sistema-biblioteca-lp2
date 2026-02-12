package src;

public class Livro extends ItemDoAcervo implements Reservavel, Validavel {
    private String autor;
    private boolean reservado = false;
    private String isbn;

    public Livro(String titulo, String autor, int ano, String isbn) {
        super(titulo, ano);
        setAutor(autor);
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == "") {
            System.out.println("Erro: título inválido.");
        } else {
            this.autor = autor;
        }
    }

    @Override
    public int getPrazo() {
        return 14;
    }

    @Override
    public double getValorMultaPorDia() {
        return 0.75;
    }

    @Override
    public String toString() {
        return "Livro '" + getTitulo() + "', de " + autor + " (" + getAno() + ") - Status: " + getStatus();
    }

    @Override
    public String formatarParaEtiqueta() {
        return "ETIQUETA LIVRO - " + getTitulo() +
               " | Autor: " + autor + 
               " | Ano: " + getAno();
    }

    @Override
    public void reservar() {
        reservado = true;
    }

    @Override
    public void cancelarReserva() {
        reservado = false;
    }

    @Override
    public boolean isReservado() {
        return reservado;
    }

    @Override
    public boolean validar() {
        return getTitulo() != null &&
            !getTitulo().isEmpty() &&
            isbn != null &&
            isbn.length() == 13;
    }


}
