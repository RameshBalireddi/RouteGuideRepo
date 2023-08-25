package routeGuide.APIResponse;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class APIResponse {

    private boolean success;
    private String message;
    private Object data;

    public static ResponseEntity<APIResponse> success(String message, Object data){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return ResponseEntity.ok(apiResponse);
    }


    public static ResponseEntity<APIResponse> errorBadRequest(String message){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMessage(message);
        apiResponse.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    public static ResponseEntity<APIResponse> successCreate(String message,Object data){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    public static ResponseEntity<APIResponse> errorUnauthorised(String message){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMessage(message);
        apiResponse.setData(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    public static ResponseEntity<APIResponse> errorNotFound(String message){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMessage(message);
        apiResponse.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    public static ResponseEntity<APIResponse> uploadSuccess(String message){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage(message);
        apiResponse.setData(null);
        return ResponseEntity.ok(apiResponse);
    }

    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public Object getData() {
        return data;
    }

}
