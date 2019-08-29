package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.Student;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import store.StudentStore;
import utils.Util;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class StudentController extends Controller {
    private HttpExecutionContext ec;
    private StudentStore studentStore;

    @Inject
    public StudentController(HttpExecutionContext ec, StudentStore studentStore) {
        this.studentStore = studentStore;
        this.ec = ec;
    }

    public CompletionStage<Result> listStudents() {
        return completedFuture(ok("There you go"));
    }

    public CompletionStage<Result> retrieve(int id) {
        return completedFuture(ok("There you go"));
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        return supplyAsync(() -> {
            if (json == null) {
                return badRequest(Util.createResponse("Expecting Json data", false));
            }

            Optional<Student> studentOptional = studentStore.addStudent(Json.fromJson(json, Student.class));
            return studentOptional.map(student -> {
                JsonNode jsonObject = Json.toJson(student);
                return created(Util.createResponse(jsonObject, true));
            }).orElse(internalServerError(Util.createResponse("Could not create data.", false)));
        }, ec.current());
    }

    public CompletionStage<Result> update() {
        return completedFuture(ok("There you go"));
    }

    public CompletionStage<Result> delete(int id) {
        return completedFuture(ok("There you go"));
    }
}
