package cn.anytec.welcome.quadrant.pojo;


import java.util.List;
import java.util.Map;

public class IdentifyPojo {

    private Map<String,List<MatchFace>> results;

    public Map<String, List<MatchFace>> getResults() {
        return results;
    }

    public class MatchFace{
        private double confidence;
        private Face face;

        public double getConfidence() {
            return confidence;
        }

        public Face getFace() {
            return face;
        }

        public class Face{
            private long id;
            private int person_id;
            private double age;
            private String gender;
            private List<String> emotions;
            private boolean friend;
            private List<String> galleries;
            private String meta;
            private String normalized;
            private String photo;
            private String thumbnail;
            private String photo_hash;
            private String timestamp;
            private int x1;
            private int x2;
            private int y1;
            private int y2;

            public long getId() {
                return id;
            }

            public int getPerson_id() {
                return person_id;
            }

            public double getAge() {
                return age;
            }

            public String getGender() {
                return gender;
            }

            public List<String> getEmotions() {
                return emotions;
            }

            public boolean isFriend() {
                return friend;
            }

            public List<String> getGalleries() {
                return galleries;
            }

            public String getMeta() {
                return meta;
            }

            public String getNormalized() {
                return normalized;
            }

            public String getPhoto() {
                return photo;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public String getPhoto_hash() {
                return photo_hash;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public int getX1() {
                return x1;
            }

            public int getX2() {
                return x2;
            }

            public int getY1() {
                return y1;
            }

            public int getY2() {
                return y2;
            }
        }
    }

}
