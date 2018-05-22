package cn.anytec.welcome.quadrant.pojo;

import java.util.List;

public class VerifyPojo {

    private List<VerifyDuo> results;

    public List<VerifyDuo> getResults() {
        return results;
    }

    public class VerifyDuo{
        private DetectPojo.FaceInfo bbox1;
        private DetectPojo.FaceInfo bbox2;
        private double confidence;
        private boolean verified;

        public DetectPojo.FaceInfo getBbox1() {
            return bbox1;
        }

        public DetectPojo.FaceInfo getBbox2() {
            return bbox2;
        }

        public double getConfidence() {
            return confidence;
        }

        public boolean isVerified() {
            return verified;
        }
    }
}
