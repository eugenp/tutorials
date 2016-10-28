package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Student;
import models.StudentStore;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.Util;

import java.util.Set;

public class StudentController extends Controller {
    public Result create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Util.createResponse("Expecting Json data", false));
        }
        Student student = StudentStore.getInstance().addStudent(Json.fromJson(json, Student.class));
        JsonNode jsonObject = Json.toJson(student);
        return created(Util.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Util.createResponse("Expecting Json data", false));
        }
        Student student = StudentStore.getInstance().updateStudent(Json.fromJson(json, Student.class));
        if (student == null) {
            return notFound(Util.createResponse("Student not found", false));
        }

        JsonNode jsonObject = Json.toJson(student);
        return ok(Util.createResponse(jsonObject, true));
    }

    public Result retrieve(int id) {
        if (StudentStore.getInstance().getStudent(id) == null) {
            return notFound(Util.createResponse("Student with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(StudentStore.getInstance().getStudent(id));
        return ok(Util.createResponse(jsonObjects, true));
    }

    public Result listStudents() {
        Set<Student> result = StudentStore.getInstance().getAllStudents();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(Util.createResponse(jsonData, true));

    }

    public Result delete(int id) {
        if (!StudentStore.getInstance().deleteStudent(id)) {
            return notFound(Util.createResponse("Student with id:" + id + " not found", false));
        }
        return ok(Util.createResponse("Student with id:" + id + " deleted", true));
    }

}
