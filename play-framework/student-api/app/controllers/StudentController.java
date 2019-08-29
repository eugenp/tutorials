package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Set;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class StudentController extends Controller {
    private HttpExecutionContext ec;
    private StudentStore studentStore;

    @Inject
    public StudentController(HttpExecutionContext ec, StudentStore studentStore) {
        this.studentStore = studentStore;
        this.ec = ec;
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

    public CompletionStage<Result> listStudents() {
        return supplyAsync(() -> {
            Set<Student> result = studentStore.getAllStudents();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
            return ok(Util.createResponse(jsonData, true));
        }, ec.current());
    }

    public CompletionStage<Result> retrieve(int id) {
        return supplyAsync(() -> {
            final Optional<Student> studentOptional = studentStore.getStudent(id);
            return studentOptional.map(student -> {
                JsonNode jsonObjects = Json.toJson(student);
                return ok(Util.createResponse(jsonObjects, true));
            }).orElse(notFound(Util.createResponse("Student with id:" + id + " not found", false)));
        }, ec.current());
    }

    public CompletionStage<Result> update(Http.Request request) {
        JsonNode json = request.body().asJson();
        return supplyAsync(() -> {
            if (json == null) {
                return badRequest(Util.createResponse("Expecting Json data", false));
            }
            Optional<Student> studentOptional = studentStore.updateStudent(Json.fromJson(json, Student.class));
            return studentOptional.map(student -> {
                if (student == null) {
                    return notFound(Util.createResponse("Student not found", false));
                }
                JsonNode jsonObject = Json.toJson(student);
                return ok(Util.createResponse(jsonObject, true));
            }).orElse(internalServerError(Util.createResponse("Could not create data.", false)));
        }, ec.current());
    }

    public CompletionStage<Result> delete(int id) {
        return supplyAsync(() -> {
            boolean status = studentStore.deleteStudent(id);
            if (!status) {
                return notFound(Util.createResponse("Student with id:" + id + " not found", false));
            }
            return ok(Util.createResponse("Student with id:" + id + " deleted", true));
        }, ec.current());
    }
}
