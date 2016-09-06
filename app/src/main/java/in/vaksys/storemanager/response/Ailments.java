package in.vaksys.storemanager.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lenovoi3 on 8/8/2016.
 */
public class Ailments {

    /**
     * error : false
     * data : [{"id":"1","code":"BAD_POSTURE","title":"Bad Posture"},{"id":"2","code":"DIABETICE","title":"Diabetice"},{"id":"3","code":"FREQUENT_STOMACH_PAINS","title":"Frequent Stomach Pains"},{"id":"4","code":"FOR_MIND_CONCENTRATION","title":"For Mind Concentration"},{"id":"5","code":"FORZEN_SHOUDER","title":"Forzen Shouder"},{"id":"6","code":"SCOLIOSIS","title":"Scoliosis"}]
     */

    @SerializedName("error")
    private boolean error;
    /**
     * id : 1
     * code : BAD_POSTURE
     * title : Bad Posture
     */

    @SerializedName("data")
    private List<DataBean> data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("id")
        private String id;
        @SerializedName("code")
        private String code;
        @SerializedName("title")
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
