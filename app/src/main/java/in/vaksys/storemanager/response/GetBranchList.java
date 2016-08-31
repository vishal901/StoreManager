package in.vaksys.storemanager.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lenovoi3 on 8/31/2016.
 */
public class GetBranchList {


    /**
     * error : false
     * message : Get Branch successfully.
     * branch : [{"id":"1","name":"S g highway","address":"Pakwan Hotel"},{"id":"2","name":"mehsana","address":"mehsana address bdbbdbb"},{"id":"3","name":"mehsana baj","address":"hahahhsh"},{"id":"4","name":"bsbbsb","address":"hshsbbd"},{"id":"5","name":"hhahahsh","address":"jjdjjdjjdjjj"},{"id":"6","name":"hhahahsh jdjjsjjs","address":"jjdjjdjjdjjj"},{"id":"7","name":"mesasaka","address":"hhsjsj"},{"id":"8","name":"visha","address":"balol"},{"id":"9","name":"hsjjsjs","address":"bsjsjjsj"},{"id":"10","name":"ghjbvv","address":"bbjjb. bn"},{"id":"11","name":"rakshit","address":"ab"},{"id":"12","name":"abcs","address":"abda"},{"id":"13","name":"xyz","address":"xas"},{"id":"14","name":"hhhjhhjjjjnj","address":"vbbjjjjjnbnn"},{"id":"15","name":"gsddt","address":"chfdshh"},{"id":"16","name":"bas yhau","address":"jajkjjdj"},{"id":"17","name":"admin","address":"admin"},{"id":"21","name":"addd","address":"adsdd"},{"id":"22","name":"adminass","address":"nzjdjjdj"},{"id":"23","name":"kzjxndn","address":"jzndnndndk"},{"id":"24","name":"nananns","address":"hznsndndnnn"},{"id":"25","name":"location","address":"thanks"},{"id":"26","name":"adminkkkk","address":"bznznsnjs\nsjjzjs"},{"id":"28","name":"abc","address":"sjjsjsjjdjd"},{"id":"29","name":"akleshpur","address":"akleshpur"},{"id":"30","name":"Ahmedabad","address":"Ahmedabad,india"},{"id":"31","name":"Vadodara","address":"abc"},{"id":"32","name":"surat","address":"abcd"},{"id":"33","name":"raj","address":"abc"},{"id":"34","name":"memnagar","address":"abc"}]
     */

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    /**
     * id : 1
     * name : S g highway
     * address : Pakwan Hotel
     */

    @SerializedName("branch")
    private List<BranchBean> branch;

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

    public List<BranchBean> getBranch() {
        return branch;
    }

    public void setBranch(List<BranchBean> branch) {
        this.branch = branch;
    }

    public static class BranchBean {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("address")
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
