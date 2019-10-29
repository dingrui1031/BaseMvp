package cn.dr.basemvp.update;

/**
 * dr
 * 2019/5/24
 */
public class MyUpdateBean{


    /**
     * httpcode : 200
     * message : ok
     * data : {"id":16,"name":"1.0.4","code":"5","content":"1.修复已知bug","create_date":"2019-05-23 05:07:08","file":"http://sj-mdt.basecare.cn/sj/content/1214","down_address":""}
     */

    private int httpcode;
    private String message;
    private DataBean data;

    public int getHttpcode() {
        return httpcode;
    }

    public void setHttpcode(int httpcode) {
        this.httpcode = httpcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 16
         * name : 1.0.4
         * code : 5
         * content : 1.修复已知bug
         * create_date : 2019-05-23 05:07:08
         * file : http://sj-mdt.basecare.cn/sj/content/1214
         * down_address :
         */

        private int id;
        private String name;
        private String code;
        private String content;
        private String create_date;
        private String file;
        private String down_address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getDown_address() {
            return down_address;
        }

        public void setDown_address(String down_address) {
            this.down_address = down_address;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", content='" + content + '\'' +
                    ", create_date='" + create_date + '\'' +
                    ", file='" + file + '\'' +
                    ", down_address='" + down_address + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyUpdateBean{" +
                "httpcode=" + httpcode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
