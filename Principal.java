public class Principal {
    public static void main(String[] args){
        System.out.println("O que deseja fazer: 1 - inserir, 2 - listar, 3 - atualizar - 4 - deletar");
        DAO dao = new DAO();
        dao.conectar();
        Aluno aluno = new Aluno(1,2,"aluno", 10);
        int i = MyIO.readInt();
        switch (i){
            case 1:
                dao.inserirAluno(aluno);
            case 2:
                dao.listarAluno();
            case 3:
                dao.atualizarAluno(aluno);
            case 4:
                dao.deletarAluno(aluno.getId());
        }
    }
}
