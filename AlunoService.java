import java.io.IOException;


public class AlunoService {

    private AlunoDAO alunoDAO;

    public AlunoService() {
        try {
            alunoDAO = new AlunoDAO("alunos.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object add(Request request, Response response) {
        String nome = request.queryParams("nome");
        int idade = Integer.parseInt(request.queryParams("idade"));
        double notafinal = Double.parseDouble(request.queryParams("notafinal"));


        int codigo = alunoDAO.getMaxCodigo() + 1;

        Aluno aluno = new Aluno(codigo, idade, nome, notafinal);

        alunoDAO.add(aluno);

        response.status(201); // 201 Created
        return codigo;
    }

    public Object get(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));

        Aluno aluno = (Aluno) alunoDAO.get(codigo);

        if (aluno != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<aluno>\n" +
                    "\t<codigo> " + aluno.getId() + "</codigo>\n" +
                    "\t<nome> " + aluno.getNome() + "</nome>\n" +
                    "\t<idade> " + aluno.getIdade() + "</idade>\n" +
                    "\t<notafinal> " + aluno.getNotaFinal() + "</notafinal>\n" +

                    "</aluno>\n";
        } else {
            response.status(404); // 404 Not found
            return "Aluno " + codigo + " não encontrado.";
        }

    }

    public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));

        Aluno aluno = (Aluno) alunoDAO.get(codigo);

        if (aluno != null) {
            aluno.setNome(request.queryParams("nome"));
            aluno.setIdade(request.queryParams("idade"));
            aluno.setNotaFinal(Integer.parseInt(request.queryParams("notafinal")));


            alunoDAO.update(aluno);

            return codigo;
        } else {
            response.status(404); // 404 Not found
            return "Aluno não encontrado.";
        }

    }

    public Object remove(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));

        Aluno aluno = (Aluno) alunoDAO.get(codigo);

        if (aluno != null) {

            alunoDAO.remove(aluno);

            response.status(200); // success
            return codigo;
        } else {
            response.status(404); // 404 Not found
            return "aluno não encontrado.";
        }
    }

    public Object getAll(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<bensdeconsumo type=\"array\">");
        for (Aluno jog : alunoDAO.getAll()) {
            Aluno aluno = (Aluno) jog;
            returnValue.append("<aluno>\n" +
                    "\t<codigo> " + aluno.getId() + "</codigo>\n" +
                    "\t<nome> " + aluno.getNome() + "</nome>\n" +
                    "\t<idade> " + aluno.getIdade() + "</idade>\n" +
                    "\t<notafinal> " + aluno.getNotaFinal() + "</notafinal>\n" +

                    "</aluno>\n");
        }
        returnValue.append("</alunos>");
        response.header("Content-Type", "application/xml");
        response.header("Content-Encoding", "UTF-8");
        return returnValue.toString();

    }

}
