import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO {
    private List<Aluno> alunos;
    private int maxCodigo = 0;

    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream outputFile;

    public int getMaxCodigo() {
        return maxCodigo;
    }

    public AlunoDAO(String filename) throws IOException {

        file = new File(filename);
        alunos = new ArrayList<Aluno>();
        if (file.exists()) {
            readFromFile();
        }

    }

    public void add(Aluno aluno) {
        try {
            alunos.add(aluno);
            this.maxCodigo = (aluno.getId() > this.maxCodigo) ? aluno.getId() : this.maxCodigo;
            this.saveToFile();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar o aluno '" + aluno.getNome() + "' no disco!");
        }
    }

    public Aluno get(int codigo) {
        for (Aluno aluno : alunos) {
            if (codigo == aluno.getId()) {
                return aluno;
            }
        }
        return null;
    }

    public void update(Aluno j) {
        int index = alunos.indexOf(j);
        if (index != -1) {
            alunos.set(index, j);
            this.saveToFile();
        }
    }

    public void remove(Aluno j) {
        int index = alunos.indexOf(j);
        if (index != -1) {
            alunos.remove(index);
            this.saveToFile();
        }
    }

    public List<Aluno> getAll() {
        return alunos;
    }

    private List<Aluno> readFromFile() {
        alunos.clear();
        Aluno aluno = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream inputFile = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                aluno = (Aluno) inputFile.readObject();
                alunos.add(aluno);
                maxCodigo = (aluno.getId() > maxCodigo) ? aluno.getId() : maxCodigo;
            }
        } catch (Exception e) {
            System.out.println("ERRO ao gravar aluno no disco!");
            e.printStackTrace();
        }
        return alunos;
    }

    private void saveToFile() {
        try {
            fos = new FileOutputStream(file, false);
            outputFile = new ObjectOutputStream(fos);

            for (Aluno aluno : alunos) {
                outputFile.writeObject(aluno);
            }
            outputFile.flush();
            this.close();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar aluno no disco!");
            e.printStackTrace();
        }
    }

    private void close() throws IOException {
        outputFile.close();
        fos.close();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            this.saveToFile();
            this.close();
        } catch (Exception e) {
            System.out.println("ERRO ao salvar a base de dados no disco!");
            e.printStackTrace();
        }
    }

}
