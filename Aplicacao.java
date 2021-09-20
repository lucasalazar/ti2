import static spark.Spark.*;

public class Aplicacao {

    private static AlunoService alunoService = new AlunoService();

    public static void main(String[] args) {
        port(6789);


        post("/aluno", (request, response) -> alunoService.add(request, response));

        get("/aluno/:codigo", (request, response) -> alunoService.get(request, response));


        get("/aluno/delete/:codigo", (request, response) -> alunoService.remove(request, response));

        get("/aluno", (request, response) -> alunoService.getAll(request, response));


    }
}