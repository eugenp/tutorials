package controllers;
import models.*;
import util.*;
import play.mvc.*;
import play.libs.Json;
import play.libs.Json.*;
import com.fasterxml.jackson.databind.JsonNode;           
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class StudentController extends Controller {
    public Result create() {
		JsonNode json = request().body().asJson();
		if(json == null)
			return badRequest(Util.createResponse("Expecting Json data",false));
		Student student=StudentStore.getInstance().addStudent((Student)Json.fromJson(json,Student.class));
		JsonNode jsonObject=Json.toJson(student);
        return created(Util.createResponse(jsonObject,true));
    }
	public Result update() {
        JsonNode json = request().body().asJson();
		if(json == null)
			return badRequest(Util.createResponse("Expecting Json data",false));
		Student student=StudentStore.getInstance().updateStudent((Student)Json.fromJson(json,Student.class));
		if(student==null){
		return notFound(Util.createResponse("Student not found",false));
		}

		JsonNode jsonObject=Json.toJson(student);
        return ok(Util.createResponse(jsonObject,true));
    }
	public Result retrieve(int id) {
		Student student=StudentStore.getInstance().getStudent(id);
		if(student==null){
		return notFound(Util.createResponse("Student with id:"+id+" not found",false));
		}
		JsonNode jsonObjects=Json.toJson(student);
        return ok(Util.createResponse(jsonObjects,true));
    }
	public Result listStudents() {
		List<Student> result=StudentStore.getInstance().getAllStudents();
		    	ObjectMapper mapper = new ObjectMapper();

		JsonNode jsonData=mapper.convertValue(result, JsonNode.class);
        return ok(Util.createResponse(jsonData,true));

    }
	public Result delete(int id) {
		boolean status=StudentStore.getInstance().deleteStudent(id);
		if(!status){
		return notFound(Util.createResponse("Student with id:"+id+" not found",false));
		}
        return ok(Util.createResponse("Student with id:"+id+" deleted",true));
    }

}
