public class Aluno {
    private int idade;
    private int Id;
    private String nome;
    private double notaFinal;

    public Aluno(int Id, int idade, String nome, double notaFinal) {
        this.Id = Id;
        this.idade = idade;
        this.nome = nome;
        this.notaFinal = notaFinal;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNotaFinal(int notaFinal) {
        this.notaFinal = notaFinal;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public int getIdade() {
        return this.idade;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
