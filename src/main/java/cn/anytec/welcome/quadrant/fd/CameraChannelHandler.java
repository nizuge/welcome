package cn.anytec.welcome.quadrant.fd;

import cn.anytec.welcome.config.GeneralConfig;
import cn.anytec.welcome.quadrant.pojo.FDCameraData;
import cn.anytec.welcome.quadrant.pojo.FaceDefine;
import cn.anytec.welcome.util.ImageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Base64;

/**
 * @Sharable
 * 标注一个channel handler可以被多个channel安全地共享。
 */
@Component
@Sharable
public class CameraChannelHandler extends ChannelInboundHandlerAdapter {
	private static final ByteBuf HEARTBEAT_SEQUENCE = 	Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("1", CharsetUtil.ISO_8859_1));
	private static final Logger logger = LoggerFactory.getLogger(CameraChannelHandler.class);
	private static ThreadLocal<Integer> drawBoxThreadLocal = new ThreadLocal<>();


	@Autowired
	private FDCameraDataHandler fdCameraDataHandler;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private GeneralConfig config;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug(ctx.channel().remoteAddress() + " connected");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String camIp = getCameraIp(ctx);
		logger.debug("Camera:{} offline",camIp);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg != null && msg instanceof FDCameraData) {
			/*String camIp = getCameraIp(ctx);*/
			FDCameraData fdCameraData = (FDCameraData)msg;
			fdCameraDataHandler.OnCameraData(fdCameraData);
			byte[] view = fdCameraData.mJpgData;
			Integer count = drawBoxThreadLocal.get();
			if(count == null) {
				count = 1;
			}else {
				count++;
			}
			if(count == config.getData_draw()) {
				count = 0;
				for(int i=0;i<fdCameraData.mFaceNum;i++){
					FaceDefine faceDefine = fdCameraData.mFaceItem[i];
					if(faceDefine!=null){
						int x= (int) (faceDefine.left);
						int y= (int) (faceDefine.top);
						int width = (int) ((faceDefine.right-faceDefine.left));
						int height= (int) ((faceDefine.bottom-faceDefine.top));
						view = ImageUtil.drawFaceBox(fdCameraData.mJpgData,x,y,width,height);
						fdCameraData.mJpgData = view;
					}
				}
			}
			drawBoxThreadLocal.set(count);
			String base64Pic = Base64.getEncoder().encodeToString(view);
			simpMessagingTemplate.convertAndSend("/topic/"+fdCameraData.mStrMac,"{\"scene\":\""+base64Pic+"\"}");

		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) {
					if (!future.isSuccess()) {
						future.channel().close();
						//FDCameraDataHandler.setCameraSessionOffline(clientIp);
					}
				}
			});

		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		String clientIp = getCameraIp(ctx);
		logger.error(cause.getMessage() , cause);
		logger.debug("ExceptionCaught from {}", clientIp);// let camera reconnect
		ctx.channel().close();
	}

	
	private String getCameraIp(ChannelHandlerContext ctx) {
		return ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
	}

	
	
}
