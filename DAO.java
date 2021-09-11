import java.sql.*;

public class DAO {
    private Connection conexao;

    public DAO() {
        this.conexao = null;
    }

    public boolean conectar() {
        String driverName = "";
        String serverName = "localhost";
        String mydatabase = "alunos";
        int porta = 5432;
        String username = "postgres";
        String password = "123";
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean inserirAluno(Aluno aluno) {

        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO alunos (id, idade, nome, notafinal) "
                    + "VALUES (" + aluno.getId() + ", " + aluno.getIdade() + ", '" + aluno.getNome() + "'," + aluno.getNotaFinal() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean deletarAluno(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM alunos WHERE id = " + id + ";");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarAluno(Aluno aluno) {

        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE alunos SET nome = '" + aluno.getNome() + "', idade = '"
                    + aluno.getIdade() + "',notaFinal='"+ aluno.getNotaFinal() +
                    " WHERE id = " + aluno.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    public Aluno[] listarAluno(){
        Aluno[] alunos = null;
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM alunos");
            if(rs.next()){
                rs.last();
                alunos = new Aluno[rs.getRow()];

                rs.beforeFirst();
                for (int i = 0; rs.next(); i++){
                    alunos[i] = new Aluno(rs.getInt("id"), rs.getInt("idade"),rs.getString("nome"),rs.getInt("notafinal"));
                }
            }
            st.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return alunos;
    }
}
