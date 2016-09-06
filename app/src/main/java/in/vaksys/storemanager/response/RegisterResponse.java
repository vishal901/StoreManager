package in.vaksys.storemanager.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovoi3 on 8/8/2016.
 */
public class RegisterResponse {


    /**
     * error : false
     * message : Data saved successfully.
     * customer_id : 14
     */

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("customer_id")
    private String customerId;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
