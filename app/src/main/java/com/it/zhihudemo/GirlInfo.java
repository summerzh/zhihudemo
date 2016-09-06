package com.it.zhihudemo;

import java.util.List;

/**
 * created by gyt on 2016/8/24
 */
public class GirlInfo {


    /**
     * error : false
     * results : [{"_id":"57bc5238421aa9125fa3ed70","createdAt":"2016-08-23T21:40:08.159Z","desc":"8.24","publishedAt":"2016-08-24T11:38:48.733Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f740f701gqj20u011hgo9.jpg","used":true,"who":"daimajia"},{"_id":"57bb039e421aa9125fa3ed5e","createdAt":"2016-08-22T21:52:30.572Z","desc":"8-22","publishedAt":"2016-08-23T11:29:45.813Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f72v5ra09fj20u011hdit.jpg","used":true,"who":"代码家"},{"_id":"57b93f9e421aa950cf8050ff","createdAt":"2016-08-21T13:43:58.241Z","desc":"8-22","publishedAt":"2016-08-22T11:29:37.164Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f71bezmt3tj20u00k0757.jpg","used":true,"who":"代码家"},{"_id":"57b64b6d421aa93a804bea26","createdAt":"2016-08-19T07:57:33.576Z","desc":"8-19","publishedAt":"2016-08-19T11:26:30.163Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f6yq5xrdofj20u00u0aby.jpg","used":true,"who":"daimajia"},{"_id":"57b53d22421aa93a74c34638","createdAt":"2016-08-18T12:44:18.965Z","desc":"8-18","publishedAt":"2016-08-18T12:55:02.929Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f6xsqw8057j20dw0kugpf.jpg","used":true,"who":"daimajia"},{"_id":"57b326d8421aa93a74c3462c","createdAt":"2016-08-16T22:44:40.54Z","desc":"8-17","publishedAt":"2016-08-17T11:38:52.656Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f6vyy5a99ej20u011gq87.jpg","used":true,"who":"代码家"},{"_id":"57b1e41d421aa93a804bea00","createdAt":"2016-08-15T23:47:41.110Z","desc":"816","publishedAt":"2016-08-16T11:22:38.139Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f6uv5gbsa9j20u00qxjt6.jpg","used":true,"who":"代码家"},{"_id":"57b10a9b421aa96004f4ba37","createdAt":"2016-08-15T08:19:39.899Z","desc":"8.15","publishedAt":"2016-08-15T11:27:22.462Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f6u4boc0k2j20u00u0gni.jpg","used":true,"who":"代码家"},{"_id":"57ad4023421aa949ef961f4b","createdAt":"2016-08-12T11:18:59.569Z","desc":"8-12","publishedAt":"2016-08-12T11:39:10.578Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f6qsn74e3yj20u011htc6.jpg","used":true,"who":"代码家"},{"_id":"57abf5ac421aa93fa66e8406","createdAt":"2016-08-11T11:49:00.660Z","desc":"8.11","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f6pnw6i7lqj20u00u0tbr.jpg","used":true,"who":"代码家"}]
     */

    private boolean             error;
    /**
     * _id : 57bc5238421aa9125fa3ed70
     * createdAt : 2016-08-23T21:40:08.159Z
     * desc : 8.24
     * publishedAt : 2016-08-24T11:38:48.733Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/610dc034jw1f740f701gqj20u011hgo9.jpg
     * used : true
     * who : daimajia
     */

    private List<ResultsEntity> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        private String  _id;
        private String  createdAt;
        private String  desc;
        private String  publishedAt;
        private String  source;
        private String  type;
        private String  url;
        private boolean used;
        private String  who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
