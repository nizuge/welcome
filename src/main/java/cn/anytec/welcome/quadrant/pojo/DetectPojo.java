package cn.anytec.welcome.quadrant.pojo;

import java.util.List;

public class DetectPojo {

    private double orientation;
    private List<FaceInfo> faces;

    public double getOrientation() {
        return orientation;
    }

    public List<FaceInfo> getFaces() {
        return faces;
    }

    public class FaceInfo{
        private double age;
        private List<String> emotions;
        private String gender;
        private int x1;
        private int x2;
        private int y1;
        private int y2;

        public double getAge() {
            return age;
        }

        public List<String> getEmotions() {
            return emotions;
        }

        public String getGender() {
            return gender;
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

        public String toBbox(){
            return "[["+x1+","+y1+","+x2+","+y2+"]]";
        }
    }
}
