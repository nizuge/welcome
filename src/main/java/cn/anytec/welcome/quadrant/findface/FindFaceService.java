package cn.anytec.welcome.quadrant.findface;


import cn.anytec.welcome.quadrant.pojo.DetectPojo;
import cn.anytec.welcome.quadrant.pojo.IdentifyPojo;
import cn.anytec.welcome.quadrant.pojo.VerifyPojo;

import org.springframework.stereotype.Service;


@Service
public interface FindFaceService {

    DetectPojo imageDetect(byte[] photo);

    VerifyPojo imagesVerify(byte[] photo1, byte[] photo2);

    IdentifyPojo imageIdentify(byte[] photo, DetectPojo.FaceInfo face);
}
