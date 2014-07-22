package org.kratos.kracart.utility;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class ImageUtils {

	public static boolean createThumbnail(String fromFileStr,
			String saveToFileStr, String suffix, int width, int height,
			boolean equalProportion) throws Exception {
		double Ratio = 0.0;
		File fromFile = new File(fromFileStr);
		if (!fromFile.isFile()) {
			return false;
		}
		File ThF = new File(saveToFileStr);
		BufferedImage Bi = ImageIO.read(fromFile);
		Image Itemp = Bi.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
		if ((Bi.getHeight() > width) || (Bi.getWidth() > height)) {
			if (Bi.getHeight() > Bi.getWidth())
				Ratio = (double) width / Bi.getHeight();
			else
				Ratio = (double) height / Bi.getWidth();
		}
		AffineTransformOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(Ratio, Ratio), null);
		Itemp = op.filter(Bi, null);
		try {
			ImageIO.write((BufferedImage) Itemp, suffix, ThF);
		} catch (Exception ex) {
			throw new Exception(" ImageIo.write error in CreatThum.: "
					+ ex.getMessage());
		}
		return true;
	}

	public static void resizeImage(String fromFileStr, String saveToFileStr,
			int width, int height, boolean equalProportion) throws Exception {
		BufferedImage srcImage;
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".png")) {
			imgType = "PNG";
		}
		File fromFile = new File(fromFileStr);
		File saveFile = new File(saveToFileStr);
		srcImage = ImageIO.read(fromFile);
		if (width > 0 || height > 0) {
			srcImage = resize(srcImage, width, height, equalProportion);
		}
		ImageIO.write(srcImage, imgType, saveFile);
	}

	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH, boolean equalProportion) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比例的缩放
		// 如果不需要等比例的缩放则下面的if else语句注释调即可
		if (equalProportion) {
			if (sx > sy) {
				sx = sy;
				targetW = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				targetH = (int) (sx * source.getHeight());
			}
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source,
					AffineTransform.getScaleInstance(sx, sy));
			g.dispose();
		}
		return target;
	}

}
