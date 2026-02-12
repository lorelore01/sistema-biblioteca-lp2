package src;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<ItemDoAcervo> acervo;
    private List<Usuario> listaDeUsuarios;
    private List<Emprestimo> registrosDeEmprestimos;

    public Biblioteca() {
        this.acervo = new ArrayList<>();
        this.listaDeUsuarios = new ArrayList<>();
        this.registrosDeEmprestimos = new ArrayList<>();
    }

    public void realizarEmprestimo(String idUsuario, String titulo) {

        // Buscar usuário
        Usuario usuario = pesquisarUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Erro: usuário não cadastrado.");
            return;
        }

        // Buscar item
        ItemDoAcervo item = pesquisarItemPorTitulo(titulo);
        if (item == null) {
            System.out.println("Erro: item não cadastrado.");
            return;
        }

        // Verificar disponibilidade
        if (item.getStatus() == StatusLivro.EMPRESTADO) {
            System.out.println("Erro: item já emprestado.");
            return;
        }

        // Criar empréstimo
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(item.getPrazo());

        Emprestimo emprestimo = new Emprestimo(
                item,
                usuario,
                dataEmprestimo,
                dataDevolucaoPrevista
        );

        // Atualizar status e registrar
        item.setStatus(StatusLivro.EMPRESTADO);
        registrosDeEmprestimos.add(emprestimo);

        // Mensagem final
        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println(
                "O item '" + item.getTitulo() +
                "' foi emprestado para o usuário " +
                usuario.getNome() +
                " na data " + dataEmprestimo +
                " e deve ser devolvido em " +
                dataDevolucaoPrevista
        );
    }


    public Emprestimo buscarEmprestimoAtivoPorItem(ItemDoAcervo item) {
        for (Emprestimo emprestimo : registrosDeEmprestimos) {
            if(emprestimo.getItem().getTitulo().equalsIgnoreCase(item.getTitulo())) {
                if(emprestimo.getDataDevolucaoReal() == null) {
                    return emprestimo;
                }
            }
        }
        return null;
    }

    public void realizarDevolucao(String titulo) {
        ItemDoAcervo item = pesquisarItemPorTitulo(titulo);
        if(item == null) {
            System.out.println("Erro: esse item não está cadastrado.");
            return;
        }
        Emprestimo emprestimo = buscarEmprestimoAtivoPorItem(item);
        if(emprestimo == null) {
            System.out.println("Erro: esse emprestimo não existe.");
            return;
        }
        LocalDate hoje = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), hoje);

        if(dias > 0) {
            double multa = dias * item.getValorMultaPorDia();
            System.out.println("Item devolvido. Você precisa pagar uma multa de R$" + multa);
        } else {
            System.out.println("Item devolvido.");
        }
        emprestimo.getItem().setStatus(StatusLivro.DISPONIVEL);
        emprestimo.setDataDevolucaoReal(hoje);
    }

    public ItemDoAcervo pesquisarItemPorTitulo(String titulo) {
        for(ItemDoAcervo item : this.acervo) {
            if(item.getTitulo().equalsIgnoreCase(titulo)) {
                return item;
            }
        }
        return null;
    }

    public Usuario pesquisarUsuarioPorId(String id) {
        for(Usuario usuario : this.listaDeUsuarios) {
            if(usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

//    public List<Livro> pesquisarLivroPorTermo(String termo) {
//
//    }

    public void listarAcervo() {
        System.out.println("Items no Acervo");
        for (var item : acervo) {
            System.out.println(item);
        }
    }

    public void cadastrarItem(ItemDoAcervo item) {
        this.acervo.add(item);
        System.out.println("O item " + item.getTitulo() + " foi cadastrado.");
    }

    public void cadastrarUsuario(Usuario usuario) {
        this.listaDeUsuarios.add(usuario);
        System.out.println("O usuário " + usuario.getNome() + " foi cadastrado.");
    }

    public void imprimirDocumento(Imprimivel objeto) {
        System.out.println("==================================");
        System.out.println(objeto.formatarParaEtiqueta());
        System.out.println("==================================");
    }

    public static void main(String[] args) {
        Livro livroJavaComoProgramar = new Livro("Java Como Programar", "Deitel", 2014, "1234567890123");
        Livro livroMemoria = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881, "9876543210123");
        Usuario meuUsuario = new Usuario("Thiago", "123");
        Biblioteca minhaBiblioteca = new Biblioteca();

        Revista revistaVeja = new Revista("Veja - Abril", 2015, 1);
        minhaBiblioteca.cadastrarItem(revistaVeja);
        minhaBiblioteca.cadastrarItem(livroJavaComoProgramar);
        minhaBiblioteca.cadastrarItem(livroMemoria);
        minhaBiblioteca.cadastrarUsuario(meuUsuario);
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.realizarEmprestimo("123", "Veja - Abril");
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.registrosDeEmprestimos.get(0).setDataDevolucaoPrevista(LocalDate.of(2025, 8, 31));
        minhaBiblioteca.realizarDevolucao("Veja - Abril");
        minhaBiblioteca.listarAcervo();
        System.out.println();


        System.out.println(livroMemoria.getPrazo());
        System.out.println(revistaVeja.getPrazo());

        minhaBiblioteca.imprimirDocumento(livroJavaComoProgramar);
        minhaBiblioteca.imprimirDocumento(meuUsuario);
        
    }
}
